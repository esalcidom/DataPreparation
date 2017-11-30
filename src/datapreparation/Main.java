/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 *
        * INPUT PROCESS
        * 1. Start validating the number of inputs and if the path of the csv is correct then start the process
        * 2. set the path of file and start the 
        * 
        * PROCESS TO DEFINE AND VALIDATE
        * * 1. Define the data set by definitOp method which only gets the name, values and distinct elements of columns, sub type
        * 1.5 User need to define if the distinct values found are correct NOTE...The method to combine values works now is needed the input from user
        * * 2. Define if the data set is enough to do the investigation.
        * * 3. Handle null, empty or wrong data and if necessary identify the rows that should be deleted. NOTE..right now the way to handle is to delete the rows
        * 3.5. User need to define if the rows with wrong data should be deleted or handle
        * * 4. Define one more time if the data set is enough to do the investigation.
        * * 4. Define the types of variables for future statistical analysis
        * * 4.5 User need to define if the columns types are define correctly.
        * * 5 Creates new table which will define user if it is the correct one to use in the investigation
        * 5.5 User need to define if empty values need to be changed or just to remove them.
        * * 6. Converting Text Variables to Numerical variables. assign a Remap value
        * * 7. Converting Numerical variables to Categorical variables
        * 8. Make sure that user is agree with the variables
        * * BEGINNING OF THE STATISTICAL ANALYSIS
        * * 8. Generate Statistic Summary for each variable
        * * 9. Create Contingency tables for all variables that contains categorical values
        * 10. Calculate Lineal regression
        * 11. Calculate ANOVA
        * ///////NOTE: NEED TO CHECK HOW TO HANDLE STRING VARIABLES TO CATEGORY VARIABLES
        * 12. Clostering k-Means
        * 13. Detect variable distribution???????
 */
package datapreparation;
/**
 *
 * @author Emmanuel
 */
public class Main {
    
    public static void main(String[] args) {
        
        if(args.length == 0)
            System.out.println("Main | Error | Please specify the CSV file path to analyse");
        else{
            DataAnalysisProcess process = new DataAnalysisProcess(args[0]);
            process.startAnalysis();
        }
        System.exit(0);
    }
}
