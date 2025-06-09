/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models.up;

import java.util.List;
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
    private double payableAmount;
    private double paidAmount;
    private double changeAmount;
    private double discount;
    private boolean type;

    public InvoiceItem() {
    }

    public InvoiceItem(int id, String name) {
        this.id = id;
        this.name = name;
        this.customer = new Customers();
        this.user = new Users();
        this.type = true;
    }

    public InvoiceItem(int id, String name, Customers customer, List<CartItem> cartItems, double totalAmount, int totalItem, double payableAmount, double paidAmount, double changeAmount, double discount) {
        this.id = id;
        this.name = name;
        this.customer = customer;
        this.cartItems = cartItems;
        this.totalAmount = totalAmount;
        this.totalItem = totalItem;
        this.payableAmount = payableAmount;
        this.paidAmount = paidAmount;
        this.changeAmount = changeAmount;
        this.discount = discount;
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

    public double getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(double payableAmount) {
        this.payableAmount = payableAmount;
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void updateTotalAmountAndItems() {
        double amount = 0;
        int itemCount = 0;
        for (CartItem item : cartItems) {
            amount += item.getPrice() * item.getQuantity();
            itemCount += item.getQuantity();
        }
        this.totalAmount = amount;
        this.totalItem = itemCount;
    }

    public void resetCart() {
        this.totalAmount = 0;
        this.totalItem = 0;
    }

}
