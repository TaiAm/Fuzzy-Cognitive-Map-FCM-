/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bamo.app.fcm;

/**
 *This class will get the detail the matrix information from the previous classes
 * and then use this to perform the inference base1
 * and on the parameter that was initially given.
 * an array list that contains the matrix is supplied after which the  data i learned until a convergence is reached
 * after which a threshold value is used to get the disease that show positive based on the value in 
 * @author DELL
 */
public class FcmLearner {
    //the class must take an array list paramter
    private double [][] adjacencyMatrix;
    private double sigmoidFactor =5;
    private double threshold = 0;
    private double error = 0.0001;
    //let the user set the index of the result elements so that the reult can be successfully compared with the given threshold.
    public FcmLearner(double [][] matrix){
        this.adjacencyMatrix = matrix;
    }
    public void setThreshold(double threshold){
        this.threshold = threshold;
    }
    public double getSigmoidFactor(){
        return this.sigmoidFactor;
    }
    public void setSigmoidFactor(double factor){
        this.sigmoidFactor = factor;
    }
    public double getThreshold(){
        return this.threshold;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }
    public double[][] getWeightMatrix(){
        return adjacencyMatrix;
    }
    //there will be two public method, one of the methods will take the 
    /**
     * this method take one single array of input and output the result
     * this method will be return a string that tells the 
     * @param input the input from the user whose value is calculated.
     */
    public FCMResult getSingleResult(double [] input)throws Exception{
        if (this.adjacencyMatrix.length  != input.length) {
            throw new Exception("the length of the input array does not match with the length of the adjascent matrix");
        }
        boolean check = true;
        double [] temp = input;//copy the value of the input in another data so that the variable scope will not be messed with.
        double [] init = new double[input.length];
        //include the whhile loop with the condition here
        int iterationCount=0;
       do {            
            for (int i = 0; i < temp.length; i++) {//update each of the activation value inthe emp variable and test for the 
                for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                    if (i==j) {
                        continue;
                    }
                    init[j] +=temp[j] * adjacencyMatrix[i][j];//update the state of the input
                }
            }
            // add the value 
            double [] previous = temp.clone();
            for (int i = 0; i < init.length; i++) {
                temp[i] = activationFunction(temp[i] + init[i]);
            }
            double check1 = getAbsoluteAverage(previous);
            double check2 = getAbsoluteAverage(temp) ;
            check = Math.abs(check1 - check2)> error;
            iterationCount++;
        } while (check);
        return evaluateResult(temp,iterationCount);
        //evaluate the result when you get here.
    }
    /**
     * this function generated multiple result as compared with the other overload of the function
     * it can be used to generate an array of results by supplying an array of inputs.
     * @param inputs the 2 dimensional array of the input to be provided.
     * @return an array of string equal to the row of the matrix supplied.
     */
    public FCMResult [] getResults(double[][] inputs) throws Exception{
        FCMResult [] result = new FCMResult[inputs.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = getSingleResult(inputs[i]);
        }
        return result;
    }
    //this method will get the absolute average of a n array passed into it
    //so as to calculate the convergence state of the algorithm.
    private double getAbsoluteAverage(double [] inputs){
        double result = 0;
        for (int i = 0; i < inputs.length; i++) {
            result+= Math.abs(inputs[i]);
        }
        result = result/inputs.length;
        return result;
    }
    public double [] activationFunction(double [] inputs){
        double [] result = new double[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            result[i]= activationFunction(inputs[i]);
        }
        return result;
    }
    private double activationFunction(double input){
        //this function just calculate the sigmoid function operation
//        1 + e-mx
        double temp = 1 + Math.exp(-1 * input * this.sigmoidFactor);
        double result = (double)1/temp;
        return result;
    }

    private FCMResult evaluateResult(double[] temp,int count) {
        //this function evaluate the output of the final result to produce a result object
        return new FCMResult(temp,count);
    }
}
