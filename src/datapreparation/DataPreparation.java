/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datapreparation;
import DataInputRead.*;
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
        
    }
    
}
