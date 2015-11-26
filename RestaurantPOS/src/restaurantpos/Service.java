/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantpos;

/**
 *
 * @author iftekher
 */
public class Service {
    private int serviceId;
    private String date;
    private double totalBill;
    private double discountAmount;
    private double givenAmount;
    private double returnAmount;
    private int tableNo;
    private String servedBy;

    public Service() {
    }

    public Service(int serviceId, String date, double totalBill, double discountAmount, double givenAmount, double returnAmount, int tableNo, String servedBy) {
        this.serviceId = serviceId;
        this.date = date;
        this.totalBill = totalBill;
        this.discountAmount = discountAmount;
        this.givenAmount = givenAmount;
        this.returnAmount = returnAmount;
        this.tableNo = tableNo;
        this.servedBy = servedBy;
    }

    public Service(int serviceId, String date, double totalBill, double discountAmount, String servedBy) {
        this.serviceId = serviceId;
        this.date = date;
        this.totalBill = totalBill;
        this.discountAmount = discountAmount;
        this.servedBy = servedBy;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getDate() {
        return date;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getGivenAmount() {
        return givenAmount;
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    public int getTableNo() {
        return tableNo;
    }

    public String getServedBy() {
        return servedBy;
    }
    
    
}
