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
    
    /*
    public void generateDefVar(String name){
        if(!defMap.containsKey(name)){
            DataDef dataDef = new DataDef();
            ///process of identification
            //Intervalo de confianza
            //
            
            defMap.put(name, dataDef);
        }
    }*/
    
    public void generateAllDefVar(){
        //Automated Def all variables
        String s;
        DataDef def;
        StatOperator op;
        StringBuilder colName;
        try{
            for(int i=1;i<colSize;i++){
                def = new DataDef();
                colName = new StringBuilder(dataSet.getMetaData().getColumnName(i));
                //Need to check if at the moment to load the column now we calculate summary or first identify variable and need to do all the flow code in this method?
                op = new StatOperator(dataSet.getColumn(colName.toString()));
                def.setMean(op.calMean());
                //Las demas operaciones para definir en DataDef
                //
                //
                //Mapear Def con Variable
                defMap.put(colName.toString(), def);
            }
            
        }
        catch(SQLException sqle){
            System.out.println("TableData | SQLException | generateAllDefVar | " + sqle.getMessage());
        }
        
        
            
            
         
        
        
    }
    
}
