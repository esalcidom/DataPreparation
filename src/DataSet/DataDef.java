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
    //private List<String> variableData; delete the whole store of values of column, that is why we have the table
    private List<VariableType> varType;
    private VariableSubType varSubType; //variable sub type refers if the variable is numeric, alpha or alphanumeric
    private double mean;
    private double median;
    private double skewness;
    private double standrDev;
    private double range;
    private double max;
    private double min;
    private double lowQuartile;
    private double upQuartile;
    private double rangeQuartile;
    private double variance;
    private double kurtosis;
    private Map<String,Integer> distValues;
    private List<String> distHead; //Need to check if it's necessary this distinc values'
    private int population;
    private boolean isEnable;
    
    
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

    
}
