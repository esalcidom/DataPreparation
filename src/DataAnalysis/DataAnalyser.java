/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAnalysis;

import DataSet.DataDef;
import DataSet.TableData;
import DataSet.VariableType;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Emmanuel
 */
public class DataAnalyser {
    
    private OutputResult outputResult;
    private TableData tableData;
    
    public DataAnalyser(TableData table){
        outputResult = OutputResult.getInstance();
        tableData = table;
    }
    
    public void startAnalyseTable(){
        
        for(Map.Entry<String, DataDef> entry : tableData.getDefMap().entrySet()){
                DataDef dataDef = entry.getValue();
                writeVariableSummary(dataDef);
        }
        try{
            outputResult.closeFile();
        }
        catch(IOException iOE){
                System.out.println("DataAnalyser | startAnalyseTable | IOException | " + iOE.getMessage());
        }   
    }
    
    private String analyseSymmetry(double skewness, double kurtosis){
        String line = "";
        if(skewness == 0)
            line += "The data is symetric and with normal distribution. ";
        else if(skewness < -0.5d)
            line += "The data is not symetric and the left tail is long relative to right tail. ";
        else if(skewness > 0.5d)
            line += "The data is not symetric and the right tail is long relative to left tail. ";
        if(kurtosis > 0.5d)
            line += "Also the tails of the data are heavy or large";
        else if(kurtosis < -0.5)
            line += "Also the tails of the data are light or small";
        return line;
    }
    
    private String analyseSpread(double stDev, double smallDif){
        String line = "";
        if(stDev/2 - smallDif > 0)
            line += "Data is spread and possible with outliers which impact in this.";
        else if(stDev/2 - smallDif < 0)
            line += "Data is not spread and should be accumulated";
        return line;
    }
    
