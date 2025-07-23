/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.dao.SuperAdmin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salepro.dal.DBContext1;
import salepro.models.SuperAdmin.ServicePackage;
import java.sql.*;

/**
 *
 * @author MY PC
 */
public class ServicePackageDAO extends DBContext1 {

    PreparedStatement stm;
    ResultSet rs;
    private static final String GET_DATA = "SELECT * FROM ServicePackages WHERE IsActive = 1 ORDER BY DurationMonths ASC";
    private static final String GET_BY_ID = "SELECT * FROM ServicePackages WHERE PackageID = ?";

    public List<ServicePackage> getAllActivePackages() {
        List<ServicePackage> packages = new ArrayList<>();
        try {
            stm = connection.prepareStatement(GET_DATA);
            rs = stm.executeQuery();
            while (rs.next()) {
                packages.add(mapResultSetToPackage(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return packages;
    }

    public ServicePackage findById(Integer packageId) {
        try {
            stm = connection.prepareStatement(GET_BY_ID);
            stm.setInt(1, packageId);
            rs = stm.executeQuery();

            if (rs.next()) {
                return mapResultSetToPackage(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ServicePackage mapResultSetToPackage(ResultSet rs) throws SQLException {
        ServicePackage pkg = new ServicePackage();
        pkg.setPackageId(rs.getInt("PackageID"));
        pkg.setPackageName(rs.getString("PackageName"));
        pkg.setDescription(rs.getString("Description"));
        pkg.setDurationMonths(rs.getInt("DurationMonths"));
        pkg.setPrice(rs.getDouble("Price"));
        pkg.setDiscountPercent(rs.getDouble("DiscountPercent"));
        pkg.setIsActive(rs.getBoolean("IsActive"));

        // Convert SQL Timestamp to LocalDateTime
        Timestamp timestamp = rs.getTimestamp("CreatedAt");
        if (timestamp != null) {
            pkg.setCreatedAt(timestamp.toLocalDateTime());
        }

        return pkg;
    }
}
