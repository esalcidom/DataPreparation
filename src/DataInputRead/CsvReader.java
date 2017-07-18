/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataInputRead;

import com.opencsv.*;
import java.io.*;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Emmanuel
 */
public class CsvReader {
    
    private CSVReader reader;
    private String[] columnHeader;
    private int columnSize;
    
    public CsvReader(String path){
        try{
            reader = new CSVReader(new FileReader(path), ',');
        }
        catch(Exception e){
            System.out.println("[CsvReader]: " + e.getMessage());
        }
    }
    
    public void readCsv(){        
        String[] nextLine;
        if(reader != null){
            try{
                List<String[]> dataTable = reader.readAll();
                columnSize = dataTable.get(0).length;
                if(hasHeaders(dataTable))
                    setListHeader(dataTable);
                else
                    setDefaultHeader();
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
        String colName = "Column ";
        columnHeader = new String[columnSize];
        for(int i=0;i<columnHeader.length;i++){
            columnHeader[i] = colName + i;
        }
    }
    
}
