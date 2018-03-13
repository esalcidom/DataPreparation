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
public class SimpleReg {
    private String var1Name;
    private String var2Name;
    private List<Double> var1Values;
    private List<Double> var2Values;
    private double[][] matrix;
    private double rPearsonCorrelation;
    private double regressionSumSquares;
    private double rSquare;
    private double significance; // determine if a regression model can be applied to a observed data
    private double slope; //The beta or asociated with the X
    private double slopeConfidenceInterval;
    private double slopeStErr;
    private double sumCrossProducts;
    private double sumSquareErr;
    private double totalSumSquares;
    private double xSumSquares;
    private double intercept; //The alpha or initial value of the ecuation
    private double interceptStErr;
    private double meanSquareErr;
    
    public SimpleReg(){
        
    }
    public SimpleReg(List<Double> var1, List<Double> var2, String var1Name, String var2Name){
        var1Values = var1;
        var2Values = var2;
        this.var1Name = var1Name;
        this.var2Name = var2Name;
        createMatrix();
    }
    public SimpleReg(DataDef var1, DataDef var2){
        if(var1.getVarSubType().equals(VariableSubType.NUMERIC))
            var1Values = DefOperator.generateStringListToDouble(var1.getOriginalValues());
        else
            var1Values = var1.getNumericValues();
        if(var2.getVarSubType().equals(VariableSubType.NUMERIC))
            var2Values = DefOperator.generateStringListToDouble(var2.getOriginalValues());
        else
            var2Values = var2.getNumericValues();
        var1Name = var1.getName().toString();
        var2Name = var2.getName().toString();
        createMatrix();
    }
    
    private void createMatrix(){
        double[] var1Array = DefOperator.generateArrayDouble(var1Values);
        double[] var2Array = DefOperator.generateArrayDouble(var2Values);
        double[][] matrixResult = {var1Array,var2Array};
        setMatrix(matrixResult);
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
     * @return the matrix
     */
    public double[][] getMatrix() {
        return matrix;
    }

    /**
     * @param matrix the matrix to set
     */
    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * @return the rPearsonCorrelation
     */
    public double getrPearsonCorrelation() {
        return rPearsonCorrelation;
    }

    /**
     * @param rPearsonCorrelation the rPearsonCorrelation to set
     */
    public void setrPearsonCorrelation(double rPearsonCorrelation) {
        this.rPearsonCorrelation = rPearsonCorrelation;
    }

    /**
     * @return the regressionSumSquares
     */
    public double getRegressionSumSquares() {
        return regressionSumSquares;
    }

    /**
     * @param regressionSumSquares the regressionSumSquares to set
     */
    public void setRegressionSumSquares(double regressionSumSquares) {
        this.regressionSumSquares = regressionSumSquares;
    }

    /**
     * @return the rSquare
     */
    public double getrSquare() {
        return rSquare;
    }

    /**
     * @param rSquare the rSquare to set
     */
    public void setrSquare(double rSquare) {
        this.rSquare = rSquare;
    }

    /**
     * @return the significance
     */
    public double getSignificance() {
        return significance;
    }

    /**
     * @param significance the significance to set
     */
    public void setSignificance(double significance) {
        this.significance = significance;
    }

    /**
     * @return the slope
     */
    public double getSlope() {
        return slope;
    }

    /**
     * @param slope the slope to set
     */
    public void setSlope(double slope) {
        this.slope = slope;
    }

    /**
     * @return the intercept
     */
    public double getIntercept() {
        return intercept;
    }

    /**
     * @param intercept the intercept to set
     */
    public void setIntercept(double intercept) {
        this.intercept = intercept;
    }

    /**
     * @return the interceptStErr
     */
    public double getInterceptStErr() {
        return interceptStErr;
    }

    /**
     * @param interceptStErr the interceptStErr to set
     */
    public void setInterceptStErr(double interceptStErr) {
        this.interceptStErr = interceptStErr;
    }

    /**
     * @return the meanSquareErr
     */
    public double getMeanSquareErr() {
        return meanSquareErr;
    }

    /**
     * @param meanSquareErr the meanSquareErr to set
     */
    public void setMeanSquareErr(double meanSquareErr) {
        this.meanSquareErr = meanSquareErr;
    }

    /**
     * @return the slopeConfidenceInterval
     */
    public double getSlopeConfidenceInterval() {
        return slopeConfidenceInterval;
    }

    /**
     * @param slopeConfidenceInterval the slopeConfidenceInterval to set
     */
    public void setSlopeConfidenceInterval(double slopeConfidenceInterval) {
        this.slopeConfidenceInterval = slopeConfidenceInterval;
    }

    /**
     * @return the slopeStErr
     */
    public double getSlopeStErr() {
        return slopeStErr;
    }

    /**
     * @param slopeStErr the slopeStErr to set
     */
    public void setSlopeStErr(double slopeStErr) {
        this.slopeStErr = slopeStErr;
    }

    /**
     * @return the sumCrossProducts
     */
    public double getSumCrossProducts() {
        return sumCrossProducts;
    }

    /**
     * @param sumCrossProducts the sumCrossProducts to set
     */
    public void setSumCrossProducts(double sumCrossProducts) {
        this.sumCrossProducts = sumCrossProducts;
    }

    /**
     * @return the sumSquareErr
     */
    public double getSumSquareErr() {
        return sumSquareErr;
    }

    /**
     * @param sumSquareErr the sumSquareErr to set
     */
    public void setSumSquareErr(double sumSquareErr) {
        this.sumSquareErr = sumSquareErr;
    }

    /**
     * @return the totalSumSquares
     */
    public double getTotalSumSquares() {
        return totalSumSquares;
    }

    /**
     * @param totalSumSquares the totalSumSquares to set
     */
    public void setTotalSumSquares(double totalSumSquares) {
        this.totalSumSquares = totalSumSquares;
    }

    /**
     * @return the xSumSquares
     */
    public double getxSumSquares() {
        return xSumSquares;
    }

    /**
     * @param xSumSquares the xSumSquares to set
     */
    public void setxSumSquares(double xSumSquares) {
        this.xSumSquares = xSumSquares;
    }
    
}
