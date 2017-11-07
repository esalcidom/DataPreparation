/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataSet;

import java.util.List;
import org.apache.commons.math3.linear.RealMatrix;

/**
 *
 * @author Emmanuel
 */
public class PearsonCorrelation {
    
    private String var1Name;
    private String var2Name;
    private List<Double> var1Values;
    private List<Double> var2Values;
    private double[][] matrix;
    private RealMatrix correlationMatrix;
    private RealMatrix correlationPValues;
    private RealMatrix correalationStandarError;
    
     public PearsonCorrelation(List<Double> var1, List<Double> var2, String var1Name, String var2Name){
        var1Values = var1;
        var2Values = var2;
        this.var1Name = var1Name;
        this.var2Name = var2Name;
        createMatrix();
    }
    public PearsonCorrelation(DataDef row, DataDef col){
        var1Values = row.getNumericValues();
        var2Values = col.getNumericValues();
        var1Name = row.getName().toString();
        var2Name = col.getName().toString();
        createMatrix();
    }
    public PearsonCorrelation(){
        
    }

    private void createMatrix(){
        double[] var1Array = new double[var1Values.size()];
        double[] var2Array = new double[var2Values.size()];
        for(int i=0;i<var1Array.length;i++){
            var1Array[i] = var1Values.get(i);
            var2Array[i] = var2Values.get(i);
        }

        double[][] matrixResult = {var1Array,var2Array};
        setMatrix(matrixResult);
    }

    /**
     * @return the correlationMatrix
     */
    public RealMatrix getCorrelationMatrix() {
        return correlationMatrix;
    }

    /**
     * @param correlationMatrix the correlationMatrix to set
     */
    public void setCorrelationMatrix(RealMatrix correlationMatrix) {
        this.correlationMatrix = correlationMatrix;
    }

    /**
     * @return the correlationPValues
     */
    public RealMatrix getCorrelationPValues() {
        return correlationPValues;
    }

    /**
     * @param correlationPValues the correlationPValues to set
     */
    public void setCorrelationPValues(RealMatrix correlationPValues) {
        this.correlationPValues = correlationPValues;
    }

    /**
     * @return the correalationStandarError
     */
    public RealMatrix getCorrealationStandarError() {
        return correalationStandarError;
    }

    /**
     * @param correalationStandarError the correalationStandarError to set
     */
    public void setCorrealationStandarError(RealMatrix correalationStandarError) {
        this.correalationStandarError = correalationStandarError;
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
}
