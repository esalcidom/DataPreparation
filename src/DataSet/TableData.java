/*
 * Intention of this class is to handle any input reader and parse to object DefaultTableModel
 * Also to create DataDef objects I think how to map them.  
 */
package DataSet;
import com.mockrunner.mock.jdbc.MockResultSet;
import java.sql.*;
import java.util.*;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
/**
 *
 * @author Emmanuel
 */
public class TableData {
    
    private MockResultSet dataSet;
    private HashMap<String,DataDef> defMap;
    private List<Integer> indexDeletedList;
    private int colSize = 0;
    private DefOperator defOperator;
    private StatOperator statOperator;
    private List<ContingencyTableDef> contingencyTableList;
    private List<PearsonCorrelation> pearsonCorrelationList;
    private List<KendallTau> kendallCorrelation;
    private List<SimpleReg> simpleRegList;
    private List<AnovaOneWay> anovaList;
    private List<KMeans> kMeansList;
    
    public TableData(){
        defMap = new HashMap<String,DataDef>();
        defOperator = new DefOperator();
        statOperator = new StatOperator();
        indexDeletedList = new ArrayList<Integer>();
        pearsonCorrelationList = new ArrayList<PearsonCorrelation>();
        kendallCorrelation = new ArrayList<KendallTau>();
        simpleRegList = new ArrayList<SimpleReg>();
        anovaList = new ArrayList<AnovaOneWay>();
    }
    
