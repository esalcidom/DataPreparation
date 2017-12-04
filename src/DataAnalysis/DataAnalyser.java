/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAnalysis;

import DataSet.DataDef;
import DataSet.TableData;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Emmanuel
 */
public class DataAnalyser {
    
    private OutputResult outputResult;
    private TableData tableData;
    
    public DataAnalyser(TableData table){
        outputResult = OutputResult.getInstance();
        tableData = table;
    }
    
    public void startAnalyseTable(){
        
        for(Map.Entry<String, DataDef> entry : tableData.getDefMap().entrySet()){
            
                DataDef dataDef = entry.getValue();
                outputResult.writeVariableSummary(dataDef);
        }
        try{
            outputResult.closeFile();
        }
        catch(IOException iOE){
                System.out.println("DataAnalyser | startAnalyseTable | IOException | " + iOE.getMessage());
        }
        
    }
    
}
