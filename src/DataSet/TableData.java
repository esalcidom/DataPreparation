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
    
    private MockResultSet dataSet;
    private HashMap<String,DataDef> defMap;
    private int colSize = 0;
    
    public void setDataSetFromCSV(String[] header, List<String[]> data, int dataColumnSize) throws Exception{
        if(header != null && data!= null){
            colSize = dataColumnSize;
            if(header.length == colSize)
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
    
    public void setDataSetFromDB(MockResultSet data){
        dataSet = data;
    }
    
    public void cleanDefOp(){
        //Automated Def all variables
        StringBuilder colName;
        List columnData;
        List<String> columnResult;
        List<String> distinctValues;
        Map<String,Integer> distinctCount;
        DataDef def;
        
        try{
            for(int i=1;i<colSize;i++){
                def = new DataDef();
                colName = new StringBuilder(dataSet.getMetaData().getColumnName(i));
                columnData = dataSet.getColumn(colName.toString());
                columnResult = cleanVariable(columnData);
                distinctValues = distVariables(columnResult);
                distinctCount = countDistVariables(distinctValues,columnResult);
                def.setName(colName);
                def.setVariableData(columnResult);
                def.setDistHead(distinctValues);
                def.setDistValues(distinctCount);
            }
        }
        catch(SQLException sqle){
            System.out.println("TableData | SQLException | generateAllDefVar | " + sqle.getMessage());
        }
    }
    
    //Mapear Def con Variable
        //defMap.put(colName.toString(), def);
    
    
    private List<String> cleanVariable(List data){
        DefOperator op = new DefOperator();
        //Need to clean the list string one by one
        for(int i=0;i<data.size();i++){
            data.set(i, op.cleanString(data.get(i).toString()));
        }
        return data;
    }
    
    private List<String> distVariables(List<String> data){
        DefOperator op = new DefOperator();
        return op.getDistinctValues(data);
    }
    
    private HashMap<String,Integer> countDistVariables(List<String> dist, List<String> data){
        HashMap<String,Integer> countMap = new HashMap<String,Integer>();
        for(String distValue : dist){
            countMap.put(distValue, Collections.frequency(data, distValue));
        }
        return countMap;
    }
    
}
