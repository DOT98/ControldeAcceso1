package Calculator;

/**
 *
 * @author anmijurane
 */

public class random {

    public random() {
    
    }
            
    public static double getRandom(int min, int max){
        double x = (Math.random()*((max-min)+1))+min;
        return x;
    }
    
    public static int ConvrtInteger(double num){
        int number = (int)(Math.ceil(num)); 
        return number;
    }
    
    public static String Operator(){      
        //1 vale suma (+)
        //2 vale resta (-)
        String operator = "";
        double number = getRandom(0,1); 
        int number2 = (int)Math.ceil(number);
        if(number2 == 1){
            System.out.println(""+number2);
            operator = "+";
        }else if(number2 == 2){
            //System.out.println(""+number2);
            operator = "-";         
        }            
        return operator;
    }
    
    
}
