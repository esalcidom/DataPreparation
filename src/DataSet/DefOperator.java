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
        
        if(totalMissing >= (variableDef.getPopulation() * 0.15))
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
    
}
