/*
 * Intention of this class is to handle any input reader and parse to object DefaultTableModel
 * Also to create DataDef objects I think how to map them.  
 */
package DataSet;
import com.mockrunner.mock.jdbc.*;
import java.sql.*;
import java.util.*;
/**
 *
 * @author Emmanuel
 */
public class TableData {
    
    private ResultSet dataSet;
    
    public void setDataSetFromCSV(String[] header, List<String[]> data) throws Exception{
        if(header != null && data!= null){
            if(header.length == data.size())
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
    
}
