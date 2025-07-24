package salepro.controller.management.cashier;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import salepro.dao.CustomerDAO;
import salepro.dao.StoreDAO;
import salepro.models.Customers;

/**
 *
 * @author MY PC
 */
@WebServlet(name = "AddCustomerServlet", urlPatterns = {"/AddCustomerServlet"})
public class AddCustomerServlet extends HttpServlet {

    // Validation patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(03|05|07|08|09|01[2|6|8|9])[0-9]{8}$"
    );
    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^[\\p{L}\\s]{2,100}$", Pattern.UNICODE_CHARACTER_CLASS
    );

    private static final String ADD_CUSTOMER_JSP = "view/jsp/employees/add_customer_panel.jsp";

    private Gson gson = new Gson();
    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            sendJsonError(response, "Action parameter is required");
            return;
        }

        try {
            switch (action.trim()) {
                case "loadForm":
                    handleLoadForm(request, response);
                    break;
                case "validateField":
                    handleValidateField(request, response);
                    break;
                case "saveCustomer":
                    handleSaveCustomer(request, response);
                    break;
                default:
                    sendJsonError(response, "Invalid action: " + action);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonError(response, "Server error: " + e.getMessage());
        }
    }

    // Load empty form
    private void handleLoadForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set empty attributes
        request.setAttribute("branchId", "1");
        request.setAttribute("fullName", "");
        request.setAttribute("phone", "");
        request.setAttribute("email", "");
        request.setAttribute("address", "");
        request.setAttribute("gender", "");
        request.setAttribute("description", "");
        request.setAttribute("birthDate", "");

        request.getRequestDispatcher(ADD_CUSTOMER_JSP).forward(request, response);
    }

    // Validate single field
    private void handleValidateField(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String fieldName = request.getParameter("fieldName");
        String fieldValue = request.getParameter("fieldValue");

        if (fieldName == null || fieldName.trim().isEmpty()) {
            out.print(gson.toJson(createValidationResult(false, "Field name is required")));
            return;
        }

        ValidationResult result = validateField(fieldName.trim(), fieldValue);
        out.print(gson.toJson(result));
    }

    // Validate individual field
    private ValidationResult validateField(String fieldName, String fieldValue) {

        String value = (fieldValue != null) ? fieldValue.trim() : "";

        switch (fieldName) {
            case "fullName":
                return validateFullName(value);
            case "phone":
                return validatePhone(value);
            case "email":
                return validateEmail(value);
            case "address":
                return validateAddress(value);
            case "birthDate":
                return validateBirthDate(value);
            case "gender":
                return validateGender(value);
            case "description":
                return validateDescription(value);
            default:
                return createValidationResult(false, "Unknown field: " + fieldName);
        }
    }

    // Individual field validations
    private ValidationResult validateFullName(String value) {
        if (value.isEmpty()) {
            return createValidationResult(false, "Họ và tên là bắt buộc");
        }
        if (value.length() < 2) {
            return createValidationResult(false, "Họ và tên phải có ít nhất 2 ký tự");
        }
        if (value.length() > 100) {
            return createValidationResult(false, "Họ và tên không được vượt quá 100 ký tự");
        }

        if (!NAME_PATTERN.matcher(value).matches()) {
            return createValidationResult(false, "Họ và tên chỉ được chứa chữ cái và khoảng trắng");
        }
        return createValidationResult(true, "");
    }

    private ValidationResult validatePhone(String value) {
        if (value.isEmpty()) {
            return createValidationResult(false, "Số điện thoại là bắt buộc");
        }

        if (!PHONE_PATTERN.matcher(value).matches()) {
            return createValidationResult(false, "Số điện thoại không đúng định dạng");
        }

        // Check duplicate
        try {
            if (customerDAO.checkPhoneExists(value)) {
                Customers existing = customerDAO.findByPhone(value);
                String name = (existing != null) ? existing.getFullName() : "khách hàng khác";
                return createValidationResult(false, "Số điện thoại đã được sử dụng bởi " + name);
            }
        } catch (Exception e) {
            System.err.println("Error checking phone duplicate: " + e.getMessage());
        }

        return createValidationResult(true, "");
    }

    private ValidationResult validateEmail(String value) {
        if (value.isEmpty()) {
            return createValidationResult(false, "Email là bắt buộc");
        }
        if (value.length() > 100) {
            return createValidationResult(false, "Email không được vượt quá 100 ký tự");
        }
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            return createValidationResult(false, "Email không đúng định dạng");
        }

        // Check duplicate
        try {
            if (customerDAO.checkEmailExists(value)) {
                return createValidationResult(false, "Email đã được sử dụng bởi khách hàng khác");
            }
        } catch (Exception e) {
            System.err.println("Error checking email duplicate: " + e.getMessage());
        }

        return createValidationResult(true, "");
    }

    private ValidationResult validateAddress(String value) {
        // Address is optional
        if (value.length() > 255) {
            return createValidationResult(false, "Địa chỉ không được vượt quá 255 ký tự");
        }
        return createValidationResult(true, "");
    }

    private ValidationResult validateBirthDate(String value) {
        if (value.isEmpty()) {
            return createValidationResult(false, "Ngày sinh là bắt buộc");
        }

        try {
            LocalDate birthDate = LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate today = LocalDate.now();
            LocalDate minDate = today.minusYears(150);

            if (birthDate.isAfter(today)) {
                return createValidationResult(false, "Ngày sinh không thể trong tương lai");
            }
            if (birthDate.isBefore(minDate)) {
                return createValidationResult(false, "Ngày sinh không hợp lệ (quá xa)");
            }

        } catch (DateTimeParseException e) {
            return createValidationResult(false, "Ngày sinh không đúng định dạng");
        }

        return createValidationResult(true, "");
    }

    private ValidationResult validateGender(String value) {
        if (value.isEmpty()) {
            return createValidationResult(false, "Giới tính là bắt buộc");
        }
        if (!value.equals("Male") && !value.equals("Female") && !value.equals("Other")) {
            return createValidationResult(false, "Giới tính không hợp lệ");
        }
        return createValidationResult(true, "");
    }

    private ValidationResult validateDescription(String value) {
        // Description is optional
        if (value.length() > 500) {
            return createValidationResult(false, "Ghi chú không được vượt quá 500 ký tự");
        }
        return createValidationResult(true, "");
    }

    private ValidationResult createValidationResult(boolean valid, String message) {
        ValidationResult result = new ValidationResult();
        result.valid = valid;
        result.message = message;
        return result;
    }

    private void sendJsonError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", message);
        out.print(gson.toJson(error));
    }

    // Fixed handleSaveCustomer method
    private void handleSaveCustomer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // Validate all fields first
            Map<String, String> formData = extractFormData(request);
            Map<String, String> validationErrors = validateAllFields(formData);

            if (!validationErrors.isEmpty()) {
                Map<String, Object> result = new HashMap<>();
                result.put("success", false);
                result.put("message", "Dữ liệu không hợp lệ");
                result.put("errors", validationErrors);
                out.print(gson.toJson(result));
                return;
            }

            // Extract required fields for DAO method
            String fullName = formData.get("fullName");
            String phone = formData.get("phone");
            String email = formData.get("email");
            String gender = formData.get("gender");
            String birthDate = formData.get("birthDate"); // Format: yyyy-MM-dd
            String address = formData.get("address");
            String description = formData.get("description");

            boolean saved = customerDAO.insertCustomer2(fullName, phone, email, address, description, gender, birthDate);

            // Respone JSON
            Map<String, Object> result = new HashMap<>();
            if (saved) {
                result.put("success", true);
                result.put("message", "Thêm khách hàng thành công!");
                Customers customer = createCustomerFromFormData(formData);
                result.put("customer", customer);
            } else {
                result.put("success", false);
                result.put("message", "Lỗi khi lưu khách hàng. Vui lòng thử lại!");
            }

            out.print(gson.toJson(result));

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "Lỗi hệ thống: " + e.getMessage());
            out.print(gson.toJson(result));
        }
    }

    // Extract form data from request
    private Map<String, String> extractFormData(HttpServletRequest request) {
        Map<String, String> formData = new HashMap<>();
        formData.put("fullName", getParameterValue(request, "fullName"));
        formData.put("phone", getParameterValue(request, "phone"));
        formData.put("email", getParameterValue(request, "email"));
        formData.put("address", getParameterValue(request, "address"));
        formData.put("birthDate", getParameterValue(request, "birthDate"));
        formData.put("gender", getParameterValue(request, "gender"));
        formData.put("description", getParameterValue(request, "description"));
        return formData;
    }

    private Customers createCustomerFromFormData(Map<String, String> formData) {
        Customers customer = new Customers();

        customer.setFullName(formData.get("fullName"));
        customer.setPhone(formData.get("phone"));
        customer.setEmail(formData.get("email"));
        customer.setAddress(formData.get("address"));
        customer.setGender(formData.get("gender"));
        customer.setDescription(formData.get("description"));

        try {
            LocalDate localDate = LocalDate.parse(formData.get("birthDate"), DateTimeFormatter.ISO_LOCAL_DATE);
            Date birthDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            customer.setBirthDate(birthDate);
        } catch (DateTimeParseException e) {
            customer.setBirthDate(null);
        }

        Date now = new Date();
        customer.setCreatedAt(now);
        customer.setCustomerId(0);
        customer.setTotalSpent(0.0);
        customer.setRank("Bronze");

        return customer;
    }

    private String getParameterValue(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        return (value != null) ? value.trim() : "";
    }

    private Map<String, String> validateAllFields(Map<String, String> formData) {
        Map<String, String> errors = new HashMap<>();

        for (Map.Entry<String, String> entry : formData.entrySet()) {
            String fieldName = entry.getKey();
            String fieldValue = entry.getValue();

            ValidationResult result = validateField(fieldName, fieldValue);
            if (!result.valid) {
                errors.put(fieldName, result.message);
            }
        }
        return errors;
    }

    public static class ValidationResult {

        public boolean valid;
        public String message;
    }

}
