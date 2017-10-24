/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataSet;

import com.mockrunner.mock.jdbc.MockResultSet;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

/**
 *
 * @author Emmanuel
 */
public class DefOperator {
    
    //private List<String> varData;
    //private List<VariableType> varType;
    
    public DefOperator(List data){
        //this.varData = data;
    }
    
    public DefOperator(){
        
    }
    
    public List<String> getDistinctValues(List<String> values){
        return values.stream().distinct().collect(Collectors.toList());
    }
    
    public void defType(){
        
    }
    
    public void defineVariableType(DataDef variable){
        List<VariableType> list = new ArrayList<VariableType>();
        if(isCategorical(variable.getPopulation(), variable.getDistHead().size()))
            list.add(VariableType.CATEGORICAL);
        if(isBinary(variable.getDistHead().size()))
            list.add(VariableType.BINARY);
        if(isContinuous(variable.getVarSubType()))
            list.add(VariableType.CONTINUOUS);
    }
    
    public void defineVariableSubType(DataDef data){
        Random random = new Random();
        List<String> listSearch = new ArrayList<String>();
        while(listSearch.size()<10){
            String element = data.getDistHead().get(random.nextInt(data.getDistHead().size())); 
            if(element.equals("") || element.equals(" "))
                continue;
            listSearch.add(element);
        }
        data.setVarSubType(defineAlpha(listSearch));
        if(data.getVarSubType()==VariableSubType.NONE)
            data.setVarSubType(defineAlphaNumeric(listSearch));
        if(data.getVarSubType()==VariableSubType.NONE || data.getVarSubType()==VariableSubType.ALPHANUMERIC)
            data.setVarSubType(defineNumeric(listSearch));
    }
                
    private VariableSubType defineAlpha(List<String> list){
        for(int i =0;i<list.size();i++){
            if(isAlpha(list.get(i))){
                if(i==list.size()-1){
                    return VariableSubType.ALPHA;
                }
                else
                    continue;
            }
            else{
                return VariableSubType.NONE;
            }            
        }
        return VariableSubType.NONE;
    }
    
    private VariableSubType defineAlphaNumeric(List<String> list){
        for(int i =0;i<list.size();i++){
            if(isAlphanum(list.get(i))){
                if(i==list.size()-1){
                    return VariableSubType.ALPHANUMERIC;
                }
                else
                    continue;
            }
            else{
                return VariableSubType.NONE;
            }            
        }
        return VariableSubType.NONE;
    }
    
    private VariableSubType defineNumeric(List<String> list){
        for(int i =0;i<list.size();i++){
            if(isNumeric(list.get(i)) || isDouble(list.get(i))){
                if(i==list.size()-1){
                    return VariableSubType.NUMERIC;
                }
                else
                    continue;
            }
            else
                return VariableSubType.ALPHANUMERIC;
        }
        return VariableSubType.ALPHANUMERIC;
    }
    
    private boolean isAlpha(String value){
        return StringUtils.isAlphaSpace(value);
    }
    
    private boolean isAlphanum(String value){
        return StringUtils.isAlphanumericSpace(value);
    }
    
    private boolean isNumeric(String value){
        return StringUtils.isNumeric(value);  
    }
    
