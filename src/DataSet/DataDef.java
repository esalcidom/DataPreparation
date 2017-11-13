/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataSet;

import java.util.*;

/**
 *
 * @author Emmanuel
 */
public class DataDef {
    
    private StringBuilder name;
    private List<VariableType> varType;
    private VariableType mapedType;
    private VariableSubType varSubType; //variable sub type refers if the variable is numeric, alpha or alphanumeric
    private double mean;
    private double[] mode;
    private double geometricMean;
    private double[] normilizeData;
    private double percentil;
    private double median;
    private double skewness;
    private double standrDev;
    private double standrErr;
    private double range;
    private double max;
    private double min;
    private double lowQuartile;
    private double midQuartile;
    private double upQuartile;
    private double rangeQuartile;
    private double variance;
    private double populationVariance;
    private double kurtosis;
    private double minDif;
    private Map<String,Integer> distValues;
    private List<String> distHead; 
    private int population;
    private boolean isEnable;
    private List<Double> numericValues; 
    private List<String> originalValues;
    private List<String> categoricalValue;
    private Map<String,String> remapValues; //This characteristic can hold a remap values for the variable holding the same key string as distValues and the other value. NOTE THE REMAP IS FOR ALPHA TO NUMERIC
    
    
    public DataDef(){
        isEnable = true;
        varSubType = VariableSubType.NONE;
    }
    /**
     * @return the name
     */
    public StringBuilder getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(StringBuilder name) {
        this.name = name;
    }

    /**
     * @return the mean
     */
    public double getMean() {
        return mean;
    }

    /**
     * @param mean the mean to set
     */
    public void setMean(double mean) {
        this.mean = mean;
    }

    /**
     * @return the median
     */
    public double getMedian() {
        return median;
    }

    /**
     * @param median the median to set
     */
    public void setMedian(double median) {
        this.median = median;
    }

    /**
     * @return the skewness
     */
    public double getSkewness() {
        return skewness;
    }

    /**
     * @param skewness the skewness to set
     */
    public void setSkewness(double skewness) {
        this.skewness = skewness;
    }

    /**
     * @return the standrDev
     */
    public double getStandrDev() {
        return standrDev;
    }

    /**
     * @param standrDev the standrDev to set
     */
    public void setStandrDev(double standrDev) {
        this.standrDev = standrDev;
    }

    /**
     * @return the range
     */
    public double getRange() {
        return range;
    }

    /**
     * @param range the range to set
     */
    public void setRange(double range) {
        this.range = range;
    }

    /**
     * @return the max
     */
    public double getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(double max) {
        this.max = max;
    }

    /**
     * @return the min
     */
    public double getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(double min) {
        this.min = min;
    }

    /**
     * @return the lowQuartile
     */
    public double getLowQuartile() {
        return lowQuartile;
    }

    /**
     * @param lowQuartile the lowQuartile to set
     */
    public void setLowQuartile(double lowQuartile) {
        this.lowQuartile = lowQuartile;
    }

    /**
     * @return the upQuartile
     */
    public double getUpQuartile() {
        return upQuartile;
    }

    /**
     * @param upQuartile the upQuartile to set
     */
    public void setUpQuartile(double upQuartile) {
        this.upQuartile = upQuartile;
    }

    /**
     * @return the rangeQuartile
     */
    public double getRangeQuartile() {
        return rangeQuartile;
    }

    /**
     * @param rangeQuartile the rangeQuartile to set
     */
    public void setRangeQuartile(double rangeQuartile) {
        this.rangeQuartile = rangeQuartile;
    }

    /**
     * @return the variance
     */
    public double getVariance() {
        return variance;
    }

    /**
     * @param variance the variance to set
     */
    public void setVariance(double variance) {
        this.variance = variance;
    }

    /**
     * @return the kurtosis
     */
    public double getKurtosis() {
        return kurtosis;
    }

    /**
     * @param kurtosis the kurtosis to set
     */
    public void setKurtosis(double kurtosis) {
        this.kurtosis = kurtosis;
    }

    /**
     * @return the distValues
     */
    public Map<String,Integer> getDistValues() {
        return distValues;
    }

    /**
     * @return the variableData
     
    public List<String> getVariableData() {
        return variableData;
    }

    
    public void setVariableData(List<String> variableData) {
        this.variableData = variableData;
    }
    * 
    * 
    */

    /**
     * @param distValues the distValues to set
     */
    public void setDistValues(Map<String,Integer> distValues) {
        this.distValues = distValues;
    }

