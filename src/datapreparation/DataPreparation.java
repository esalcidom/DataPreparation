/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datapreparation;
import DataInputRead.*;
import DataSet.*;
import com.mockrunner.mock.jdbc.MockResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.*;
/**
 *
 * @author Emmanuel
 */
public class DataPreparation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /**
        * @param args the command line arguments
        *
        * PROCESS TO DEFINE AND VALIDATE
        * * 1. Define the data set by definitOp method which only gets the name, values and distinct elements of columns, sub type
        * 1.5 User need to define if the distinct values found are correct NOTE...The method to combine values works now is needed the input from user
        * * 2. Define if the data set is enough to do the investigation.
        * * 3. Handle null, empty or wrong data and if necessary identify the rows that should be deleted. NOTE..right now the way to handle is to delete the rows
        * 3.5. User need to define if the rows with wrong data should be deleted or handle
        * * 4. Define one more time if the data set is enough to do the investigation.
        * * 4. Define the types of variables for future statistical analysis
        * * 4.5 User need to define if the columns types are define correctly.
        * * 5 Creates new table which will define user if it is the correct one to use in the investigation
        * 5.5 User need to define if empty values need to be changed or just to remove them.
        * * 6. Converting Text Variables to Numerical variables. assign a Remap value
        * * 7. Converting Numerical variables to Categorical variables
        * 8. Make sure that user is agree with the variables
        * * BEGINNING OF THE STATISTICAL ANALYSIS
        * * 8. Generate Statistic Summary for each variable
        * * 9. Create Contingency tables for all variables
        * 
        * ///////NOTE: NEED TO CHECK HOW TO HANDLE STRING VARIABLES TO CATEGORY VARIABLES
        */
        
        String path = "C:\\Users\\Emmanuel\\Documents\\Maestria\\CSVSamples\\farm_gdl_cp_1.csv";
        CsvReader reader = new CsvReader(path);
        reader.readCsv();
        TableData table = new TableData();
        TableData tableNoNull = new TableData();
        
        try{
            table.setDataSetFromCSV(reader.getColumnHeader(),reader.getDataTable(), reader.getColumnSize());
            table.definitionOp();
            table.validateMonoNullVariables();
            if(table.isDataEnough()){
                //defOp.isPopulationEnough(defOp.validateVariables(table));
                //If user wants to combine two or more distinct values for a specific variable.
                //Then what we need is the selected variable and the diff values to combine.
                //NOTE..Think if we better create a class TableOperator
                //NOTE..Think if we can do a search of blank values and delete the complete row
                tableNoNull = TableData.cloneTable(table);
                tableNoNull.deleteBlankValues();
                ///NOTA NO SE ESTA GENERANDO BIEN LOS VALORES STRING Y NUMERICOS
                tableNoNull.definitionOp();
                tableNoNull.createNumAndCatVariables();
                //we can check with table with no null or blank is ok with user approval
                //then implement the edition of the original table in focus with the rows with issues
                if(tableNoNull.isDataEnough()){
                    tableNoNull.summerizeColumns();
                }
                else{
                    System.out.println("Not enough Data please check");
                }
            }
            else{
                System.out.println("Not enough Data please check");
            }
            
            
        }
        catch(Exception e){
            System.out.println("Main | Exception | " + e.getMessage());
         }
    }
    
}
