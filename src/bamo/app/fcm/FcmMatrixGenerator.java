/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bamo.app.fcm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *This class calculate the weight and generate the needed matrix. using the value loaded from the file.
 * @author bamo
 */
public class FcmMatrixGenerator {
    private ArrayList<int [][]> data;// the data needed for the 
    //the two properties are needed so as to pad the result with the number of necessary zeros
    private int rowNum;
    private int colNum;
    private final int DIVISOR = 3;
    private double [][]matrixData;
    
    public FcmMatrixGenerator(ArrayList<int[][]> data,int rowNum,int colNum)throws Exception{
        if (data ==null || rowNum==0 || colNum==0) {
           throw new Exception("invalid argument supplied for constructor"); 
        }
        this.data = data;
        this.rowNum = rowNum;
        this.colNum = colNum;
    }
    //calculate the weight for the data entered
    public double [][] calculateAllWeight(){
        if (matrixData!=null && matrixData.length !=0) {
            return matrixData;
        }
        double [][] result = new double[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            int [][] current = data.get(i);
            double [] tempWeight = calculateWeight(current);
            result[i] = tempWeight;
        }
        if (result.length < rowNum) {
            result =padRowWithZero(result);
        }
        matrixData = result;
        return result;
    }

    private double[] calculateWeight(int[][] current) { 
        int lowValue = -4;
        int highValue = 4;
        int len =Math.abs(lowValue-highValue) + 1;
        int [][] countValues = parseArray(current,len);
        //perform the calculation and return as single value
        double [] weights = calculateRelationship(countValues,current.length);
        return weights;
    }
    //this method count the amount of count values that is present in the file.
    private int [][] parseArray(int [][] current,int len){
        // the array dimension must be uniform
        int [][]countValues = new int[current[0].length][len];
        for (int i = 0; i < current.length; i++) {
            for (int j = 0; j < current[i].length; j++) {
                int value = current[i][j];
                // need to estimate the correct index based on the value
                //before indexing since the value can be negative.
                int index = calculateIndex(value);
                countValues[j][index]++;
            }
        }
        return countValues;
    }

    private double[] calculateRelationship(int[][] countValues,int len) {
        //need the triangle rule calculation here
        double [] membershipValue= new double[]{-1.8f,-1.2f,-0.6f,-0.2f,0f,0.6f,1.2f,1.8f,2.4f};
        double [] result = new double[countValues.length];
        for (int i = 0; i < countValues.length; i++) {
            int [] counts = countValues[i];
            double sum =0f;
            for (int j = 0; j < counts.length; j++) {
                sum+=(membershipValue[j] *(counts[j])/len);
            }
            result[i]=sum/DIVISOR;
        }
        if (result.length < colNum) {
            result = padColumnWithZero(result);
        }
        return result;
    }

    private double[][] padRowWithZero(double[][] result) {
        int len = rowNum- result.length;
        List<double []> list = Arrays.asList(result);
        for (int i = 0; i < len; i++) {
           list.add(new double [result[0].length]);
        }
        Float [][] temp =list.toArray(new Float [][]{});
        return unbox(temp);
    }

    private double[][] unbox(Float[][] temp) {
        double [][] result = new double[temp.length][];
        for (int i = 0; i < temp.length; i++) {
            result[i] = new double[temp[i].length];
            for (int j = 0; j < temp[i].length; j++) {
                result[i][j]= temp[i][j].doubleValue();
            }
        }
        return result;
    }

    private double[] padColumnWithZero(double[] res) {
        double [] result = new double[colNum];
        for (int i = 0; i < res.length; i++) {
            result[i]= res[i];
        }
        return result;
    }

    private int calculateIndex(int value) {
        int min=4;//positive value for min
       return  min + value;
        
    }
}
