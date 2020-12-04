package compuertaslogicas;

import javax.swing.JOptionPane;

/**
 *
 * @author danielot
 */
public class CompuertasLogicas {

    static String op;
    static int Value1, Value2;
                
    public CompuertasLogicas(String op, int val1, int val2) {
        CompuertasLogicas.op = op;
        Value1 = val1;
        Value2 = val2;
    }

    int operationAND(int num1, int num2){                             
        return num1&num2;
    }
    
    int operationOR(int num1, int num2){
        return num1|num2;
    }
    
    int operationNOT(int num1){                
        if (num1 == 1) {
            return 0;
        }else{
            return 1;
        }        
    }
    
    String makeoperation(){
        if (op.equals("AND")) {
            return ""+operationAND(Value1, Value2);
        }if (op.equals("OR")) {
            return ""+operationOR(Value1, Value2);
        }if (op.equals("NOT")) {
            return ""+operationNOT(Value1);
        }else{
            JOptionPane.showMessageDialog(null, "Selecciona una opcion");
        }
       return "nada";
    }      
}

