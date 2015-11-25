/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurantpos;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


/**
 *
 * @author iftekher
 */
public class FXMLDocumentController implements Initializable {
    
 
    @FXML
    private Pane paneLoginPanel;
    @FXML
    private TextField serviceIdField;
    @FXML
    private TextField itemQuantityField;
    @FXML
    private ComboBox<Integer> tableNoBox;
    @FXML
    private DatePicker serviceDateBox;
    @FXML
    private ComboBox<String> itemNameBox;
    @FXML
    private ComboBox<String> servedByBox;
    @FXML
    private TableView<Order> orderDetailsTable;
    @FXML
    private TableColumn<Order, Number> itemIdColumn;
    @FXML
    private TableColumn<Order, String> itemNameColumn;
    @FXML
    private TableColumn<Order, Number> itemQuantityColumn;
    @FXML
    private TableColumn<Order, Number> itemPriceColumn;;
    @FXML
    private TextField totalPriceField;
    @FXML
    private TextField vatField;
    @FXML
    private TextField serviceChargeField;
    @FXML
    private TextField grandTotalField;
    @FXML
    private TextField paidAmountField;
    @FXML
    private TextField returnAmountField;
    @FXML
    private ComboBox<MealHour> mealHourBox;
    @FXML
    private TableView<Food> foodMenuTable;
    @FXML
    private TableColumn<Food, Number> itemIdColumnMenu;
    @FXML
    private TableColumn<Food, String> itemNameColumnMenu;
    @FXML
    private TableColumn<Food, Number> unitPriceColumnMenu;
    @FXML
    private ComboBox<MealHour> mealHourBoxMenu;
    
    private ObservableList <Integer> TableNo;
    private ObservableList <String> WaiterList;
    private ObservableList <String> FoodNames;
    private ObservableList <Order> Orders;
    private ObservableList <Food> FoodMenu;
   
    String DB_URL = "jdbc:mysql://127.0.0.1/restaurantPOSdb";
    String DB_USER = "root";
    String DB_PASS = "123";
    
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mealHourBox.getItems().addAll(MealHour.values());
        mealHourBoxMenu.getItems().addAll(MealHour.values());
        TableNo = FXCollections.observableArrayList();
        WaiterList = FXCollections.observableArrayList();
        Orders = FXCollections.observableArrayList();
        FoodMenu = FXCollections.observableArrayList();
        for(int i = 0; i < 10; i++){
            TableNo.add(i, i+1);
        }
        tableNoBox.setItems(TableNo);
        
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query1 = "select * from waiters;";
            
            ResultSet waiterInfo = statement.executeQuery(query1);
            for(int i = 0; ; i++) {
                if(!waiterInfo.next()){
                    break;
                }
                String waiterName = waiterInfo.getString("name");
                WaiterList.add(i, waiterName);
            }
            servedByBox.setItems(WaiterList);
            
