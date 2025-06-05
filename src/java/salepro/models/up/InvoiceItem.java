/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models.up;

import java.util.List;
import salepro.models.Customers;

/**
 *
 * @author MY PC
 */
public class InvoiceItem {

    private int id;
    private String name;
    private Customers customer;
    private List<CartItem> cartItems;  
    private double totalAmount;
    private int totalItem;

    public InvoiceItem() {
    }

    public InvoiceItem(int id, String name) {
        this.id = id;
        this.name = name;
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

}
