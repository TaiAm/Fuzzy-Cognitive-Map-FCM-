/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bamo.app.fcm;

import java.util.ArrayList;

/**
 *This class that is called directly by the 
 * @author bamo
 */
public class FcmLib {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        try {
            // sample usage for this libarary
            if (args.length!=3) {
                System.out.println("Usage::\nThis sample program should be run with 3 argument,\n the first argument is the path to the file\n the second argument is the num of rows\n the third argument is the number of columns.");
            }
            //manually pass the argument here.
            String [] arg = new String[3];
            arg[0]="disease formatted-1.csv";
            arg[1]="32";
            arg[2]="32";
            double [][] printResult = getMatrix(arg[0], Integer.parseInt(arg[1]), Integer.parseInt(arg[2]));
            printMatrix(printResult);
            FcmLearner learner = new FcmLearner(printResult);
            //get the input to test with 
            //this input is just for testing
            double [] input = new double[]{0.2,0.3,0.5,0.5,0.6,0.12,0.78,0.0,0,0.2,0.3,0.5,0.5,0.12,0.78,0.0,0.2,0.3,0.5,0.5,0.6,0.12,0.78,0.0,0,0.2,0.3,0.5,0.5,0.12,0.78,0.0};
            FCMResult result =learner.getSingleResult(input);
            System.out.println("printing the result array");
            printSingleArray(result.getResult());
            System.out.println("The total number of iteration is "+result.getNumberOfIteration());
//          System.out.println(result.);
        } catch (Exception ex) {
            System.out.println("error occur:\n"+ex.getMessage());
            ex.printStackTrace();
         }
    }
    private static void printMatrix(double [][] value){
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                System.out.print(value[i][j]+" ");
            }
            System.out.println();
            System.out.println("#############################################################################");
        }
        System.out.println();
        System.out.println("data successfully generated with " + value.length + " rows and " + value[0].length + " columns");
    }
    public static void printSingleArray(double [] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            System.out.print(' ');
        }
        System.out.println();
    }
    public static double [][]  getMatrix(String filename,int rowNum,int colNum)throws Exception{
        DataLoader dataLoader = new DataLoader(filename);
        ArrayList <int [][]> data = dataLoader.loadFileData();
        FcmMatrixGenerator gen = new FcmMatrixGenerator(data, rowNum, colNum);
        return gen.calculateAllWeight();
    }
    
}
