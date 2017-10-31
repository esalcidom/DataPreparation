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
    
    public void defineVariableSubType(DataDef data, List<String> listValues){
        Random random = new Random();
        List<String> listSearch = new ArrayList<String>();
        while(listSearch.size()<10){
            String element = listValues.get(random.nextInt(listValues.size()));
            element = this.cleanString(element);
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
        return value.replaceAll("[^a-zA-Z0-9áÁíÍúÚéÉóÓñÑ.\\s]", "").trim();
    }

    public void remapAlphaToNumeric(DataDef data){
        //As name says this remaps the values to only index base. NOTE THE IMPLEMENTATION IS MISSING IN TABLE CLASS
        int indexValues = data.getOriginalValues().size();
        for(int i=0;i<indexValues;i++){
            
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
 
    ////////SUMERAIZE OPERATION

    public void summerizeData(DataDef variableDef){
        double[] data = generateArrayDouble(variableDef.getNumericValues());
        //calculateQuartile(variableDef);
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
        try{ 
            Collections.sort(list);
            int sizeIndex = list.size();
            if(variableDef.getDistHead().size() > 4){
                variableDef.setMidQuartile(list.get(sizeIndex / 2));
                variableDef.setLowQuartile(list.get(sizeIndex / 4));
                variableDef.setUpQuartile(list.get((sizeIndex / 4) * 3));
            }
            else if(variableDef.getDistHead().size() == 4 || variableDef.getDistHead().size() == 3){
                list = castStringToDoubleList(variableDef.getDistHead());
                Collections.sort(list);
                variableDef.setMidQuartile(list.get(1));
                variableDef.setLowQuartile(list.get(0));
                variableDef.setUpQuartile(list.get(2));
            }
            variableDef.setMinDif(getMinimumDiffInValues(list)/2);
        }
        catch(Exception e){
            System.out.println("DefOperator | calculateQuartile | Exception | " + e.getMessage());
        }
    }
    
    private double getMinimumDiffInValues(List<Double> list){
        double value = list.get(list.size()-1) - list.get(list.size()-2);
        for(int i=list.size()-2;i>0;i--){
            double newValue = list.get(i) - list.get(i-1);
            if(newValue<value && newValue != 0 && newValue > 0)
                value = newValue;
        }
        return value;
    }
    
    private List<Double> castStringToDoubleList(List<String> list){
        List<Double> doubleList = new ArrayList<Double>();
        for(String value:list){
            doubleList.add(Double.parseDouble(value));
        }
        return doubleList;
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
    public void mapToCategorical(DataDef variableDef){
        
        if(variableDef.getLowQuartile() == 0.0d && variableDef.getMidQuartile() == 0.0d && variableDef.getUpQuartile() == 0.0d)
            calculateQuartile(variableDef);
        if(variableDef.getMin() == 0.0d && variableDef.getMax() == 0.0d){
            variableDef.setMax(createMax(generateArrayDouble(variableDef.getNumericValues())));
            variableDef.setMin(createMin(generateArrayDouble(variableDef.getNumericValues())));
        }
        if(variableDef.getDistHead().size()>4)   
            categorizeToFour(variableDef);
        else if(variableDef.getDistHead().size()==4 || variableDef.getDistHead().size()==3)
            categorizeToThree(variableDef);
    }
    
    private void categorizeToFour(DataDef variableDef){
        List<Double> numericalValues = variableDef.getNumericValues();
        Map<String,String> map = new HashMap<String,String>();
        
        StringBuilder index = new StringBuilder("1");
        StringBuilder tag = new StringBuilder(variableDef.getMin() +" - "+ variableDef.getLowQuartile());
        map.put(index.toString(), tag.toString());
        index.delete(0, index.length());
        tag.delete(0, tag.length());
        
        index.append("2");
        tag.append((variableDef.getLowQuartile()+variableDef.getMinDif()) + " - " + variableDef.getMidQuartile());
        map.put(index.toString(), tag.toString());
        index.delete(0, index.length());
        tag.delete(0, tag.length());
        
        index.append("3");
        tag.append((variableDef.getMidQuartile()+variableDef.getMinDif()) + " - " + variableDef.getUpQuartile());
        map.put(index.toString(), tag.toString());
        index.delete(0, index.length());
        tag.delete(0, tag.length());
        
        index.append("4");
        tag.append((variableDef.getUpQuartile()+variableDef.getMinDif()) + " - " + variableDef.getMax());
        map.put(index.toString(), tag.toString());
        
        variableDef.setCategoricalValue(createCategoricalValues(numericalValues,variableDef.getLowQuartile(),variableDef.getMidQuartile(),variableDef.getUpQuartile()));
        variableDef.setRemapValues(map);
    }
    
    private void categorizeToThree(DataDef variableDef){
        List<Double> numericalValues = variableDef.getNumericValues();
        Map<String,String> map = new HashMap<String,String>();
        
        StringBuilder index = new StringBuilder("1");
        StringBuilder tag = new StringBuilder(variableDef.getLowQuartile() +" - "+ variableDef.getMidQuartile());
        map.put(index.toString(), tag.toString());
        index.delete(0, index.length());
        tag.delete(0, tag.length());
        
        index.append("2");
        tag.append((variableDef.getMidQuartile()+variableDef.getMinDif()) + " - " + variableDef.getUpQuartile());
        map.put(index.toString(), tag.toString());
        
        variableDef.setCategoricalValue(createCategoricalValuesToThree(numericalValues,variableDef.getLowQuartile(),variableDef.getMidQuartile(),variableDef.getUpQuartile()));
        variableDef.setRemapValues(map);
    }
    
    //Numerical can be done with the assignation of index to all values.
    public void mapToNumerical(DataDef variableDef){
        List<String> headValues = variableDef.getDistHead();
        Map<String,String> map = new HashMap<String,String>();
        StringBuilder stringIndex = new StringBuilder();
        for(int i=0;i<headValues.size();i++){
            stringIndex.append(i+1);
            map.put(headValues.get(i), stringIndex.toString());
            stringIndex.delete(0, stringIndex.length());
        }
        variableDef.setNumericValues(createListbyDistinct(map,variableDef.getOriginalValues()));
        variableDef.setRemapValues(map);
    }
    
    private List<Double> createListbyDistinct(Map<String,String> map, List<String> originalValues){
        List<Double> numValues = new ArrayList<Double>(originalValues.size());
        for(String originalKey : originalValues){
            numValues.add(Double.parseDouble(map.get(originalKey)));
        }
        return numValues;
    }
    
    public void stringValuesToDouble(DataDef variableDef){
        List<Double> numList = new ArrayList<Double>();
        for(String value : variableDef.getOriginalValues()){
            try{
                numList.add(Double.parseDouble(value));
            }
            catch(Exception e){
                numList.add(null);
            }
        }
        variableDef.setNumericValues(numList);
    }

    private List<String> createCategoricalValues(List<Double> numericalValues, double lowQuartile, double midQuartile, double upQuartile) {
        List<String> values = new ArrayList<String>();
        for(int i=0;i<numericalValues.size();i++){
            if(numericalValues.get(i)<=lowQuartile)
                values.add("1");
            else if(numericalValues.get(i)>lowQuartile && numericalValues.get(i)<=midQuartile)
                values.add("2");
            else if(numericalValues.get(i)>midQuartile && numericalValues.get(i)<=upQuartile)
                values.add("3");
            else
                values.add("4");
        }
        return values;
    }

    private List<String> createCategoricalValuesToThree(List<Double> numericalValues, double lowQuartile, double midQuartile, double upQuartile) {
        List<String> values = new ArrayList<String>();
        for(int i=0;i<numericalValues.size();i++){
            if(numericalValues.get(i)>=lowQuartile && numericalValues.get(i)<=midQuartile)
                values.add("1");
            else if(numericalValues.get(i)>midQuartile && numericalValues.get(i)<=upQuartile)
                values.add("2");
        }
        return values;
    }
}
