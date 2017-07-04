
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataInputRead;

import java.sql.*;
import java.math.*;
import javafx.beans.binding.StringBinding;
/**
 *
 * @author Emmanuel
 */
public class JDBAReader {
    
    private Connection connection = null;
    private String user = null;
    private String pass = null;
    private StringBuilder url = null;
    private String rdbms = null;
    private String port = null;
    private String dbName = null;
    private String hostname = null;
    private ResultSet queryResult = null;
    
    JDBAReader(){
        
    }
    
    public void createStatement(String query){
        Statement stmt = null;
        try{
            stmt = connection.createStatement();
            this.queryResult = stmt.executeQuery(query);
        }
        catch(SQLException se){
            System.out.println("JDBAReader|Stmt|SQLException: " + se.getMessage());
        }
        catch(Exception e){
            System.out.println("JDBAReader|Stmt|Exception: " + e.getMessage());
        }
        finally{
            try{ 
                stmt.close();
            }
            catch(SQLException se){
                System.out.println("JDBAReader|Stmt|SQLException: " + se.getMessage());
            }
        }
    }
    
    public void setConnection(){
        try{
            this.connection = DriverManager.getConnection(url.toString(), user, pass);
        }
        catch(SQLException se){
            System.out.println("Error: " + se.getMessage());
        }
    }
    
    public void registerDriver(){
        rdbms.toUpperCase();
        String driver = null;
        url = new StringBuilder("jdbc:");
        switch(this.rdbms){
            case "MYSQL":
                driver = "com.mysql.jdbc.Driver";
                url.append("mysql://").append(hostname).append("/").append(dbName);
                break;
            case "ORACLE":
                driver = "oracle.jdbc.driver.OracleDriver";
                url.append("oracle:thin:@").append(hostname).append(":").append(port).append(":").append(dbName);
                break;
            case "DB2":
                driver = "COM.ibm.db2.jdbc.net.DB2Driver";
                url.append("db2:").append(hostname).append(":").append(port).append("/").append(dbName);
                break;
            case "SYBASE":
                driver = "com.sybase.jdbc.SybDriver";
                url.append("sybase:Tds:").append(hostname).append(":").append(port).append("/").append(dbName);
                break;
        }
        try{
            Class.forName(driver);
        }
        catch(ClassNotFoundException cnfe){
            System.out.println("Error: Unable to load driver class");
        }/*
        catch(IllegalAccessException iae){
            System.out.println("Error: Access problem while loading");
        }
        catch(InstantiationException ie){
            System.out.println("Error: Unable to instantiate driver");
        }*/
    }
    
    
    
    public void setRdbms(String rdbms){
        this.rdbms = rdbms;
        this.rdbms.toUpperCase();
    }
    public String getRdbms(){
        return this.rdbms;
    }
    
    public void setUser(String user){
        this.user = user;
    }
    public String getUser(){
        return this.user;
    }
    
    public void setPass(String pass){
        this.pass = pass;
    }
    public String getPass(){
        return this.pass;
    }
    
    public void setConnection(Connection con){
        this.connection = con;
    }
    public Connection getConnection(){
        return this.connection;
    }
    
    public void setPort(String port){
        this.port = port;
    }
    public String getPort(){
        return this.port;
    }
    
    public void setDbName(String name){
        this.dbName = name;
    }
    public String getDbName(){
        return this.dbName;
    }
    
    public void setHostname(String name){
        this.hostname = name;
    }
    public String getHostname(){
        return this.hostname;
    }
    
    public void setQueryResult(ResultSet result){
        this.queryResult = result;
    }
    public ResultSet getQueryResult(){
        return this.queryResult;
    }
    
    public void closeDbCon(){
        try{
         this.connection.close();   
        }
        catch(SQLException se){
            System.out.println("Error: " + se.getMessage());
        }
    }
}

