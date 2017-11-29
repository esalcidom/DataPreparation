/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataSet;

import java.util.List;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Clusterable;

/**
 *
 * @author Emmanuel
 */
public class KMeans {
    
    private String varName;
    private List<String> varValues;
    private List<CentroidCluster<Clusterable>> cluster;
    
    public KMeans(){
        
    }
    public KMeans(List<String> var, String varName){
        varValues = var;
        this.varName = varName;
    }
    public KMeans(DataDef var){
        varValues = var.getOriginalValues();
        this.varName = var.getName().toString();
    }

    /**
     * @return the varName
     */
    public String getVarName() {
        return varName;
    }

    /**
     * @param varName the varName to set
     */
    public void setVarName(String varName) {
        this.varName = varName;
    }

    /**
     * @return the varValues
     */
    public List<String> getVarValues() {
        return varValues;
    }

    /**
     * @param varValues the varValues to set
     */
    public void setVarValues(List<String> varValues) {
        this.varValues = varValues;
    }

    /**
     * @return the cluster
     */
    public List<CentroidCluster<Clusterable>> getCluster() {
        return cluster;
    }

    /**
     * @param cluster the cluster to set
     */
    public void setCluster(List<CentroidCluster<Clusterable>> cluster) {
        this.cluster = cluster;
    }

    
    
}
