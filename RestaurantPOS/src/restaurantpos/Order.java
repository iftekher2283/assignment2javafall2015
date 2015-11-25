/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantpos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iftekher
 */
public class Order {
    private int serviceId;
    private int tableNo;
    private String date;
    private String servedBy;
    private String mealHour;
    private String itemName;
    private int itemQuantity;
    private int itemId;
    private double price;

    public Order() {
    }

    public Order(int serviceId, int tableNo, String date, String servedBy, String mealHour, String itemName, int itemQuantity) {
        this.serviceId = serviceId;
        this.tableNo = tableNo;
        this.date = date;
        this.servedBy = servedBy;
        this.mealHour = mealHour;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public int getServiceId() {
        return serviceId;
    }

    public int getTableNo() {
        return tableNo;
    }

    public String getDate() {
        return date;
    }

    public String getServedBy() {
        return servedBy;
    }

    public String getMealHour() {
        return mealHour;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }
    
    public int getItemIdFromOrders(){
        return itemId;
    }
    
    public int getItemId(){
        itemId = 0;
        String DB_URL ="jdbc:mysql://127.0.0.1/restaurantPOSdb";
        String DB_USER = "root";
        String DB_PASS = "123";
        
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query = "select * from foodItems;";
            ResultSet foodItems = statement.executeQuery(query);
            
            while(foodItems.next()){
                if(itemName.equalsIgnoreCase(foodItems.getString("itemName"))){
                    itemId = foodItems.getInt("itemId");
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemId;
    }
    
    public double getOrderPriceFromOrders(){
        return price;
    }
    
    public double getOrderPrice(){
        double orderPrice = 0.0;
        String DB_URL ="jdbc:mysql://127.0.0.1/restaurantPOSdb";
        String DB_USER = "root";
        String DB_PASS = "123";
        
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query = "select * from foodItems;";
            ResultSet foodItems = statement.executeQuery(query);
            
            while(foodItems.next()){
                if(itemName.equalsIgnoreCase(foodItems.getString("itemName"))){
                    orderPrice = (double)this.getItemQuantity() * foodItems.getDouble("price");
                    break;
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderPrice;
    }
    
    public Order(int serviceId, int itemId, int itemQuantity, double price) {
        this.serviceId = serviceId;
        this.itemId = itemId;
        this.itemQuantity =itemQuantity;
        this.price = price;
    }
}
