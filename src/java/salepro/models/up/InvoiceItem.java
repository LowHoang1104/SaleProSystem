/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models.up;

import java.util.List;
import salepro.dao.CustomerDAO;
import salepro.models.Customers;
import salepro.models.Users;

/**
 *
 * @author MY PC
 */
public class InvoiceItem {

    private int id;
    private String name;
    private Customers customer;
    private Users user;
    private List<CartItem> cartItems;
    private double totalAmount;
    private int totalItem;

    private double subTotal;
    private double afterdiscountAmount;

    private double paidAmount;
    private double changeAmount;
    private double discount;
    private double discountAmount;

    private double VATPercent;

    private double VATAmount;

    private double shortAmount;
    private boolean type;

    
    private double pointsUsed = 0;     // Điểm đã sử dụng (tạm tính)
    private double pointsToAdd = 0;    // Điểm sẽ tích từ tiền thừa (tạm tính)

    public InvoiceItem() {
    }

    public InvoiceItem(int id, String name) {
        this.id = id;
        this.name = name;
        this.customer = new Customers();
        this.user = new Users();
        this.type = true;
        this.VATPercent = 10;
        this.changeAmount = 0;
        this.shortAmount = 0;
    }

    public InvoiceItem(int id, String name, Customers customer, Users user, List<CartItem> cartItems, int totalItem, double subTotal, double totalAmount, double paidAmount, double changeAmount, double discount, double discountAmount, double VATPercent, double VATAmount, boolean type, double shortAmount) {
        this.id = id;
        this.name = name;
        this.customer = customer;
        this.user = user;
        this.cartItems = cartItems;

        this.totalItem = totalItem;
        this.subTotal = subTotal;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.changeAmount = changeAmount;
        this.discount = discount;
        this.discountAmount = discountAmount;
        this.VATPercent = VATPercent;
        this.VATAmount = VATAmount;
        this.type = type;
        this.shortAmount = shortAmount;
    }

    public void updateName() {
        if (type) {
            this.name = "Hóa đơn" + this.id;
        } else {
            this.name = "Đặt hàng" + this.id;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getAfterdiscountAmount() {
        return afterdiscountAmount;
    }

    public void setAfterdiscountAmount(double afterdiscountAmount) {
        this.afterdiscountAmount = afterdiscountAmount;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public double getVATPercent() {
        return VATPercent;
    }

    public void setVATPercent(double VATPercent) {
        this.VATPercent = VATPercent;
    }

    public double getVATAmount() {
        return VATAmount;
    }

    public void setVATAmount(double VATAmount) {
        this.VATAmount = VATAmount;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double SubTotal) {
        this.subTotal = SubTotal;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public double getShortAmount() {
        return shortAmount;
    }

    public void setShortAmount(double shortAmount) {
        this.shortAmount = shortAmount;
    }

    public void updateOriginalAmountAndItems() {
        double amount = 0;
        int itemCount = 0;
        for (CartItem item : cartItems) {
            amount += item.getPrice() * item.getQuantity();
            itemCount += item.getQuantity();
        }

        this.subTotal = amount;
        this.totalItem = itemCount;
    }

    public void resetCart() {
        this.totalAmount = 0;
        this.totalItem = 0;
    }

    public double getPointsUsed() {
        return pointsUsed;
    }

    public void setPointsUsed(double pointsUsed) {
        this.pointsUsed = pointsUsed;
    }

    public double getPointsToAdd() {
        return pointsToAdd;
    }

    public void setPointsToAdd(double pointsToAdd) {
        this.pointsToAdd = pointsToAdd;
    }

    
    public void resetShortChangeAmount(){
        this.changeAmount = 0;
        this.shortAmount = 0;
        this.pointsUsed = 0;
        this.pointsToAdd = 0;
    }

}
