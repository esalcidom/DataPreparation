/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataSet;

import java.util.*;
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
