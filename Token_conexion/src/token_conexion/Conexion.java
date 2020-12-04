package token_conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conexion {

    static Connection conexion = null;
    static Statement comando = null;
    static ResultSet registro;

    public static  Connection MySQLConnect() {

        try {
            String servidor = "jdbc:mysql://localhost:3306/usuarios";
            String usuario = "root";
            String pass = "root";
            //Se inicia la conexión
            conexion = DriverManager.getConnection(servidor, usuario, pass);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error en la conexión a la base de datos: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error en la conexión a la base de datos: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } finally {
            JOptionPane.showMessageDialog(null, "Conexión Exitosa");
        }
        return conexion;
    }
}
