/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bamo.app.fcm;

/**
 *class with static method to translate data into the fuzzy values
 * @author DELL
 */
public class ValueConverter {
    
    public static double translateValue(int value){
        switch(value){
            case 0 :
                return 0;
            case 1 :
                return 0.2;
            case 2:
                return 0.5;
            case 3:
                return 0.7;
            case 4:
                return 0.9;
            default:
                return 0;
        }
    }
    
    public static double translateAge(int value){
       switch(value){
           case 1:
               return 0.25;
           case 2:
           case 3:
               return 0.5;
           case 4:
           case 5:
               return 0.8;
           default:
               return 0;
       }
    }
}
