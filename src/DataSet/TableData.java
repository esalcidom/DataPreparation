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
                for(String head : header){
                    mockResultSet.addColumn(head);
                }
                for(String[] row : data){
                    mockResultSet.addRow(row);
                }
                dataSet = mockResultSet;
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
        dataSet = data;
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
                colName = new StringBuilder(dataSet.getMetaData().getColumnName(i));
                columnData = dataSet.getColumn(colName.toString());
                columnResult = cleanVariable(columnData);
                distinctValues = distVariables(columnResult);
                distinctCount = countDistVariables(distinctValues,columnResult);
                def.setName(colName);
                def.setDistHead(distinctValues);
                def.setDistValues(distinctCount);
                def.setPopulation(columnResult.size());
                defOperator.defineVariableSubType(def);
                defMap.put(def.getName().toString(),def);
            }
        }
        catch(SQLException sqle){
            System.out.println("TableData | SQLException | generateAllDefVar | " + sqle.getMessage());
        }
    }
    
    public void validateMonoNullVariables(){
        //iterate for all variables and check if a variable is monotonic and disable the one that are.
        //get every data def to check the diff values
        for(Map.Entry<String, DataDef> entry : defMap.entrySet()){
            DataDef definition = entry.getValue();
            if(defOperator.isMonotinic(definition)){
                definition.setIsEnable(false);
                defMap.replace(entry.getKey(), definition);
            }
            if(defOperator.isManyMissing(definition)){
                definition.setIsEnable(false);
                defMap.replace(entry.getKey(), definition);
            }
        }	
    }
    
    //Combine two or more diff values of variable into one
    //NOTE...the input of variable, diff values and new tag of the value is still missing
    public void combineVariableValues(String variable, String newKey, List<String> listValues){
        DataDef variableCol = defMap.get(variable);
        defOperator.combineVariableValues(variableCol, newKey, listValues);
        
    }
    
    public boolean isDataEnough(){
        int enableCol = 0;
        int population = dataSet.getRowCount();
        for(Map.Entry<String, DataDef> entry : defMap.entrySet()){
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
        return dataSet.getColumn(name);
    }
    
}
