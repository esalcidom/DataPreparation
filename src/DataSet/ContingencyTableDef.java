package DataSet;

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
        rowValues = row.getCategoricalValues();
        colValues = col.getCategoricalValues();
        rowName = row.getName();
        colName = col.getName();
    }
    public ContingencyTableDef(){

    }

    public void createContingencyTable(){
        if(rowValues != null && colValues != null){
            jsc.contingencytables.ContingencyTable table = new jsc.contingencytables.ContingencyTable(rowValues,colValues);
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

}