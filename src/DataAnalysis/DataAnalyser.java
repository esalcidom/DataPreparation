/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAnalysis;

import DataSet.ContingencyTableDef;
import DataSet.DataDef;
import DataSet.TableData;
import DataSet.VariableSubType;
import DataSet.VariableType;
import java.io.IOException;
import java.util.List;
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
    }
    
    public void startRelationAnalysis(){
        for(ContingencyTableDef entry: tableData.getContingencyTableList()){
            writeContingencySummary(entry);
        }
    }
    
    public void closeFile(){
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
    
    private void validateDataSummaryValue(int value, String title) throws IOException{
        outputResult.writeLine(title);
        outputResult.writeLine(" "+value);
    }
    
    private void validateDataSummaryValue(boolean value, String title) throws IOException{
        outputResult.writeLine(title);
        outputResult.writeLine(" "+value);
    }
    
    private void validateDataSummaryValue(Double value, String title) throws IOException{
        if(value != null){
            outputResult.writeLine(title);
            outputResult.writeLine(" "+String.valueOf(value));
        }
    }
    
    private void validateDataSummaryValue(VariableType value, String title) throws IOException{
        if(value != null){
            outputResult.writeLine(title);
            outputResult.writeLine(" "+value.toString());
        }
    }
    
    private void validateDataSummaryValue(VariableSubType value, String title) throws IOException{
        if(value != null){
            outputResult.writeLine(title);
            outputResult.writeLine(" "+value.toString());
        }
    }
    
    private void validateDataSummaryValue(double[] value, String title) throws IOException{
        if(value != null){
            outputResult.writeLine(title);
            outputResult.printDoubleArray(value);
        }
    }
    
    private void validateDataSummaryValue(double[][] value, String title) throws IOException{
        if(value != null){
            outputResult.writeLine(title);
            outputResult.printDouble2DArray(value);
        }
    }
    
    private void validateDataSummaryValue(Map value, String title) throws IOException{
        if(value != null){
            outputResult.writeLine(title);
            outputResult.printDistinctMap(value);
        }
    }
    
    private void validateDataSummaryValue(List value, String title) throws IOException{
        if(value != null){
            outputResult.writeLine(title);
            outputResult.printList(value);
        }
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
            validateDataSummaryValue(data.getMapedType(), "Variable maped to:");
            validateDataSummaryValue(data.getVarSubType(), "Variable subtype:");
            validateDataSummaryValue(data.getMean(),"Mean | Sum of numbers divided by N");
            validateDataSummaryValue(data.getGeometricMean(), "Geometric Mean | Nth root of product of all numbers");
            validateDataSummaryValue(data.getMode(), "Mode | The most commonly reported value are");
            validateDataSummaryValue(data.getMedian(), "Median | Middle entry in sorted sequence");
            validateDataSummaryValue(data.getRange(), "Range | Maximum subtracting Minimum");
            validateDataSummaryValue(data.getMin(),"Min value:");
            validateDataSummaryValue(data.getMax(), "Max value:");
            validateDataSummaryValue(data.getQ1(), "Quartile 1 | Splits off the lowest 25% of data from the highest 75%");
            validateDataSummaryValue(data.getQ3(), "Quartile 3 | Splits off the highest 25% of data from the lowest 75%");
            validateDataSummaryValue(data.getIqr(), "Interquartile Range | Q3 - Q1 ");
            validateDataSummaryValue(data.getLowIqr(), "Low Interquartile Range:");
            validateDataSummaryValue(data.getHighIqr(), "High Interquartile Range:");
            validateDataSummaryValue(data.getSkewness(),"Skewness:");
            validateDataSummaryValue(data.getKurtosis(),"Kurtosis:");
            outputResult.writeLine(analyseSymmetry(data.getSkewness(),data.getKurtosis()));
            validateDataSummaryValue(data.getStandrDev(),"Standard Deviation | Spread of the data from the mean");
            outputResult.writeLine(analyseSpread(data.getStandrDev(),data.getMinDif()));
            validateDataSummaryValue(data.getStandrErr(),"Standar Error | Average difference between the mean and the expected value.");
            validateDataSummaryValue(data.getLowQuartile(),"Low Quartile Not by lib:");
            validateDataSummaryValue(data.getMidQuartile(),"Mid Quartile Not by lib:");
            validateDataSummaryValue(data.getUpQuartile(),"Up Quartile Not by lib:");
            validateDataSummaryValue(data.getRangeQuartile(),"Range Quartile:");
            validateDataSummaryValue(data.getVariance(),"Variance | Spread of data and measure of how much the values differ from mean");
            validateDataSummaryValue(data.getPopulationVariance(),"Pupulation Variance | Spread of data and measure of how much the values differ from mean");
            validateDataSummaryValue(data.getCoefficientVariation(),"Coefficient Variation:");
            validateDataSummaryValue(data.getMinDif(),"Minimum difference between values:");
            validateDataSummaryValue(data.getPopulation(),"Population:");
            validateDataSummaryValue(data.getIsEnable(),"Is this variable important to set?");
            outputResult.printBlankLine();
            outputResult.printSubSeparator();
            validateDataSummaryValue(data.getNormilizeData(),"Normilize Data:");
            validateDataSummaryValue(data.getDistValues(),"Distinct values:");
            validateDataSummaryValue(data.getIndexOutliersList(),"Index of possible outliers:");
            validateDataSummaryValue(data.getNumericValues(),"Numeric Values:");
            validateDataSummaryValue(data.getOriginalValues(),"Original Values:");
            validateDataSummaryValue(data.getCategoricalValue(),"Categorical Values:");
            validateDataSummaryValue(data.getRemapValues(),"Remap Values:");
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
    
    private void writeContingencySummary(ContingencyTableDef contingency){
        try{
            outputResult.printContingencyLabel(); 
            outputResult.writeLine(contingency.getRowName() + " - " + contingency.getColName());
            validateDataSummaryValue(contingency.getContingencyTable(), "Contingency Table");
            validateDataSummaryValue(contingency.getChiSquare(), "ChiSquare:");
            validateDataSummaryValue(contingency.getpValue(), "P Value:");
            validateDataSummaryValue(contingency.getCramersV(), "Cramers V:");
            validateDataSummaryValue(contingency.getTauVal(), "Tau Value:");
            validateDataSummaryValue(contingency.isCochransCriterion(), "Cochrans Criterion:");
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
