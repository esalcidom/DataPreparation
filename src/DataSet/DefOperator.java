/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataSet;

import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

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
    
    private boolean isAlpha(String value){
        return StringUtils.isAlphaSpace(value);
    }
    
    private boolean isAlphanum(String value){
        return StringUtils.isAlphanumericSpace(value);
    }
    
    private boolean isBlank(String value){
        return StringUtils.isBlank(value);
    }
    
    public String cleanString(String value){
        return value.replaceAll("[^a-zA-Z0-9áÁíÍúÚéÉóÓñÑ\\s]", "").trim();
    }

    /**
     * @return the varData
     
    public List<String> getVarData() {
        return varData;
    }

    /**
     * @param varData the varData to set
     
    public void setVarData(List<String> varData) {
        this.varData = varData;
    }
    * 
    */
    
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
    
    public void disableVariable(){
        
    }
    
    //for the moment will work in here for the 
    public boolean isMonotinic(DataDef variableDef){
        if(variableDef.getDistValues().size()==1)
            return true;
        else
            return false;
    }

    public boolean isPopulationEnough(ArrayList<Integer> totals){
        int totalPopulation = totals.get(1);
        int totalVariables = totals.get(0);
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
    
}
