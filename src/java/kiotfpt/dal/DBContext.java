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
public class DBContext {
    protected Connection connection;
    public DBContext()
    {
        try {
            String user = "sa"; 
            String pass = "123";
            String url = "jdbc:sqlserver://LAPTOP-K39LMUDO:1433;databaseName=CLShop";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public static void main(String[] args) {
        
        if((new DBContext()).connection!=null){
            System.out.println("Connect success");
        }else{
            System.out.println("Connect false");
        }
        
    }

}
