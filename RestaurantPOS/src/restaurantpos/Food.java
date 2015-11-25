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
public class Food {
    private int itemId;
    private String itemName;
    private double itemPrice;
    private String MealHour;

    public Food() {
    }

    public Food(int itemId, String itemName, double itemPrice, String MealHour) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.MealHour = MealHour;
    }
 
    public Food(int itemId, String itemName, double itemPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public String getMealHour() {
        return MealHour;
    } 
}