    private void writeVariableSummary(DataDef data){
        try{
            outputResult.printSeparator();
            outputResult.writeLine("Variable Name:");
            outputResult.writeLine(" "+data.getName().toString());
            outputResult.printBlankLine();
            outputResult.writeLine("Variable Types: ");
            for(VariableType type:data.getVarType())
                outputResult.writeLine(" "+type.toString());
            if(data.getMapedType() != null){
                outputResult.writeLine("Variable maped to:");
                outputResult.writeLine(" "+data.getMapedType().toString());
            }
            if(data.getVarSubType() != null){
                outputResult.writeLine("Variable subtype:");
                outputResult.writeLine(" "+data.getVarSubType().toString());
            }
            outputResult.writeLine("Mean | Sum of numbers divided by N");
            outputResult.writeLine(" "+String.valueOf(data.getMean()));
            outputResult.writeLine("Geometric Mean | Nth root of product of all numbers");
            outputResult.writeLine(" "+String.valueOf(data.getGeometricMean()));
            outputResult.writeLine("Mode | The most commonly reported value are");
            outputResult.printDoubleArray(data.getMode());            
            outputResult.writeLine("Median | Middle entry in sorted sequence");
            outputResult.writeLine(" "+String.valueOf(data.getMedian()));
            outputResult.writeLine("Range | Maximum subtracting Minimum");
            outputResult.writeLine(" "+String.valueOf(data.getRange()));
            outputResult.writeLine("Min value:");
            outputResult.writeLine(" "+String.valueOf(data.getMin()));
            outputResult.writeLine("Max value:");
            outputResult.writeLine(" "+String.valueOf(data.getMax()));
            outputResult.writeLine("Quartile 1 | Splits off the lowest 25% of data from the highest 75%");
            outputResult.writeLine(" "+String.valueOf(data.getQ1()));
            outputResult.writeLine("Quartile 3 | Splits off the highest 25% of data from the lowest 75%");
            outputResult.writeLine(" "+String.valueOf(data.getQ3()));
            outputResult.writeLine("Interquartile Range | Q3 - Q1 ");
            outputResult.writeLine(" "+String.valueOf(data.getIqr()));
            outputResult.writeLine("Low Interquartile Range:");
            outputResult.writeLine(" "+String.valueOf(data.getLowIqr()));
            outputResult.writeLine("High Interquartile Range:");
            outputResult.writeLine(" "+String.valueOf(data.getHighIqr()));
            outputResult.writeLine("Skewness:");
            outputResult.writeLine(" "+String.valueOf(data.getSkewness()));
            outputResult.writeLine("Kurtosis:");
            outputResult.writeLine(" "+String.valueOf(data.getKurtosis()));
            outputResult.writeLine(analyseSymmetry(data.getSkewness(),data.getKurtosis()));
            outputResult.writeLine("Standard Deviation | Spread of the data from the mean");
            outputResult.writeLine(" "+String.valueOf(data.getStandrDev()));
            outputResult.writeLine(analyseSpread(data.getStandrDev(),data.getMinDif()));
            outputResult.writeLine("Standar Error:");
            outputResult.writeLine(" "+String.valueOf(data.getStandrErr()));
            outputResult.writeLine("Low Quartile Not by lib:");
            outputResult.writeLine(" "+String.valueOf(data.getLowQuartile()));
            outputResult.writeLine("Mid Quartile Not by lib:");
            outputResult.writeLine(" "+String.valueOf(data.getMidQuartile()));
            outputResult.writeLine("Up Quartile Not by lib:");
            outputResult.writeLine(" "+String.valueOf(data.getUpQuartile()));
            outputResult.writeLine("Range Quartile:");
            outputResult.writeLine(" "+String.valueOf(data.getRangeQuartile()));
            outputResult.writeLine("Variance | Spread of data and measure of how much the values differ from mean");
            outputResult.writeLine(" "+String.valueOf(data.getVariance()));
            outputResult.writeLine("Pupulation Variance | Spread of data and measure of how much the values differ from mean");
            outputResult.writeLine(" "+String.valueOf(data.getPopulationVariance()));
            outputResult.writeLine("Coefficient Variation:");
            outputResult.writeLine(" "+String.valueOf(data.getCoefficientVariation()));
            outputResult.writeLine("Minimum difference between values:");
            outputResult.writeLine(" "+String.valueOf(data.getMinDif()));
            outputResult.writeLine("Population:");
            outputResult.writeLine(" "+String.valueOf(data.getPopulation()));
            outputResult.writeLine("Is this variable important to set?");
            outputResult.writeLine(" "+String.valueOf(data.getIsEnable()));
            outputResult.printBlankLine();
            outputResult.printSubSeparator();
            outputResult.writeLine("Normilize Data:");
            outputResult.printDoubleArray(data.getNormilizeData());
            outputResult.writeLine("Distinct values: ");
            outputResult.printDistinctMap(data.getDistValues());
            if(data.getIndexOutliersList() != null){
                outputResult.writeLine("Index of possible outliers: ");
                outputResult.printIntegerList(data.getIndexOutliersList());
            }
            if(data.getNumericValues() != null){
                outputResult.writeLine("Numeric Values: ");
                outputResult.printDoubleList(data.getNumericValues());
            }
            outputResult.writeLine("Original Values: ");
            outputResult.printStringList(data.getOriginalValues());
            if(data.getCategoricalValue() != null){
                outputResult.writeLine("Categorical Values: ");
                outputResult.printStringList(data.getCategoricalValue());
            }
            if(data.getRemapValues() != null){
                outputResult.writeLine("Remap Values: ");
                outputResult.printRemapMap(data.getRemapValues());
            }
            outputResult.printSubSeparator();
        }
        catch(IOException iOE){
            System.out.println("DataAnalyser | IOException | File not found " + iOE.getMessage());
        }
        finally{
            try{
                outputResult.flushToFile();
            }
            catch(IOException iOE){
                System.out.println("DataAnalyser | IOException | File not found " + iOE.getMessage());
            }
        }
        
    }
    
}
