/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bamo.app.fcm;

/**
 *This class represent the result that is return from the training. it help the programmer return the necessary information needed from the api
 * it contain method to transform the result into the desirable format for proper operation
 * @author DELL
 */
public class FCMResult {
    private double [] finalResult;
    //the variable holds information about the index of the arrray value
    private int iteration;
    public FCMResult(double [] value, int iteration){
        this.finalResult = value;
        this.iteration = iteration;
    }
    
    public double [] getResult(){
        return finalResult;
    }
    
    //function to get the number of iteration performed
    public int getNumberOfIteration(){
        return iteration;
    }
}