    public void setDataSetFromCSV(String[] header, List<String[]> data, int dataColumnSize) throws Exception{
        //Read from the csv file to table
        if(header != null && data!= null){
            setColSize(dataColumnSize);
            if(header.length == getColSize())
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
            for(int i=1;i<=getColSize();i++){
                def = new DataDef();
                colName = new StringBuilder(getDataSet().getMetaData().getColumnName(i));
                def.setName(colName);
                columnData = this.getDataSet().getColumn(colName.toString());
                columnResult = cleanVariable(columnData);
                distinctValues = distVariables(columnResult);
                distinctCount = countDistVariables(distinctValues,columnResult);
                def.setOriginalValues(columnResult);
                updateColumnValue(colName,columnResult);
                defOperator.defineVariableSubType(def,columnData);
                def.setDistHead(distinctValues);
                def.setDistValues(distinctCount);
                def.setPopulation(columnResult.size());
                if(def.getVarSubType().equals(VariableSubType.NUMERIC)){
                    defOperator.stringValuesToDouble(def);
                }
                defOperator.defineVariableType(def);
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
        dataSet = createEnableColTable(dataSet, dataDef);
        updatedTable.setDataSet(dataSet);
        updatedTable.setColSize(dataSet.getColumnCount());
        updatedTable.setDefMap(dataDef);
        return updatedTable;
    }
    
    private static MockResultSet createEnableColTable(MockResultSet dataSet, HashMap<String,DataDef> dataDef){
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
    
    //Create categorical values for Numerical variable and numerical values from Alpha variables
    public void createNumAndCatVariables(){
        for(Map.Entry<String, DataDef> entry : defMap.entrySet()){
            if(entry.getValue().getVarSubType().equals(VariableSubType.NUMERIC))
            defOperator.mapToCategorical(entry.getValue());
        else if(entry.getValue().getVarSubType().equals(VariableSubType.ALPHA) || entry.getValue().getVarSubType().equals(VariableSubType.ALPHANUMERIC))
            defOperator.mapToNumerical(entry.getValue());
        }
    }

    public void createContingencyTableList(){
        if(contingencyTableList == null)
            contingencyTableList = new ArrayList<ContingencyTableDef>();
        List<DataDef> dataDefList = new ArrayList<DataDef>();
        for(Map.Entry<String, DataDef> entry : defMap.entrySet()){
            if((entry.getValue().getVarType().contains(VariableType.CATEGORICAL) || entry.getValue().getCategoricalValue() != null) && entry.getValue().getIsEnable() == true)
                dataDefList.add(entry.getValue());
            else
                continue;
        }
        //Iterate with List all the posible combinations to create contingency tables
        for(int i=0;i<dataDefList.size();i++){
            for(int j=i+1;j<dataDefList.size();j++){
                ContingencyTableDef contiTable = new ContingencyTableDef(dataDefList.get(i).getCategoricalValue(), dataDefList.get(j).getCategoricalValue(),dataDefList.get(i).getName().toString(),dataDefList.get(j).getName().toString());
                contiTable.setRowMap(dataDefList.get(i).getRemapValues());
                contiTable.setColMap(dataDefList.get(j).getRemapValues());
                contiTable.createContingencyTable();
                statOperator.calculateContingencySummary(contiTable);
                contingencyTableList.add(contiTable); 
            }
        }
    }
    
    public List<DataDef> getNumericVariableList(){
        List<DataDef> dataDefList = new ArrayList<DataDef>();
        for(Map.Entry<String, DataDef> entry : defMap.entrySet()){
            if((entry.getValue().getVarSubType().equals(VariableSubType.NUMERIC) || entry.getValue().getNumericValues() != null) && entry.getValue().getIsEnable() == true)
                dataDefList.add(entry.getValue());
            else
                continue;
        }
        return dataDefList;
    }
    
    public List<DataDef> getAlphaVariables(){
        List<DataDef> dataDefList = new ArrayList<DataDef>();
        for(Map.Entry<String, DataDef> entry : defMap.entrySet()){
            if(entry.getValue().getVarSubType().equals(VariableSubType.ALPHA) || (entry.getValue().getVarSubType().equals(VariableSubType.ALPHANUMERIC) || entry.getValue().getNumericValues() != null) && entry.getValue().getIsEnable() == true)
                dataDefList.add(entry.getValue());
            else
                continue;
        }
        return dataDefList;
    }
    
    public void createKendallTau(List<DataDef> dataDefList){
        for(int i=0;i<dataDefList.size();i++){
            for(int j=i+1;j<dataDefList.size();j++){
                KendallTau kendallT = new KendallTau(dataDefList.get(i),dataDefList.get(j));
                statOperator.calculateKendallTauSummary(kendallT);
                kendallCorrelation.add(kendallT);
            }
        }
    }
    
    public void createKMeans(List<DataDef> dataDefList){
        for(int i=0;i<dataDefList.size();i++){
            KMeans kMean = new KMeans(dataDefList.get(i));
            statOperator.calculateKMeanSummary(kMean);
            kMeansList.add(kMean);
        }
    }
    
    public void createPearsonCorrelation(List<DataDef> dataDefList){
        
        for(int i=0;i<dataDefList.size();i++){
            for(int j=i+1;j<dataDefList.size();j++){
                DataSet.PearsonCorrelation pearsonC = new DataSet.PearsonCorrelation(dataDefList.get(i),dataDefList.get(j));
                statOperator.calculatePearsonSummary(pearsonC);
                pearsonCorrelationList.add(pearsonC);
            }
        }
    }
    
    public void createSimpleReg(List<DataDef> dataDefList){
        for(int i=0;i<dataDefList.size();i++){
            for(int j=i+1;j<dataDefList.size();j++){
                SimpleReg simpleReg = new SimpleReg(dataDefList.get(i),dataDefList.get(j));
                statOperator.calculateSimpleRegressionSummary(simpleReg);
                simpleRegList.add(simpleReg);
            }
        }
    }
    
    public void createAnova(List<DataDef> dataDefList){
        for(int i=0;i<dataDefList.size();i++){
            for(int j=i+1;j<dataDefList.size();j++){
                AnovaOneWay anova = new AnovaOneWay(dataDefList.get(i),dataDefList.get(j));
                statOperator.calculateAnovaSummary(anova);
                anovaList.add(anova);
            }
        }
    }
    
    public void detectOutliers(){
        for(Map.Entry<String, DataDef> entry : defMap.entrySet()){
            if(entry.getValue().getIsEnable()){
                double low = entry.getValue().getLowIqr();
                double high = entry.getValue().getHighIqr();
                List<Integer> index = new ArrayList<Integer>();
                List<Double> variableData = new ArrayList<Double>();
                if((entry.getValue().getVarSubType().equals(VariableSubType.NUMERIC)))
                    variableData = DefOperator.generateStringListToDouble(entry.getValue().getOriginalValues());  
                else if(entry.getValue().getVarSubType().equals(VariableSubType.ALPHA) || entry.getValue().getVarSubType().equals(VariableSubType.ALPHANUMERIC))
                    variableData = entry.getValue().getNumericValues();
                
                for(int i=0;i<variableData.size();i++){
                    if(variableData.get(i)<low)
                        index.add(i);
                    else if(variableData.get(i)>high)
                        index.add(i);
                }
                entry.getValue().setIndexOutliersList(index);
            }
        }
    }
    
    public void deleteBlankValues(){
        //check every row in the table and if there is any blank value then delete the row
        MockResultSet data = this.getDataSet();
        MockResultSet noBlankDataSet = new MockResultSet("clonedNoBlankDataSet");
        setDataSetColName(noBlankDataSet);
        int row = 1;
        List listRow = null;
        try{
            if(!data.isFirst())
                data.first();
            if(!noBlankDataSet.isFirst())
                noBlankDataSet.first();
            do{
                listRow = data.getRow(row);
                if(!listRow.contains("")){
                    noBlankDataSet.addRow(listRow);
                    noBlankDataSet.next();
                    row++;
                    continue;
                }
                else{
                    indexDeletedList.add(row);
                }
                row++;
            }
            while(data.next());
            this.setDataSet(noBlankDataSet);
            //Create original data from new dataSet
        }
        catch(SQLException sqle){
            System.out.println("Main | SQLException | deleteBlankValues | " + sqle.getMessage());
        }
    }
    
    private void setDataSetColName(MockResultSet dataSet){
        HashMap<String,DataDef> dataDef = (HashMap<String,DataDef>)this.getDefMap();
        for(Map.Entry<String, DataDef> entry : dataDef.entrySet()){
            if(entry.getValue().getIsEnable()){
                String col = entry.getKey();
                dataSet.addColumn(col);
            }
        }
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
            try{
                data.set(i, defOperator.cleanString(data.get(i).toString()));
            }
            catch(Exception e){
                continue;
            }
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
    
    public void summerizeColumns(){
        for(Map.Entry<String, DataDef> entry : getDefMap().entrySet()){
            if(entry.getValue().getIsEnable())
                defOperator.summerizeData(entry.getValue());
        }
    }

    /**
     * @return the colSize
     */
    public int getColSize() {
        return colSize;
    }

    /**
     * @param colSize the colSize to set
     */
    public void setColSize(int colSize) {
        this.colSize = colSize;
    }

    /**
     * @return the contingencyTableList
     */
    public List<ContingencyTableDef> getContingencyTableList() {
        return contingencyTableList;
    }

    /**
     * @param contingencyTableList the contingencyTableList to set
     */
    public void setContingencyTableList(List<ContingencyTableDef> contingencyTableList) {
        this.contingencyTableList = contingencyTableList;
    }

    /**
     * @return the statOperator
     */
    public StatOperator getStatOperator() {
        return statOperator;
    }

    /**
     * @param statOperator the statOperator to set
     */
    public void setStatOperator(StatOperator statOperator) {
        this.statOperator = statOperator;
    }

    /**
     * @return the pearsonCorrelationList
     */
    public List<PearsonCorrelation> getPearsonCorrelationList() {
        return pearsonCorrelationList;
    }

    /**
     * @param pearsonCorrelationList the pearsonCorrelationList to set
     */
    public void setPearsonCorrelationList(List<PearsonCorrelation> pearsonCorrelationList) {
        this.pearsonCorrelationList = pearsonCorrelationList;
    }

    /**
     * @return the kendallCorrelation
     */
    public List<KendallTau> getKendallCorrelation() {
        return kendallCorrelation;
    }

    /**
     * @param kendallCorrelation the kendallCorrelation to set
     */
    public void setKendallCorrelation(List<KendallTau> kendallCorrelation) {
        this.kendallCorrelation = kendallCorrelation;
    }

    /**
     * @return the simpleRegList
     */
    public List<SimpleReg> getSimpleRegList() {
        return simpleRegList;
    }

    /**
     * @param simpleRegList the simpleRegList to set
     */
    public void setSimpleRegList(List<SimpleReg> simpleRegList) {
        this.simpleRegList = simpleRegList;
    }

    /**
     * @return the anovaList
     */
    public List<AnovaOneWay> getAnovaList() {
        return anovaList;
    }

    /**
     * @param anovaList the anovaList to set
     */
    public void setAnovaList(List<AnovaOneWay> anovaList) {
        this.anovaList = anovaList;
    }

}
