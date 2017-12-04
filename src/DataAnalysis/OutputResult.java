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
        bufferedWriter.write(line);
        bufferedWriter.newLine();
    }
    
    public void closeFile() throws IOException{
        bufferedWriter.close();
        outputStream.close();
    }
    
    private void flushToFile() throws IOException{
        bufferedWriter.flush();
        outputStream.flush();
    }
    
    private void printSeparator() throws IOException{
        bufferedWriter.write("******************************************************************");
        bufferedWriter.newLine();
    }
    
    private void printSubSeparator() throws IOException{
        bufferedWriter.write("------------------------------");
        bufferedWriter.newLine();
    }
    
    private void printBlankLine() throws IOException{
        bufferedWriter.newLine();
    }
    
    private void printDistinctMap(Map<String,Integer> map) throws IOException{
        String line = "";
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            line += "   |   " + entry.getKey() + " -> " + entry.getValue() + "  |   ";
        }
        writeLine(line);
    }
    
    private void printRemapMap(Map<String,String> map) throws IOException{
        for(Map.Entry<String, String> entry : map.entrySet()){
            writeLine(entry.getKey() + " - " + entry.getValue());
        }
    }
    
    private void printStringList(List<String> list) throws IOException{
        String line = "";
        for(String value:list){
            line = "    " + value.toString();
        }
        writeLine(line);
    }
    
    private void printIntegerList(List<Integer> list) throws IOException{
        String line = "";
        for(Integer value:list){
            line = "    " + value.toString();
        }
        writeLine(line);
    }
    
    private void printDoubleList(List<Double> list) throws IOException{
        String line = "";
        for(Double value:list){
            line = "    " + value.toString();
        }
        writeLine(line);
    }
    
    private void printDoubleArray(double[] array) throws IOException{
        String line = "";
        for(double value:array){
            line += "   " + value;
        }
        writeLine(line);
    }
    
    public void writeVariableSummary(DataDef data){
        try{
            printSeparator();
            writeLine("Variable Name: " + data.getName().toString());
            printBlankLine();
            writeLine("Variable Types: ");
            for(VariableType type:data.getVarType())
                writeLine(type.toString());
            if(data.getMapedType() != null)
                writeLine("Variable maped to: " + data.getMapedType().toString());
            if(data.getVarSubType() != null)
                writeLine("Variable subtype: " + data.getVarSubType().toString());
            writeLine("Mean: " + data.getMean());
            writeLine("Mode: ");
            printDoubleArray(data.getMode());
            writeLine("Geometric Mean:" + data.getGeometricMean());
            writeLine("Median: " + data.getMedian());
            writeLine("Range: " + data.getRange());
            writeLine("Min value: " + data.getMin());
            writeLine("Max value: " + data.getMax());
            writeLine("Normilize Data:");
            printDoubleArray(data.getNormilizeData());
            writeLine("Quartile 1: " + data.getQ1());
            writeLine("Quartile 3: " + data.getQ3());
            writeLine("Interquartile Range: " + data.getIqr());
            writeLine("Low Interquartile Range: " + data.getLowIqr());
            writeLine("High Interquartile Range: " + data.getHighIqr());
            writeLine("Skewness: " + data.getSkewness());
            writeLine("Standar Deviation: " + data.getStandrDev());
            writeLine("Standar Error: " + data.getStandrErr());
            writeLine("Low Quartile Not by lib: " + data.getLowQuartile());
            writeLine("Mid Quartile Not by lib: " + data.getMidQuartile());
            writeLine("Up Quartile Not by lib: " + data.getUpQuartile());
            writeLine("Range Quartile: " + data.getRangeQuartile());
            writeLine("Variance: " + data.getVariance());
            writeLine("Pupulation Variance: " + data.getPopulationVariance());
            writeLine("Coefficient Variation: " + data.getCoefficientVariation());
            writeLine("Kurtosis: " + data.getKurtosis());
            writeLine("Minimum difference between values: " + data.getMinDif());
            writeLine("Population: " + data.getPopulation());
            writeLine("Is this variable important to set? " + data.getIsEnable());
            printBlankLine();
            printSubSeparator();
            writeLine("Distinct values: ");
            printDistinctMap(data.getDistValues());
            if(data.getIndexOutliersList() != null){
                writeLine("Index of possible outliers: ");
                printIntegerList(data.getIndexOutliersList());
            }
            if(data.getNumericValues() != null){
                writeLine("Numeric Values: ");
                printDoubleList(data.getNumericValues());
            }
            writeLine("Original Values: ");
            printStringList(data.getOriginalValues());
            if(data.getCategoricalValue() != null){
                writeLine("Categorical Values: ");
                printStringList(data.getCategoricalValue());
            }
            if(data.getRemapValues() != null){
                writeLine("Remap Values: ");
                printRemapMap(data.getRemapValues());
            }
            printSubSeparator();
        }
        catch(IOException iOE){
            System.out.println("DataAnalyser | IOException | File not found " + iOE.getMessage());
        }
        finally{
            try{
                flushToFile();
            }
            catch(IOException iOE){
                System.out.println("DataAnalyser | IOException | File not found " + iOE.getMessage());
            }
        }
        
    }
}
