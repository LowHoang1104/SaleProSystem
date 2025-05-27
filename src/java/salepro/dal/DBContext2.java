/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class DBContext2 {
      protected Connection connection;
    private static String currentDbName;

    public static void setCurrentDatabase(String dbName) {
        currentDbName=dbName;
        
    }

     public static String getCurrentDatabase() {
        return currentDbName;       
    }
    public static void clearDatabase() {
        currentDbName="";
    }

    public DBContext2() {
        try {
            String user = "sa";
            String pass = "123";
            String url = "jdbc:sqlserver://LAPTOP-K39LMUDO:1433;databaseName= "+ DBContext2.currentDbName;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
           if((new DBContext1()).connection!=null){
            System.out.println("Connect success");
               System.out.println(currentDbName);
        }else{
            System.out.println("Connect false");
        }
    }
}
