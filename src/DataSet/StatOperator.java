/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataSet;

import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.inference.OneWayAnova;
import org.apache.commons.math3.stat.regression.SimpleRegression;

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
    
    //calculate kendall tau correlation
    public void calculateKendallTauSummary(KendallTau kTau){
        KendallsCorrelation kCorr = new KendallsCorrelation(kTau.getMatrix());
        kTau.setCorrelation(kCorr.correlation(DefOperator.generateArrayDouble(kTau.getVar1Values()),DefOperator.generateArrayDouble(kTau.getVar2Values())));
        kTau.setCorrelationMatrix(kCorr.computeCorrelationMatrix(kTau.getMatrix()));
    }
    
    public void calculatePearsonSummary(PearsonCorrelation pearsonC){
        
        org.apache.commons.math3.stat.correlation.PearsonsCorrelation pearC = new org.apache.commons.math3.stat.correlation.PearsonsCorrelation(pearsonC.getMatrix());
        pearsonC.setCorrelationMatrix(pearC.getCorrelationMatrix());
        pearsonC.setCorrelation(pearC.correlation(DefOperator.generateArrayDouble(pearsonC.getVar1Values()),DefOperator.generateArrayDouble(pearsonC.getVar2Values())));
        pearsonC.setCorrelationPValues(pearC.getCorrelationPValues());
        pearsonC.setCorrealationStandarError(pearC.getCorrelationStandardErrors());
    }
    
    public void calculateSimpleRegressionSummary(SimpleReg simpleReg){
        SimpleRegression simReg = new SimpleRegression();
        simReg.addData(simpleReg.getMatrix());
        simpleReg.setIntercept(simReg.getIntercept());
        simpleReg.setInterceptStErr(simReg.getInterceptStdErr());
        simpleReg.setMeanSquareErr(simReg.getMeanSquareError());
        simpleReg.setrPearsonCorrelation(simReg.getR());
        simpleReg.setRegressionSumSquares(simReg.getRegressionSumSquares());
        simpleReg.setrSquare(simReg.getRSquare());
        simpleReg.setSignificance(simReg.getSignificance());
        simpleReg.setSlope(simReg.getSlope());
        simpleReg.setSlopeConfidenceInterval(simReg.getSlopeConfidenceInterval());
        simpleReg.setSlopeStErr(simReg.getSlopeStdErr());
        simpleReg.setSumCrossProducts(simReg.getSumOfCrossProducts());
        simpleReg.setSumSquareErr(simReg.getSumSquaredErrors());
        simpleReg.setTotalSumSquares(simReg.getTotalSumSquares());
        simpleReg.setxSumSquares(simReg.getXSumSquares());
    }
    
    public void calculateAnovaSummary(AnovaOneWay anova){
        OneWayAnova oAnova = new OneWayAnova();
        List<double[]> variables = new ArrayList<double[]>();
        variables.add(DefOperator.generateArrayDouble(anova.getVar1Values()));
        variables.add(DefOperator.generateArrayDouble(anova.getVar2Values()));
        anova.setfValue(oAnova.anovaFValue(variables));
        anova.setpValue(oAnova.anovaPValue(variables));
        anova.setIsTestAlpha(oAnova.anovaTest(variables, 0.05d));
    }
    
    public void calculateKMeanSummary(KMeans kMeans){
        KMeansPlusPlusClusterer kMeanPlus = new KMeansPlusPlusClusterer(4);
        kMeans.setCluster(kMeanPlus.cluster(kMeans.getVarValues()));
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