            String query2 = "select * from serviceDetails;";
            ResultSet services = statement.executeQuery(query2);
            int serviceId = 1;
            while(services.next()){
                serviceId = services.getInt("serviceId") + 1;
            }
            serviceIdField.setText(serviceId + "");
                    // TODO
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        orderDetailsTable.setItems(Orders);
        itemIdColumn.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getItemId()));
        itemNameColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getItemName()));
        itemQuantityColumn.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getItemQuantity()));
        itemPriceColumn.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getOrderPrice()));
        
        foodMenuTable.setItems(FoodMenu);
        itemIdColumnMenu.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getItemId()));
        itemNameColumnMenu.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getItemName()));
        unitPriceColumnMenu.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getItemPrice()));
    }    

    @FXML
    private void handleAddOrderAction(ActionEvent event) {
        
        int serviceId = Integer.parseInt(serviceIdField.getText());
        int tableNo = Integer.parseInt(tableNoBox.getSelectionModel().getSelectedItem() + "");
        String date = serviceDateBox.getValue() + "";
        String servedBy = servedByBox.getSelectionModel().getSelectedItem();
        String mealHour = mealHourBox.getSelectionModel().getSelectedItem() + "";
        String itemName = itemNameBox.getSelectionModel().getSelectedItem();
        int quantity = Integer.parseInt(itemQuantityField.getText());
        
        Order order = new Order(serviceId, tableNo, date, servedBy, mealHour, itemName, quantity);
        Orders.add(order);
       // Orders.remove(0, Orders.size());
        
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query1 = "insert into orderDetails values(" + order.getServiceId() + "," + order.getItemId() + "," + order.getItemQuantity() + "," + order.getOrderPrice() + ");";
            statement.executeUpdate(query1);
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        double totalPrice = 0.0;
        for(int i = 0; i < Orders.size(); i++){
            Order orders = Orders.get(i);
            totalPrice = totalPrice + orders.getOrderPrice();
        }
        totalPriceField.setText(totalPrice + "");
        double vat = (totalPrice * 15) / 100;
        vatField.setText(vat + "");
        double serviceCharge = (totalPrice * 10) / 100;
        serviceChargeField.setText(serviceCharge + "");
        double grandTotal = totalPrice + vat + serviceCharge;
        grandTotalField.setText(grandTotal + "");
        
        itemNameBox.getSelectionModel().clearSelection();
        itemQuantityField.setText("");
    }


    @FXML
    private void handleMealHourAction(ActionEvent event) {
        FoodNames = FXCollections.observableArrayList();
        String orderMealHour = mealHourBox.getSelectionModel().getSelectedItem() + "";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query = "select * from foodItems;";
            ResultSet foodNames = statement.executeQuery(query);
            
            while(foodNames.next()){
                if(orderMealHour.equalsIgnoreCase("Dinner")){
                    String foodMealHour = foodNames.getString("mealHour");
                    if(foodMealHour.equalsIgnoreCase("Lunch") || foodMealHour.equalsIgnoreCase("Dinner")){
                        String foodName = foodNames.getString("itemName");
                        FoodNames.add(foodName);
                    }
                }
                else{
                    String foodMealHour = foodNames.getString("mealHour");
                    if(foodMealHour.equalsIgnoreCase(orderMealHour)){
                        String foodName = foodNames.getString("itemName");
                        FoodNames.add(foodName);
                    }
                }
            }
            itemNameBox.setItems(FoodNames);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleNewServiceAction(ActionEvent event) {
        int serviceId = Integer.parseInt(serviceIdField.getText());
        String date = serviceDateBox.getValue() + "";
        double totalBill = Double.parseDouble(grandTotalField.getText());
        double givenAmount = Double.parseDouble(paidAmountField.getText());
        double returnAmount = Double.parseDouble(returnAmountField.getText());
        int tableNo = Integer.parseInt(tableNoBox.getSelectionModel().getSelectedItem() + "");
        String servedBy = servedByBox.getSelectionModel().getSelectedItem();
        
        Service service = new Service(serviceId, date, totalBill, givenAmount, returnAmount, tableNo, servedBy);
        
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query = "insert into serviceDetails values(" + service.getServiceId() + ", '" + service.getDate() + "', " + service.getTotalBill() + "," + service.getGivenAmount() + "," + service.getReturnAmount() + "," + service.getTableNo() + ",'" + service.getServedBy() +"');";
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int newServiceId = service.getServiceId() + 1;
        serviceIdField.setText(newServiceId + "");
        tableNoBox.getSelectionModel().clearSelection();
        servedByBox.getSelectionModel().clearSelection();
        mealHourBox.getSelectionModel().clearSelection();
        totalPriceField.setText("");
        vatField.setText("");
        serviceChargeField.setText("");
        grandTotalField.setText("");
        paidAmountField.setText("");
        returnAmountField.setText("");
        
        Orders.remove(0, Orders.size());
    }

    @FXML
    private void handleRemoveOrderAction(ActionEvent event) {
        Order order = orderDetailsTable.getSelectionModel().getSelectedItem();
       
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query = "delete from orderDetails where serviceId=" + order.getServiceId() + "&& itemId=" + order.getItemId() + ";";
            statement.executeUpdate(query);
            Orders.remove(order);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        double totalPrice = 0.0;
        for(int i = 0; i < Orders.size(); i++){
            Order orders = Orders.get(i);
            totalPrice = totalPrice + orders.getOrderPrice();
        }
        totalPriceField.setText(totalPrice + "");
        double vat = (totalPrice * 15) / 100;
        vatField.setText(vat + "");
        double serviceCharge = (totalPrice * 10) / 100;
        serviceChargeField.setText(serviceCharge + "");
        double grandTotal = totalPrice + vat + serviceCharge;
        grandTotalField.setText(grandTotal + "");
    }

    @FXML
    private void handleGivenAmountAction(ActionEvent event) {
        double givenAmount = Double.parseDouble(paidAmountField.getText());
        double totalBill = Double.parseDouble(grandTotalField.getText());
        double returnAmount = givenAmount - totalBill;
        returnAmountField.setText(returnAmount + "");
    }

    @FXML
    private void handleMealHourActionMenu(ActionEvent event) {
        FoodMenu.remove(0, FoodMenu.size());
        String mealHour = mealHourBoxMenu.getSelectionModel().getSelectedItem() + "";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query = "select * from foodItems;";
            ResultSet foodItems = statement.executeQuery(query);
            
            while(foodItems.next()){
                if(mealHour.equalsIgnoreCase("Dinner")){
                    if (foodItems.getString("mealHour").equalsIgnoreCase("Lunch") || foodItems.getString("mealHour").equalsIgnoreCase("Dinner")){
                        Food food = new Food(foodItems.getInt("itemId"), foodItems.getString("itemName"), foodItems.getDouble("price"));
                        FoodMenu.add(food);
                    }
                }
                else{
                    if (foodItems.getString("mealHour").equalsIgnoreCase(mealHour)){
                        Food food = new Food(foodItems.getInt("itemId"), foodItems.getString("itemName"), foodItems.getDouble("price"));
                        FoodMenu.add(food);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleLogOutAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
            
            Scene scene = new Scene(root);
            
            RestaurantPOS.getMainStage().setScene(scene);
            RestaurantPOS.getMainStage().show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
