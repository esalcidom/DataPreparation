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
    
    private List<String> varData;
    private List<VariableType> varType;
    
    public DefOperator(List data){
        this.varData = data;
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
     */
    public List<String> getVarData() {
        return varData;
    }

    /**
     * @param varData the varData to set
     */
    public void setVarData(List<String> varData) {
        this.varData = varData;
    }
    
    public ArrayList<Integer> validateVariables(TableData table){
        int totalPopulation = 0;
        int totalVariables = 0;
        ArrayList<Integer> totals = new ArrayList<Integer>(2);
        HashMap<String,DataDef> tableDefMap = table.getDefMap();
        //get every data def to check the diff values
        for(Map.Entry<String,DataDef> entry : tableDefMap.entrySet()){
            DataDef definition = entry.getValue();
            if(isMonotinic(definition)){
                    definition.setIsEnable(false);
            }
            else{
                ///////is necesary to also store de population of the variable in DataDef.
                totalPopulation += definition.getPopulation();
                totalVariables++;
            }
            ///Now we diseabled the variables with no use and now need to check if the amount of population is a good one
        }	
        totals.add(totalVariables);
        totals.add(totalPopulation);
        return totals;
    }
    
    //for the moment will work in here for the 
    public boolean isMonotinic(DataDef variableDef){
        if(variableDef.getDistValues().size()==1)
            return true;
        else
            return false;
    }
    
    //This method need to be called after validateVariables to check which variables are enabled
    public void validateDataSet(TableData table){
        boolean validation = isPopulationEnough(validateVariables(table));
        if(validation){
            ///Continue with the preparation
        }
        else{
            //Send message to user that this data set is incomplete and need to get more information for proper analisys
        }
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
