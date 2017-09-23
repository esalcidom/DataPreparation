/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datapreparation;
import DataInputRead.*;
import DataSet.*;
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
        
        String path = "C:\\Users\\Emmanuel\\Documents\\Maestria\\CSVSamples\\farm_gdl_cp.csv";
        CsvReader reader = new CsvReader(path);
        reader.readCsv();
        TableData table = new TableData();
        DefOperator defOp = new DefOperator();
        try{
            table.setDataSetFromCSV(reader.getColumnHeader(),reader.getDataTable(), reader.getColumnSize());
            table.definitionOp();
            defOp.isPopulationEnough(defOp.validateVariables(table));
        }
        catch(Exception e){
            System.out.println("Main | SQLException | " + e.getMessage());
         }
        
        
    }
    
}
