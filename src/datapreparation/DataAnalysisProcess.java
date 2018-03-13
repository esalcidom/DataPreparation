/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datapreparation;
import DataAnalysis.DataAnalyser;
import DataInputRead.*;
import DataSet.*;
import com.mockrunner.mock.jdbc.MockResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.*;
/**
 *This class start the 
 * @author Emmanuel
 */
public class DataAnalysisProcess {
    
    private String filePath;
    private DataAnalyser dataAnalyser;
    
    public DataAnalysisProcess(String path){
        filePath = path;
    }
    
    public void startAnalysis(){
        CsvReader reader = new CsvReader(filePath);
        reader.readCsv();
        TableData table = new TableData();
        TableData tableNoNull = new TableData();
        
        
        try{
            System.out.println("Set dada from csv...");
            table.setDataSetFromCSV(reader.getColumnHeader(),reader.getDataTable(), reader.getColumnSize());
            System.out.println("Define variables of raw data...");
            table.definitionOp();
            System.out.println("Validate Mono and Null Variables...");
            table.validateMonoNullVariables();
            if(table.isDataEnough()){
                //If user wants to combine two or more distinct values for a specific variable.
                //Then what we need is the selected variable and the diff values to combine.
                System.out.println("Create table without null or blank values...");
                tableNoNull = TableData.cloneTable(table);
                tableNoNull.deleteBlankValues();
                System.out.println("Define variables of free null values...");
                tableNoNull.definitionOp();
                System.out.println("Mapping alpha to numeric and numeric to categorical...");
                tableNoNull.createNumAndCatVariables();
                
                if(tableNoNull.isDataEnough()){
                    System.out.println("Summerize variables...");
                    tableNoNull.summerizeColumns();
                    System.out.println("Detecting outliers...");
                    tableNoNull.detectOutliers();
                    System.out.println("Creating contingency tables...");
                    tableNoNull.createContingencyTableList();
                    List<DataDef> alphaVariables = tableNoNull.getAlphaVariables();
                    List<DataDef> numericVariables = tableNoNull.getNumericVariableList();
                    //NOTE create pearson correlation and KendallTau take a lot of time
                    System.out.println("Create Pearson correlation...");
                    tableNoNull.createPearsonCorrelation(numericVariables);
                    System.out.println("Create Simple Regression...");
                    tableNoNull.createSimpleReg();
                    System.out.println("Create Anova test...");
                    tableNoNull.createAnova(numericVariables);
                    dataAnalyser = new DataAnalyser(tableNoNull);
                    dataAnalyser.startAnalyseTable();
                    dataAnalyser.startRelationAnalysis();
                }
                else{
                    System.out.println("Not enough data to analyse please check");
                }
            }
            else{
                System.out.println("Data free of null and blank values are not enough data to analyse please check");
            }
            
        }
        catch(Exception e){
            System.out.println("DataAnalysisProcess | startAnalysis | Exception | " + e.getMessage());
         }
        finally{
            dataAnalyser.closeFile();
        }
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
