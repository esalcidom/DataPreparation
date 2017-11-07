/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataSet;

import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/**
 *
 * @author Emmanuel
 */
public class StatOperator {
    
    private List<String> varData;
    private SummaryStatistics summary;
    
    public StatOperator(List data){
        this.varData = data;
        if(varData != null){
            for(String value : varData){
                summary.addValue(Double.parseDouble(value));
            }
        }
    }

    //calculate all balues for contingency table
    public void calculateContingencySummary(ContingencyTableDef conTable){
        
        double[][] doubleMatrix = conTable.getContingencyTable();
        weka.core.ContingencyTables wekaTable = new weka.core.ContingencyTables();
        conTable.setChiSquare(wekaTable.chiVal(doubleMatrix,true));
        conTable.setpValue(wekaTable.chiSquared(doubleMatrix, true));
        conTable.setCramersV(wekaTable.CramersV(doubleMatrix));
        conTable.setCochransCriterion(wekaTable.cochransCriterion(doubleMatrix));
        conTable.setTauVal(wekaTable.tauVal(doubleMatrix));
    }

    private List<String> getDistinctValues(List<String> values){
        return values.stream().distinct().collect(Collectors.toList());
    }
    
    public StatOperator(){
        
    }
    
    public double calMean(){
        return getSummary().getMean();
    }
    
    /**
     * @return the varData
     */
    public List<String> getVarData() {
        return varData;
    }

    /**
     * @param aVarData the varData to set
     */
    public void setVarData(List<String> aVarData) {
        varData = aVarData;
    }

    /**
     * @return the summary
     */
    public SummaryStatistics getSummary() {
        return summary;
    }

    /**
     * @param aSummary the summary to set
     */
    public void setSummary(SummaryStatistics aSummary) {
        summary = aSummary;
    }
}
