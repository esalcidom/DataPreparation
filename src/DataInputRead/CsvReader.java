/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataInputRead;

import com.opencsv.*;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Emmanuel
 */
public class CsvReader {
    
    private CSVReader reader;
    private String[] columnHeader;
    
    public void readCsv(String path){        
        String[] nextLine;
        if(reader != null){
            try{
                List<String[]> dataTable = reader.readAll();
                if(hasHeaders(dataTable)){
                    setListHeader(dataTable);
                }
            }
            catch(Exception e){
                System.out.println("[CsvReader]: " + e.getMessage());
            }
            finally{
                try{
                    reader.close();
                }
                catch(Exception e){
                System.out.println("[CsvReader]: " + e.getMessage());
                }
            }
        }
    }
    
    private void setListHeader(List<String[]> table){
        columnHeader = table.get(0);
        table.remove(0);
    }
    
    private boolean hasHeaders(List<String[]> table){
        String[] firstLine;
        firstLine = table.get(0);
        for(String head : firstLine){
            if(!StringUtils.isNumericSpace(head))
                continue;
            else
                return false;
        }
        return true;
    }
    
    private void setDefaultHeader(){
        String colName = "Column";
        for(int i=0;i<columnHeader.length;i++){
            columnHeader[i] = colName + i;
        }
    }
    
}
