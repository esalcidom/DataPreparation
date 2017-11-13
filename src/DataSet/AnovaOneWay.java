/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataSet;

import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class AnovaOneWay {
    private String var1Name;
    private String var2Name;
    private List<Double> var1Values;
    private List<Double> var2Values;
    private double fValue;
    private double pValue;
    private boolean isTestAlpha;
    
    public AnovaOneWay(){
        
    }
    public AnovaOneWay(List<Double> var1, List<Double> var2, String var1Name, String var2Name){
        var1Values = var1;
        var2Values = var2;
        this.var1Name = var1Name;
        this.var2Name = var2Name;
    }
    public AnovaOneWay(DataDef var1, DataDef var2){
        var1Values = var1.getNumericValues();
        var2Values = var2.getNumericValues();
        var1Name = var1.getName().toString();
        var2Name = var2.getName().toString();
    }

    /**
     * @return the var1Name
     */
    public String getVar1Name() {
        return var1Name;
    }

    /**
     * @param var1Name the var1Name to set
     */
    public void setVar1Name(String var1Name) {
        this.var1Name = var1Name;
    }

    /**
     * @return the var2Name
     */
    public String getVar2Name() {
        return var2Name;
    }

    /**
     * @param var2Name the var2Name to set
     */
    public void setVar2Name(String var2Name) {
        this.var2Name = var2Name;
    }

    /**
     * @return the var1Values
     */
    public List<Double> getVar1Values() {
        return var1Values;
    }

    /**
     * @param var1Values the var1Values to set
     */
    public void setVar1Values(List<Double> var1Values) {
        this.var1Values = var1Values;
    }

    /**
     * @return the var2Values
     */
    public List<Double> getVar2Values() {
        return var2Values;
    }

    /**
     * @param var2Values the var2Values to set
     */
    public void setVar2Values(List<Double> var2Values) {
        this.var2Values = var2Values;
    }

    /**
     * @return the fValue
     */
    public double getfValue() {
        return fValue;
    }

    /**
     * @param fValue the fValue to set
     */
    public void setfValue(double fValue) {
        this.fValue = fValue;
    }

    /**
     * @return the pValue
     */
    public double getpValue() {
        return pValue;
    }

    /**
     * @param pValue the pValue to set
     */
    public void setpValue(double pValue) {
        this.pValue = pValue;
    }

    /**
     * @return the isTestAlpha
     */
    public boolean isIsTestAlpha() {
        return isTestAlpha;
    }

    /**
     * @param isTestAlpha the isTestAlpha to set
     */
    public void setIsTestAlpha(boolean isTestAlpha) {
        this.isTestAlpha = isTestAlpha;
    }
    
    
}
