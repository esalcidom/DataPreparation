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
    private List<String[]> dataTable;
    
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
                setDataTable(reader.readAll());
                setColumnSize(getDataTable().get(0).length);
                if(hasHeaders(getDataTable()))
                    setListHeader(getDataTable());
                else
                    setDefaultHeader();
            }
            catch(Exception e){
                System.out.println("CsvReader | Read | Exception | " + e.getMessage());
            }
            finally{
                try{
                    reader.close();
                }
                catch(Exception e){
                System.out.println("CsvReader | Read | Exception | " + e.getMessage());
                }
            }
        }
    }
    
    private void setListHeader(List<String[]> table){
        setColumnHeader(table.get(0));
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
        setColumnHeader(new String[getColumnSize()]);
        for(int i=0;i<getColumnHeader().length;i++){
            getColumnHeader()[i] = colName + i;
        }
    }

    /**
     * @return the dataTable
     */
    public List<String[]> getDataTable() {
        return dataTable;
    }

    /**
     * @param dataTable the dataTable to set
     */
    public void setDataTable(List<String[]> dataTable) {
        this.dataTable = dataTable;
    }

    /**
     * @return the columnHeader
     */
    public String[] getColumnHeader() {
        return columnHeader;
    }

    /**
     * @param columnHeader the columnHeader to set
     */
    public void setColumnHeader(String[] columnHeader) {
        this.columnHeader = columnHeader;
    }

    /**
     * @return the columnSize
     */
    public int getColumnSize() {
        return columnSize;
    }

    /**
     * @param columnSize the columnSize to set
     */
    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }
    
}
