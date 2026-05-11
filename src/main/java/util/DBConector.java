package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConector {
    private static String url = "jdbc:mysql://localhost:3306/gestion_usuarios";
    private static String user = "root";
    private static String password = "admin";

    private DBConector(){
        //Evitamos que se creen instancias
    }

    //Aplicamos el patron singleton
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
