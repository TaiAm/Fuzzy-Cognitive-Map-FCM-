/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bamo.app.fcm;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *This class will load the data and also  process it 
 * @author bamo
 */
public class DataLoader {
    private String filename;
    private ArrayList<int[][]> dataFieldBuffer = new ArrayList();
    public DataLoader(String filename){
        this.filename = filename;
    }
    
    private String readString()throws Exception{
        FileReader reader = new FileReader(filename);
        StringBuilder temp = new StringBuilder();
        char [] b = new char[1024];
        while(reader.read(b)!= -1){
            temp.append(new String(b));
        }
        return temp.toString();
    }
    
    private void loadBuffer()throws Exception{
        String data = readString();
        //clear the dataFieldBuffer
        dataFieldBuffer.clear();
        String [] str = data.split("#####");
        //assume all is well 
        for (int i = 0; i < str.length; i++) { //for each of the row data
            int [][] temp = processRowData(str[i]);
            dataFieldBuffer.add(temp);
        }
    }
    public ArrayList<int[][]> loadFileData() throws Exception{
       loadBuffer();
       return dataFieldBuffer;
    }
    public ArrayList<int[][]> getFileData(){
        return dataFieldBuffer;
    }
    /**
     * This method process  a csv file that represents a row element
     * @param string
     * @return 
     */
    private int[][] processRowData(String string) throws Exception{
        String [] temp = string.trim().split("\n");
        //make sure is not hash fragment if it is then skip too
        if (isHashFragment(temp[0])) {
            temp = Arrays.copyOfRange(temp, 1, temp.length);
        }
        //check the first element to make sure its not 
       if (!isNumberRow(temp[0])) {//remove the element
           temp = Arrays.copyOfRange(temp, 1, temp.length);
       }
       int [][] result = new int[temp.length][];
        for (int i = 0; i < temp.length; i++) {
            String str = temp[i];
            if(str.equals("")){
                result[i]= new int[]{0};
                continue;
            }
            result[i]=processFloat(str);
        }
        return result;
    }
    //check if the firstrow of a row item is number row.
    private boolean isNumberRow(String str){
        String [] temp = str.split(",");
        try{
            Integer.parseInt(temp[0].trim());
            return true;
        }
        catch(NumberFormatException ex){
            return false;
        }
        
    }
    private int[] processFloat(String str) {
        String [] temp = str.split(",");
        int [] result = new int[temp.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = temp[i].trim().equals("")?0:Integer.parseInt(temp[i].trim());
        }
        return result;
    }

    private boolean isHashFragment(String string) {
        return string.trim().charAt(0)==',';
    }
}
