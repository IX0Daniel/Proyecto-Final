package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:mariadb://localhost:3306/ConnectWork";
    private static final String USERNAME = "AdminDBConnectWork";
    private static final String PASSWORD = "ConnectPSWRD123";

    public static Connection getConexion() throws Exception {
        Class.forName("org.mariadb.jdbc.Driver");
        //Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    /*
    public static Connection getConexion() throws SQLException {


        try {
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar con la base de datos "+e);
        }

        return null;
    }
*/
}
