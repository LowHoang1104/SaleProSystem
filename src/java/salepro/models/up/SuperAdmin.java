package salepro.models.up;
import java.time.LocalDateTime;

/**
 * Model class cho Super Admin
 * Tương ứng với bảng SuperAdmins trong database
 */
public class SuperAdmin {
    private Integer superAdminID;
    private String username;
    private String passwordHash;
    private String fullName;
    private String email;
    private LocalDateTime createdAt;

    // Constructors
    public SuperAdmin() {
        this.createdAt = LocalDateTime.now();
    }

    public SuperAdmin(String username, String passwordHash, String fullName, String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    public SuperAdmin(Integer superAdminID, String username, String passwordHash, String fullName, String email, LocalDateTime createdAt) {
        this.superAdminID = superAdminID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Integer getSuperAdminID() {
        return superAdminID;
    }

    public void setSuperAdminID(Integer superAdminID) {
        this.superAdminID = superAdminID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Business Methods
    /**
     * Kiểm tra xem Super Admin có quyền quản lý shop không
     */
    public boolean canManageShops() {
        return superAdminID != null && username != null;
    }

    /**
     * Kiểm tra xem Super Admin có quyền xem báo cáo không
     */
    public boolean canViewReports() {
        return superAdminID != null;
    }

    /**
     * Kiểm tra xem Super Admin có quyền cài đặt hệ thống không
     */
    public boolean canManageSystem() {
        return superAdminID != null;
    }

} 