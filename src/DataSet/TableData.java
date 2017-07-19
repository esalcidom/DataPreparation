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
    
    private ResultSet dataSet;
    private HashMap<String,DataDef> defMap;
    
    public void setDataSetFromCSV(String[] header, List<String[]> data, int dataColumnSize) throws Exception{
        if(header != null && data!= null){
            if(header.length == dataColumnSize)
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
    
    public void setDataSetFromDB(ResultSet data){
        dataSet = data;
    }
    
    private void generateDefVar(String name){
        if(!defMap.containsKey(name)){
            DataDef dataDef = new DataDef();
            ///process of identification
            
            defMap.put(name, dataDef);
        }
    }
    
}
