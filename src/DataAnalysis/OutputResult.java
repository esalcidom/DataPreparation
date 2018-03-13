/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAnalysis;

import DataSet.DataDef;
import DataSet.VariableType;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Emmanuel
 */
public class OutputResult {
    
    private String pathFile = "./outputFile.txt";
    private File outputFile;
    private FileOutputStream outputStream;
    private BufferedWriter bufferedWriter;
    private static OutputResult outputResult;
    
    private OutputResult(){
        try{
            outputFile = new File(pathFile);
            outputStream = new FileOutputStream(outputFile);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        }
        catch(FileNotFoundException fNFE){
            System.out.println("OutputResult | FileNotFoundException | File not created " + fNFE.getMessage());
        }
    }
    
    public static OutputResult getInstance(){
        if(outputResult == null)
            outputResult = new OutputResult();
        return outputResult;
    }
    
    public void writeLine(String line) throws IOException{
        bufferedWriter.write(" " + line);
        bufferedWriter.newLine();
    }
    
    public void closeFile() throws IOException{
        bufferedWriter.close();
        outputStream.close();
    }
    
    public void flushToFile() throws IOException{
        bufferedWriter.flush();
        outputStream.flush();
    }
    
    public void printSeparator() throws IOException{
        bufferedWriter.write("******************************************************************");
        bufferedWriter.newLine();
    }
    
    public void printSubSeparator() throws IOException{
        bufferedWriter.write("----------------------------------------------------");
        bufferedWriter.newLine();
    }
    
    public void printContingencyLabel() throws IOException{
        printSubSeparator();
        printSeparator();
        bufferedWriter.write("      CONTINGENCY TABLE       ");
        bufferedWriter.newLine();
        printSeparator();
        printSubSeparator();
    }
    
    public void printPearsonLabel() throws IOException{
        printSubSeparator();
        printSeparator();
        bufferedWriter.write("      Pearson Correlation Matrix       ");
        bufferedWriter.newLine();
        printSeparator();
        printSubSeparator();
    }
    
    public void printKTauLabel() throws IOException{
        printSubSeparator();
        printSeparator();
        bufferedWriter.write("      Kendall Tau Correlation Matrix       ");
        bufferedWriter.newLine();
        printSeparator();
        printSubSeparator();
    }
    
    public void printAnovaLabel() throws IOException{
        printSubSeparator();
        printSeparator();
        bufferedWriter.write("      Anova One Way       ");
        bufferedWriter.newLine();
        printSeparator();
        printSubSeparator();
    }
    
    public void printSimpleRegLabel() throws IOException{
        printSubSeparator();
        printSeparator();
        bufferedWriter.write("      Simple Regression       ");
        bufferedWriter.newLine();
        printSeparator();
        printSubSeparator();
    }
    
    public void printBlankLine() throws IOException{
        bufferedWriter.newLine();
    }
    
    public void printDistinctMap(Map<String,Integer> map) throws IOException{
        String line = "";
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            line += entry.getKey() + " -> " + entry.getValue() + "\n";
        }
        writeLine(line);
    }
    
    public void printRemapMap(Map<String,String> map) throws IOException{
        for(Map.Entry<String, String> entry : map.entrySet()){
            writeLine(entry.getKey() + " - " + entry.getValue());
        }
    }
    
    public void printStringList(List<String> list) throws IOException{
        String line = "";
        for(String value:list){
            line += "    " + value.toString();
        }
        writeLine(line);
    }
    
    public void printList(List list) throws IOException{
        String line = "";
        for(Object value:list){
            line += "    " + value.toString();
        }
        writeLine(line);
    }
    
    public void printDoubleArray(double[] array) throws IOException{
        String line = "";
        for(double value:array){
            line += "   " + value;
        }
        writeLine(line);
    }
    
    public void printDouble2DArray(double[][] array) throws IOException{
        String line = "";
        int row = array.length;
        int col = array[0].length;
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                line += "   " + array[i][j];
            }
            writeLine(line);
            line = "";
        }
        
    }
    
}
