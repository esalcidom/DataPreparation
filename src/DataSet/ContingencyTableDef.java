package DataSet;

import java.util.List;
import weka.core.*;
import jsc.contingencytables.*;

public class ContingencyTableDef {

    private String rowName;
    private String colName;
    private List<String> rowValues;
    private List<String> colValues;
    private double[][] contingencyTable;
    private double chiSquare;
    private double pValue;
    private double cramersV;
    private double tauVal;
    private boolean cochransCriterion;

    public ContingencyTableDef(List<String> row, List<String> col, String rowName, String colName){
        rowValues = row;
        colValues = col;
        this.rowName = rowName;
        this.colName = colName;
    }
    public ContingencyTableDef(DataDef row, DataDef col){
        rowValues = row.getCategoricalValue();
        colValues = col.getCategoricalValue();
        rowName = row.getName().toString();
        colName = col.getName().toString();
    }
    public ContingencyTableDef(){

    }

    public void createContingencyTable(){
        if(rowValues != null && colValues != null){
            jsc.contingencytables.ContingencyTable table = new jsc.contingencytables.ContingencyTable(rowValues.toArray(new String[0]),colValues.toArray(new String[0]));
            int[][] matrix = table.getFrequencies();
            setContingencyTable(castIntToDoubleMatix(matrix));
        }
    }

    private static double[][] castIntToDoubleMatix(int[][] intMatrix){
		double[][] doubleMatrix = new double[intMatrix.length][intMatrix[0].length];
		for(int i = 0; i < intMatrix.length; i++)
	    {
	        for(int j = 0; j < intMatrix[0].length; j++)
	        	doubleMatrix[i][j] = (double)intMatrix[i][j];
	    }
		return doubleMatrix;
	}

    //GETTERS & SETTERS

    /**
     * @return the rowName
     */
    public String getRowName() {
        return rowName;
    }

    /**
     * @param rowName the rowName to set
     */
    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    /**
     * @return the colName
     */
    public String getColName() {
        return colName;
    }

    /**
     * @param colName the colName to set
     */
    public void setColName(String colName) {
        this.colName = colName;
    }

    /**
     * @return the contingencyTable
     */
    public double[][] getContingencyTable() {
        return contingencyTable;
    }

    /**
     * @param contingencyTable the contingencyTable to set
     */
    public void setContingencyTable(double[][] contingencyTable) {
        this.contingencyTable = contingencyTable;
    }

    /**
     * @return the chiSquare
     */
    public double getChiSquare() {
        return chiSquare;
    }

    /**
     * @param chiSquare the chiSquare to set
     */
    public void setChiSquare(double chiSquare) {
        this.chiSquare = chiSquare;
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
     * @return the cramersV
     */
    public double getCramersV() {
        return cramersV;
    }

    /**
     * @param cramersV the cramersV to set
     */
    public void setCramersV(double cramersV) {
        this.cramersV = cramersV;
    }

    /**
     * @return the tauVal
     */
    public double getTauVal() {
        return tauVal;
    }

    /**
     * @param tauVal the tauVal to set
     */
    public void setTauVal(double tauVal) {
        this.tauVal = tauVal;
    }

    /**
     * @return the cochransCriterion
     */
    public boolean isCochransCriterion() {
        return cochransCriterion;
    }

    /**
     * @param cochransCriterion the cochransCriterion to set
     */
    public void setCochransCriterion(boolean cochransCriterion) {
        this.cochransCriterion = cochransCriterion;
    }

}