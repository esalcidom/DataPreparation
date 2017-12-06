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
        bufferedWriter.write("------------------------------");
        bufferedWriter.newLine();
    }
    
    public void printBlankLine() throws IOException{
        bufferedWriter.newLine();
    }
    
    public void printDistinctMap(Map<String,Integer> map) throws IOException{
        String line = "";
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            line += "   |   " + entry.getKey() + " -> " + entry.getValue() + "  |   ";
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
    
    public void printIntegerList(List<Integer> list) throws IOException{
        String line = "";
        for(Integer value:list){
            line += "    " + value.toString();
        }
        writeLine(line);
    }
    
    public void printDoubleList(List<Double> list) throws IOException{
        String line = "";
        for(Double value:list){
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
    
}