    /**
     * @return the distHead
     */
    public List<String> getDistHead() {
        return distHead;
    }

    /**
     * @param distHead the distHead to set
     */
    public void setDistHead(List<String> distHead) {
        this.distHead = distHead;
    }

    /**
     * @return the population
     */
    public int getPopulation() {
        return population;
    }

    /**
     * @param population the population to set
     */
    public void setPopulation(int population) {
        this.population = population;
    }

    /**
     * @return the isEnable
     */
    public boolean getIsEnable() {
        return isEnable;
    }

    /**
     * @param isEnable the isEnable to set
     */
    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    /**
     * @return the varSubType
     */
    public VariableSubType getVarSubType() {
        return varSubType;
    }

    /**
     * @param varSubType the varSubType to set
     */
    public void setVarSubType(VariableSubType varSubType) {
        this.varSubType = varSubType;
    }

    /**
     * @return the varType
     */
    public List<VariableType> getVarType() {
        return varType;
    }

    /**
     * @param varType the varType to set
     */
    public void setVarType(List<VariableType> varType) {
        this.varType = varType;
    }

    /**
     * @return the mapedType
     */
    public VariableType getMapedType() {
        return mapedType;
    }

    /**
     * @param mapedType the mapedType to set
     */
    public void setMapedType(VariableType mapedType) {
        this.mapedType = mapedType;
    }

    /**
     * @return the midQuartile
     */
    public double getMidQuartile() {
        return midQuartile;
    }

    /**
     * @param midQuartile the midQuartile to set
     */
    public void setMidQuartile(double midQuartile) {
        this.midQuartile = midQuartile;
    }

    /**
     * @return the numericValues
     */
    public List<Double> getNumericValues() {
        return numericValues;
    }

    /**
     * @param numericValues the numericValues to set
     */
    public void setNumericValues(List<Double> numericValues) {
        this.numericValues = numericValues;
    }

    /**
     * @return the stringValues
     */
    public List<String> getOriginalValues() {
        return originalValues;
    }

    /**
     * @param stringValues the stringValues to set
     */
    public void setOriginalValues(List<String> stringValues) {
        this.originalValues = stringValues;
    }

    /**
     * @return the remapValues
     */
    public Map<String,String> getRemapValues() {
        return remapValues;
    }

    /**
     * @param remapValues the remapValues to set
     */
    public void setRemapValues(Map<String,String> remapValues) {
        this.remapValues = remapValues;
    }

    /**
     * @return the mode
     */
    public double[] getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(double mode[]) {
        this.mode = mode;
    }

    /**
     * @return the geometricMean
     */
    public double getGeometricMean() {
        return geometricMean;
    }

    /**
     * @param geometricMean the geometricMean to set
     */
    public void setGeometricMean(double geometricMean) {
        this.geometricMean = geometricMean;
    }

    /**
     * @return the normilizeData
     */
    public double[] getNormilizeData() {
        return normilizeData;
    }

    /**
     * @param normilizeData the normilizeData to set
     */
    public void setNormilizeData(double[] normilizeData) {
        this.normilizeData = normilizeData;
    }

    /**
     * @return the percentil
     */
    public double getPercentil() {
        return percentil;
    }

    /**
     * @param percentil the percentil to set
     */
    public void setPercentil(double percentil) {
        this.percentil = percentil;
    }

    /**
     * @return the populationVariance
     */
    public double getPopulationVariance() {
        return populationVariance;
    }

    /**
     * @param populationVariance the populationVariance to set
     */
    public void setPopulationVariance(double populationVariance) {
        this.populationVariance = populationVariance;
    }

    /**
     * @return the categoricalValue
     */
    public List<String> getCategoricalValue() {
        return categoricalValue;
    }

    /**
     * @param categoricalValue the categoricalValue to set
     */
    public void setCategoricalValue(List<String> categoricalValue) {
        this.categoricalValue = categoricalValue;
    }

    /**
     * @return the minDif
     */
    public double getMinDif() {
        return minDif;
    }

    /**
     * @param minDif the minDif to set
     */
    public void setMinDif(double minDif) {
        this.minDif = minDif;
    }

    /**
     * @return the standrErr
     */
    public double getStandrErr() {
        return standrErr;
    }

    /**
     * @param standrErr the standrErr to set
     */
    public void setStandrErr(double standrErr) {
        this.standrErr = standrErr;
    }

    
}
