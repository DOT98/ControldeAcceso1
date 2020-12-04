package Calculator;

import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author anmijurane
 */
public class Processo extends javax.swing.JFrame {

    random acc = new random();
    static String Name_kid;
        
    /**
     * Creates new form Processo
     */
    public Processo() {
        initComponents();  
        //successes();
        GetNumberRigth();
        GetNumberLeft();
        getOperator();
        Operation();        
    }
    
    public Processo(String Name_Kid){
        initComponents();  
        //successes();
        GetNumberRigth();
        GetNumberLeft();
        getOperator();
        Operation();
        this.Name_kid = Name_Kid;
    }
    
    
    static int numberRigth2;
    static int numberLeft2;
    static String operator;
    static int result;
    public static int contCorrect = 0;
    public static int contIncorrect = 0;
    static int resultInt;
    static int contador = 1;
    static Result dl = new Result();
    //Arreglos
    static ArrayList<Integer> ArrRight = new ArrayList<Integer>();
    static ArrayList<String> ArrOperator = new ArrayList<String>();
    static ArrayList<Integer> ArrLeft = new ArrayList<Integer>();
    static ArrayList<Integer> ArrResultInter = new ArrayList<Integer>();
    static ArrayList<Integer> ArrResultExt = new ArrayList<Integer>();
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jLabel1 = new javax.swing.JLabel();
        FirstValue = new javax.swing.JLabel();
        Operator = new javax.swing.JLabel();
        SecondValue = new javax.swing.JLabel();
        equals = new javax.swing.JLabel();
        result_kid = new javax.swing.JTextField();
        Btn_enviar = new javax.swing.JButton();
        Siguiente = new javax.swing.JButton();
        Etq_Name_kid = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        label_numer = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jScrollPane1.setViewportView(jTree1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Resuelve la siguiente operación:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, -1, -1));

        FirstValue.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        FirstValue.setText("0");
        getContentPane().add(FirstValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 25, 31));

        Operator.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        Operator.setText("+/-");
        getContentPane().add(Operator, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 25, 31));

        SecondValue.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        SecondValue.setText("0");
        getContentPane().add(SecondValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 25, 31));

        equals.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        equals.setText("=");
        getContentPane().add(equals, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 20, 31));

        result_kid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                result_kidActionPerformed(evt);
            }
        });
        getContentPane().add(result_kid, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 106, -1));

        Btn_enviar.setText("Verificar y Siguiente");
        Btn_enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_enviarActionPerformed(evt);
            }
        });
        getContentPane().add(Btn_enviar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, -1, -1));

        Siguiente.setText("Resúestas");
        Siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SiguienteActionPerformed(evt);
            }
        });
        getContentPane().add(Siguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        Etq_Name_kid.setText("NOMBRE");
        getContentPane().add(Etq_Name_kid, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 100, -1));
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 190));

        label_numer.setText("1 / 10");
        getContentPane().add(label_numer, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        jLabel4.setText("Operación: ");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Btn_enviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_enviarActionPerformed
        
        try{
        successes();
        GetNumberRigth();
        GetNumberLeft();
        getOperator();
        Operation();
        Etq_Name_kid.setText(Name_kid.toUpperCase());
        result_kid.setText("");
        result_kid.requestFocus();
        contador++;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Ingresa un número.");
        }        
        //System.out.println("Buenas: " +contCorrect);
        //System.out.println("Malas: " +contIncorrect);
        //this.show();
        //this.dispose();  
                
        
        label_numer.setText(""+(contador)+" / 10");
        System.out.println("contador " +contador);
        
        if (contador == 11) {
            dl.show();
            this.dispose();
        }
    }//GEN-LAST:event_Btn_enviarActionPerformed
           
    public void successes(){        
        String kid_result = result_kid.getText();
        resultInt = Integer.parseInt(kid_result);
        ArrResultExt.add(resultInt);
        if (result == resultInt) {
            //JOptionPane.showMessageDialog(null, "Correcto");            
            contCorrect++;
        }else if (result != resultInt){
            //JOptionPane.showMessageDialog(null, "Incorrecto");
            contIncorrect++;
            //System.out.println("correcto: " +contIncorrect);
        }        
                                
        Result.setResult_ext(contCorrect, contIncorrect);
        
        //System.out.println(""+a);
        //System.out.println("Ya no hay mas operaciones");
    }
           
//    public int SendData(){
//        int datosa = contCorrect;                                
//        return datosa;
//    }
    
    private void SiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SiguienteActionPerformed
        /*        
        Result open = new Result();
        open.show();
        this.dispose();*/
    }//GEN-LAST:event_SiguienteActionPerformed

    private void result_kidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_result_kidActionPerformed
        
    }//GEN-LAST:event_result_kidActionPerformed

    public void GetNumberRigth(){
        double numberRigth = acc.getRandom(0,9);
        numberRigth2 = acc.ConvrtInteger(numberRigth);
        ArrRight.add(numberRigth2);
        FirstValue.setText(""+numberRigth2);        
    }
    
    public void GetNumberLeft(){        
        double numberLeft = acc.getRandom(0,numberRigth2-1);
        numberLeft2 = acc.ConvrtInteger(numberLeft);
        ArrLeft.add(numberLeft2);
        SecondValue.setText(""+numberLeft2);
        //System.out.println(numberLeft2);    
    }
    
    public void getOperator(){
        operator = acc.Operator();
        ArrOperator.add(operator);
        Operator.setText(operator);
    }
    
    public void Operation(){
        if(operator == "+"){            
            result = numberRigth2 + numberLeft2;            
        }else if(operator == "-"){
            result = numberRigth2 - numberLeft2;
        }        
            //System.out.println(""+result);
            ArrResultInter.add(result);
    }
     
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Processo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Processo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Processo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Processo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Processo().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_enviar;
    private javax.swing.JLabel Etq_Name_kid;
    private javax.swing.JLabel FirstValue;
    private javax.swing.JLabel Operator;
    private javax.swing.JLabel SecondValue;
    private javax.swing.JButton Siguiente;
    private javax.swing.JLabel equals;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel label_numer;
    private javax.swing.JTextField result_kid;
    // End of variables declaration//GEN-END:variables
}