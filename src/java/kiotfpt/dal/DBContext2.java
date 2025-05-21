/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kiotfpt.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MY PC
 */
public class DBContext2 {
    protected Connection connection;
    private static ThreadLocal<String> currentDbName = new ThreadLocal<>();

    public static void setCurrentDatabase(String dbName) {
        currentDbName.set(dbName);
    }

    public static void clearDatabase() {
        currentDbName.remove();
    }

    public DBContext2() {
        try {
            String dbName = currentDbName.get();
            if (dbName == null) {
                throw new RuntimeException("Database name not set.");
            }

            String user = "sa";
            String pass = "123";
            String url = "jdbc:sqlserver://LAPTOP-K39LMUDO:1433;databaseName=Shop" ;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      public static void main(String[] args) {
        
        if((new DBContext1()).connection!=null){
            System.out.println("Connect success");
        }else{
            System.out.println("Connect false");
        }      
    }
}
