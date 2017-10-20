/*
 * Intention of this class is to handle any input reader and parse to object DefaultTableModel
 * Also to create DataDef objects I think how to map them.  
 */
package DataSet;
import com.mockrunner.mock.jdbc.MockResultSet;
import java.sql.*;
import java.util.*;
/**
 *
 * @author Emmanuel
 */
public class TableData {
    
    private MockResultSet dataSet;
    private HashMap<String,DataDef> defMap;
    private int colSize = 0;
    private DefOperator defOperator;
    
    public TableData(){
        defMap = new HashMap<String,DataDef>();
        defOperator = new DefOperator();
        
    }
    
    public void setDataSetFromCSV(String[] header, List<String[]> data, int dataColumnSize) throws Exception{
        //Read from the csv file to table
        if(header != null && data!= null){
            colSize = dataColumnSize;
            if(header.length == colSize)
            {
                MockResultSet mockResultSet = new MockResultSet("DataSet");
                //boolean f = mockResultSet.isClosed();
                for(String head : header){
                    mockResultSet.addColumn(head);
                }
                for(String[] row : data){
                    mockResultSet.addRow(row);
                }
                setDataSet(mockResultSet);
            }
            else{
                throw new Exception("[TableData] Header and data columns are not the same size.");
            }
        }
        else{
            throw new Exception("[TableData] Header and data are null.");
        }
    }
    
    public void setDataSetFromDB(MockResultSet data){
        setDataSet(data);
    }
    
    public void definitionOp(){
        //Automated Def all variables
        //Set for every column the name, data (without special chars), list of distinct values and count, and the population
        //Then map with key as name of column and DataDef object is created with Table
        StringBuilder colName;
        List columnData;
        List<String> columnResult;
        List<String> distinctValues;
        Map<String,Integer> distinctCount;
        DataDef def;
        
        try{
            for(int i=1;i<colSize;i++){
                def = new DataDef();
                colName = new StringBuilder(getDataSet().getMetaData().getColumnName(i));
                columnData = getDataSet().getColumn(colName.toString());
                columnResult = cleanVariable(columnData);
                updateColumnValue(colName,columnResult);
                distinctValues = distVariables(columnResult);
                distinctCount = countDistVariables(distinctValues,columnResult);
                def.setName(colName);
                def.setStringValues(columnResult);
                def.setDistHead(distinctValues);
                def.setDistValues(distinctCount);
                def.setPopulation(columnResult.size());
                defOperator.defineVariableSubType(def);
                if(def.getVarSubType().equals(VariableSubType.NUMERIC))
                    defOperator.stringValuesToDouble(def);
                getDefMap().put(def.getName().toString(),def);
            }
        }
        catch(SQLException sqle){
            System.out.println("TableData | SQLException | generateAllDefVar | " + sqle.getMessage());
        }
    }
    
    private void updateColumnValue(StringBuilder colName, List<String> newColValues){
        int listIndex = 0;
        try{
            if(!this.dataSet.isFirst())
                this.dataSet.first();
            do{
                this.dataSet.updateString(colName.toString(), newColValues.get(listIndex));
                this.dataSet.updateRow();
                listIndex++;
            }
            while(this.dataSet.next());
            this.dataSet.first();
        }
        catch(SQLException sqle){
            System.out.println("TableData | SQLException | updateColumnValue | " + sqle.getMessage());
        }
        
    }
    
    public void validateMonoNullVariables(){
        //iterate for all variables and check if a variable is monotonic and disable the one that are.
        //get every data def to check the diff values
        for(Map.Entry<String, DataDef> entry : getDefMap().entrySet()){
            DataDef definition = entry.getValue();
            if(defOperator.isMonotinic(definition)){
                definition.setIsEnable(false);
                getDefMap().replace(entry.getKey(), definition);
            }
            if(defOperator.isManyMissing(definition)){
                definition.setIsEnable(false);
                getDefMap().replace(entry.getKey(), definition);
            }
        }	
    }
    
    //Combine two or more diff values of variable into one. The list of values is the one with the old values to update with the newKey
    //NOTE...the input of variable, diff values and new tag of the value is still missing
    public void combineVariableValues(String variable, String newKey, List<String> listValues){
        DataDef variableCol = getDefMap().get(variable);
        defOperator.combineVariableValues(variableCol, newKey, listValues);
        updateColumnCombinedValues(variableCol.getName().toString(), newKey, listValues);
        
    }
    
