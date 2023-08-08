/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbcontext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phamtung
 */
public class DBContext {

    protected Connection connection;

    
    public DBContext() {
        
        connection = DBContext1.getConnection();
  
    }
    
    
//    private static DBContext instance;
//    private Connection connection;
//    private String user = "root";
//    private String pass = "";
//    private String url = "jdbc:mysql://localhost:3306/swp_team4";
//
//    private DBContext() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(url, user, pass);
//        } catch (ClassNotFoundException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static synchronized DBContext getInstance() {
//        if (instance == null) {
//            instance = new DBContext();
//        }
//        return instance;
//    }
//
//    public Connection getConnection() {
//        return connection;
//    }

    
    
    
//    public static void main(String[] args) {
//        DBContext dbContext = new DBContext();
//
//    }
}
