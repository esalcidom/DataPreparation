/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorialanalysis;

import java.io.*;
import java.util.*;

/**
 *
 * @author Emmanuel
 */
public class CvsReader {
    private ArrayList<String[]> cvsData = new ArrayList<String[]>();
    
    public CvsReader(String path){
        readCvs(path);
    }
    public CvsReader(File file){
        
    }
    private void readCvs(String path){
        try{
            BufferedReader dataBR = new BufferedReader(new FileReader(new File(path)));
            String line = "";
            
            while((line = dataBR.readLine()) != null){
                String[] cvsLine = line.split(",");
                cvsData.add(cvsLine);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public ArrayList<String[]> getData(){
        return cvsData;
    }
    
    public double[][] getDataAt(int[] colIndex){
        double[][] elements = new double[(cvsData.size())][colIndex.length];
        
        int newColumnIndex = 0;
        for(int colIndexValue: colIndex){
            for(int row=0;row<cvsData.size();row++){   
                String[] line = cvsData.get(row);
                elements[row][colIndexValue] = Double.parseDouble(line[newColumnIndex]);
            }
            newColumnIndex++;
        }
        
        return elements;
    }
    
}