    //This method search for the list of new values in the data set table and if found then update the table with the new value
    //NOTE...the input of variable, diff values and new tag of the value is still missing
    private void updateColumnCombinedValues(String colName, String newValue, List<String> listNewValues){
        try{
            if(!this.dataSet.isFirst())
                this.dataSet.first();
            do{
                for(String value : listNewValues){
                    if(value.equals(this.dataSet.getString(colName))){
                        this.dataSet.updateString(colName.toString(), newValue);
                        this.dataSet.updateRow();
                    }
                }
            }
            while(this.dataSet.next());
        }
        catch(SQLException sqle){
            System.out.println("TableData | SQLException | updateColumnCombinedValues | " + sqle.getMessage());
        }
    }
    
    public static TableData cloneTable(TableData table){
        //Clone the original table so the updates can be done in the new table. NOTE..Not sure where to put this method
        TableData updatedTable = new TableData();
        MockResultSet dataSet = (MockResultSet)table.getDataSet().clone();
        HashMap<String,DataDef> dataDef = (HashMap<String,DataDef>)table.getDefMap().clone();
        dataSet = createUpdatedTable(dataSet, dataDef);
        updatedTable.setDataSet(dataSet);
        return updatedTable;
    }
    
    private static MockResultSet createUpdatedTable(MockResultSet dataSet, HashMap<String,DataDef> dataDef){
        //from the table delete the columns which are disabled in data set
        MockResultSet updatedDataSet = new MockResultSet("clonedDataSet");
        for(Map.Entry<String, DataDef> entry : dataDef.entrySet()){
            String col = entry.getKey();
            DataDef def = entry.getValue();
            if(def.getIsEnable()){
               updatedDataSet.addColumn(col, dataSet.getColumn(col));
            }
        }
        return updatedDataSet;
    }
    
    public static TableData deleteBlankValues(TableData table){
        //check every row in the table and if there is any blank value then delete the row
        MockResultSet data = table.getDataSet();
        int row = 1;
        List listRow = null;
        try{
            if(!data.isFirst())
                data.first();
            do{
                listRow = data.getRow(row);
                if(listRow.contains("")){
                    data.deleteRow();
                }
                row++;
            }
            while(data.next());
            
        }
        catch(SQLException sqle){
            System.out.println("Main | SQLException | deleteBlankValues | " + sqle.getMessage());
        }
        
        return table;
    }
    
    public boolean isDataEnough(){
        int enableCol = 0;
        int population = getDataSet().getRowCount();
        for(Map.Entry<String, DataDef> entry : getDefMap().entrySet()){
            if(entry.getValue().getIsEnable()){
                enableCol++;
            }
        }
        return defOperator.isPopulationEnough(population, enableCol);
    }
    
    private List<String> cleanVariable(List data){
        for(int i=0;i<data.size();i++){
            data.set(i, defOperator.cleanString(data.get(i).toString()));
        }
        return data;
    }
    
    private List<String> distVariables(List<String> data){
        return defOperator.getDistinctValues(data);
    }
    
    private HashMap<String,Integer> countDistVariables(List<String> dist, List<String> data){
        HashMap<String,Integer> countMap = new HashMap<String,Integer>();
        for(String distValue : dist){
            countMap.put(distValue, Collections.frequency(data, distValue));
        }
        return countMap;
    }
    
    public HashMap<String, DataDef> getDefMap(){
        return defMap;
    }
    
    public List getDataColumn(String name){
        return getDataSet().getColumn(name);
    }

    /**
     * @return the dataSet
     */
    public MockResultSet getDataSet() {
        return dataSet;
    }

    /**
     * @param dataSet the dataSet to set
     */
    public void setDataSet(MockResultSet dataSet) {
        this.dataSet = dataSet;
        this.dataSet.setResultSetType(MockResultSet.CONCUR_UPDATABLE);
        this.dataSet.setResultSetConcurrency(MockResultSet.CONCUR_UPDATABLE);
    }

    /**
     * @param defMap the defMap to set
     */
    public void setDefMap(HashMap<String,DataDef> defMap) {
        this.defMap = defMap;
    }
    
    public void deleteRowsWhitBlank(){
        
    }
    
}
