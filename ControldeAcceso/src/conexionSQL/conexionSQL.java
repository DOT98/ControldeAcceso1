
package conexionSQL;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author danie
 */
public class conexionSQL {
    
    Connection conectar = null;
    
    public Connection conexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar= (Connection)DriverManager.getConnection("jdbc:mysql://localhost/control","root","");
        
            JOptionPane.showMessageDialog(null, "Conexion exitosa");
        
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se hizo la conexion" + e.getMessage());
        }
        return conectar;
    } 
    
    
}