    private boolean isDouble(String value){
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public String cleanString(String value){
        return value.replaceAll("[^a-zA-Z0-9áÁíÍúÚéÉóÓñÑ\\s]", "").trim();
    }

    public void remapAlphaToNumeric(DataDef data){
        //As name says this remaps the values to only index base. NOTE THE IMPLEMENTATION IS MISSING IN TABLE CLASS
        HashMap<String,Integer> toNumeric = new HashMap<String,Integer>();
        int headSize = data.getDistHead().size();
        for(int i=0;i<headSize;i++){
            toNumeric.put(data.getDistHead().get(i), i++);
        }
        
    }
    
    private List<String> combineHeadValues(String newKey, List<String> head, List<String> list){
        
        for(String listValue: list){
            head.remove(listValue);
        }
        head.add(newKey);
        return head;
    }

    private Map<String,Integer> combineDistValues(String newKey, Map<String,Integer> map,List<String> list){

        int totalCount = 0;
        for(String listValue : list){
            totalCount += map.get(listValue);
            map.remove(listValue);
        }
        map.put(newKey, totalCount);

        return map;
    }

    public void combineVariableValues(DataDef variableDef, String newKey, List<String> listValues){

        //If we replace all the listValues detections for all the newKey values then we can 
        List<String> distHead = variableDef.getDistHead();
        Map distValues = variableDef.getDistValues();
        variableDef.setDistHead(combineHeadValues(newKey, distHead, listValues));
        variableDef.setDistValues(combineDistValues(newKey, distValues, listValues));
    }
    
    //for the moment will work in here for the 
    public boolean isMonotinic(DataDef variableDef){
        if(variableDef.getDistValues().size()==1)
            return true;
        else
            return false;
    }
    
    public boolean isManyMissing(DataDef variableDef){
        int totalMissing = 0;
        
        if(variableDef.getDistValues().containsKey("")){
            totalMissing += variableDef.getDistValues().get("");
        }
        if(variableDef.getDistValues().containsKey(" ")){
            totalMissing += variableDef.getDistValues().get(" ");
        }
        
        if(totalMissing >= (variableDef.getPopulation() * 0.1))
            return true;
        else
            return false;
    }

    public boolean isPopulationEnough(int totalPopulation, int totalVariables){
        if(totalPopulation / totalVariables >= 150){
            return true;
        }
        else{
            if(totalPopulation >= 250){
                return true;
            }
            return false;
        }
    }
    
    private boolean isCategorical(int totalPupulation, int totalDiff){
        //NOTE this is onlly a reference
        if(totalDiff <= (totalPupulation * 0.015))
            return true;
        else
            return false;
    }
    
    private boolean isBinary(int totalDiff){
        if(totalDiff == 2)
            return true;
        else
            return false;
    }
    
    private boolean isContinuous(VariableSubType value){
        if(value.equals(VariableSubType.NUMERIC))
            return true;
        else
            return false;
    }
    
    //Create categorical values for Numerical variable and numerical values from Alpha variables
    public void createNumAndCatVariables(DataDef variableDef){
        if(variableDef.getVarType().equals(VariableType.CONTINUOUS) && variableDef.getVarSubType().equals(VariableSubType.NUMERIC))
            mapToCategorical(variableDef);
        else if(variableDef.getVarType().equals(VariableType.TEXT) && (variableDef.getVarSubType().equals(VariableSubType.ALPHA) || variableDef.getVarSubType().equals(VariableSubType.ALPHANUMERIC)))
            mapToNumerical(variableDef);
    }
    
    ////////SUMERAIZE OPERATION

    public void summerizeData(DataDef variableDef){
        double[] data = generateArrayDouble(variableDef.getNumericValues());
        calculateQuartile(variableDef);
        variableDef.setMean(createMean(data));
        variableDef.setMode(createMode(data));
        variableDef.setGeometricMean(createGeometricMean(data));
        variableDef.setMax(createMax(data));
        variableDef.setMin(createMin(data));
        variableDef.setNormilizeData(createNormalize(data));
        variableDef.setPercentil(createPercentil(data));
        variableDef.setPopulationVariance(createpopulationVariance(data, variableDef.getMean()));
        variableDef.setVariance(createVariance(data,  variableDef.getMean()));
        variableDef.setStandrDev(createSD(data));
        variableDef.setSkewness(createSkewness(data));
        variableDef.setKurtosis(createKurtosis(data));
    }
    
    private void calculateQuartile(DataDef variableDef){
        List<Double> list = variableDef.getNumericValues();
        Collections.sort(list);
        int sizeIndex = list.size();
        variableDef.setMidQuartile(list.get(sizeIndex / 2));
        variableDef.setLowQuartile(list.get(sizeIndex / 4));
        variableDef.setLowQuartile(list.get((sizeIndex / 4) * 3));
    }

    private double createMean(double[] values){
        return StatUtils.mean(values);
    }

    private double[] createMode(double[] values){
        return StatUtils.mode(values);
    }

    private double createGeometricMean(double[] values){
        return StatUtils.geometricMean(values);
    }

    private double createMax(double[] values){
        return StatUtils.max(values);
    }

    private double createMin(double[] values){
        return StatUtils.min(values);
    }

    private double[] createNormalize(double[] values){
        return StatUtils.normalize(values);
    }

    //Don't know if percentile is neccessary
    private double createPercentil(double[] values){
        return StatUtils.percentile(values,95.0);
    }
    private double createPercentil(double[] values, double p){
        return StatUtils.percentile(values,p);
    }
    
    private double createpopulationVariance(double[] values, double mean){
        return StatUtils.populationVariance(values, mean);
    }

    private double createVariance(double[] values, double mean){
        return StatUtils.variance(values, mean);
    }

    private double createSD(double[] values){
        StandardDeviation sd = new StandardDeviation(false);
        return sd.evaluate(values);
    }

    private double createSkewness(double[] values){
        Skewness skewness = new Skewness();
        return skewness.evaluate(values,0,values.length);
    }

    private double createKurtosis(double[] values){
        Kurtosis kurtosis = new Kurtosis();
        return kurtosis.evaluate(values,0,values.length);
    }
    
    private double[] generateArrayDouble(List<Double> values){
        return values.stream().mapToDouble(Double::doubleValue).toArray();
    }
    ////////////////END SUMMERIZE OPERATION
    
    //Categorical can be done with quartiles 3 levels and to set Quartiles need to be done in another method
    private void mapToCategorical(DataDef variableDef){
        List<Double> sortList = variableDef.getNumericValues();
        Collections.sort(sortList);
        int sizeIndex = sortList.size();
        variableDef.setMidQuartile(sortList.get(sizeIndex / 2));
        variableDef.setLowQuartile(sortList.get(sizeIndex / 4));
        variableDef.setLowQuartile(sortList.get((sizeIndex / 4) * 3));
    }
    
    //Numerical can be done with the assignation of index to all values.
    private void mapToNumerical(DataDef variableDef){
        List<String> headValues = variableDef.getDistHead();
        Map<String,String> map = new HashMap<String,String>();
        StringBuilder stringIndex = new StringBuilder();
        for(int i=0;i<headValues.size();i++){
            stringIndex.append(i+1);
            map.put(headValues.get(i), stringIndex.toString());
            stringIndex.delete(0, stringIndex.length());
        }
    }
    
    public void stringValuesToDouble(DataDef variableDef){
        List<Double> numList = new ArrayList<Double>();
        for(String value : variableDef.getStringValues()){
            numList.add(Double.parseDouble(value));
        }
        variableDef.setNumericValues(numList);
    }
}
