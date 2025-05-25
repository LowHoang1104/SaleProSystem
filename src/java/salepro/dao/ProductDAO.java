/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import salepro.dal.DBContext;


/**
 *
 * @author MY PC
 */
public class ProductDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    private static final String GET_DATA = "select * from Products where Status = 1 ";
    private static final String GET_PRODUCTS_BY_ID = "select * from Products where ProductID = ? and Status = 1";
    private static final String GET_PRODUCTS_BY_CATEGORY = "select * from Products where CategoryID = ? and Status = 1";
    private static final String GET_PRODUCTS_BY_TYPE = "select * from Products where TypeID = ? and Status = 1";
    private static final String GET_TOTAL_PRODUCTS = "select count(*) as Total from Products where Status=1";
    private static final String GET_PRODUCTS_NEW_RELEASE = "select * from Products where ReleaseDate >=DATEADD(MONTH,-1,GETDATE()) and Status = 1";
    private static final String INSERT_PRODUCT = "Insert into Products(ProductName, CategoryID, SizeID, ColorID, TypeID, Price, CostPrice, Unit, Description, Images, Status, ReleaseDate)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT = " UPDATE Products SET ProductName = ?, CategoryID = ?, SizeID = ?, ColorID = ?, TypeID = ?, Price = ?, CostPrice = ?, Unit = ?, Description = ?,"
            + " Images = ?, Status = ?, ReleaseDate = ? WHERE ProductID = ?";
    private static final String DELETE_PRODUCT = "update Products SET Status = 0 where ProductID = ?";
    private static final String SEARCH_BY_NAME = "SELECT * FROM Products WHERE Status = 1 AND REPLACE(ProductName, ' ', '') "
            + "COLLATE Latin1_General_CI_AI LIKE ?";

  

   
}
