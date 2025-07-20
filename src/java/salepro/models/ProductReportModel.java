/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salepro.models;

/**
 *
 * @author tungd
 */
public class ProductReportModel {
    String name,quantity,totalmoney,percentbelowmin,numbergreatermin,numberbelowmin;

    public ProductReportModel() {
    }

    public ProductReportModel(String name, String quantity, String totalmoney, String percentbelowmin, String numbergreatermin, String numberbelowmin) {
        this.name = name;
        this.quantity = quantity;
        this.totalmoney = totalmoney;
        this.percentbelowmin = percentbelowmin;
        this.numbergreatermin = numbergreatermin;
        this.numberbelowmin = numberbelowmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(String totalmoney) {
        this.totalmoney = totalmoney;
    }

    public String getPercentbelowmin() {
        return percentbelowmin;
    }

    public void setPercentbelowmin(String percentbelowmin) {
        this.percentbelowmin = percentbelowmin;
    }

    public String getNumbergreatermin() {
        return numbergreatermin;
    }

    public void setNumbergreatermin(String numbergreatermin) {
        this.numbergreatermin = numbergreatermin;
    }

    public String getNumberbelowmin() {
        return numberbelowmin;
    }

    public void setNumberbelowmin(String numberbelowmin) {
        this.numberbelowmin = numberbelowmin;
    }
    
}
