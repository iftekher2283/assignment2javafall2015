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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author iftekher
 */
public class ManagerPanelUIController implements Initializable {
    @FXML
    private ComboBox<MealHour> mealHourManageItemBox;
    @FXML
    private TextField itemIdManageItemField;
    @FXML
    private TextField itemNameManageItemField;
    @FXML
    private TextField itemPriceManageItemField;
    @FXML
    private TableView<Food> itemDetailsManageItemTable;
    @FXML
    private TableColumn<Food, Number> itemIdManageItemColumn;
    @FXML
    private TableColumn<Food, String> itemNameManageItemColumn;
    @FXML
    private TableColumn<Food, Number> itemPriceManageItemPrice;
    
    private ObservableList<Food> FoodList; 
    
    String DB_URL = "jdbc:mysql://127.0.0.1/restaurantPOSdb";
    String DB_USER = "root";
    String DB_PASS = "123";
    
    @FXML
    private TextField fullNameManageUserField;
    @FXML
    private TextField userNameManageUserField;
    @FXML
    private PasswordField passwordManageUserField;
    @FXML
    private PasswordField repeatPasswordManageUserField;
    @FXML
    private CheckBox isManagerManageUserCheckBox;
    @FXML
    private TableView<User> userListManageUserTable;
    @FXML
    private TableColumn<User, Number> userIdManageUserColumn;
    @FXML
    private TableColumn<User, String> userNameManageUserColumn;
    @FXML
    private TableColumn<User, String> fullNameManageUserColumn;
    
    private ObservableList<User> UserList;
    
    
    @FXML
    private ComboBox<Month> monthStatementBox;
    @FXML
    private ComboBox<?> servedByStatementBox;
    @FXML
    private ComboBox<?> dateStatementBox;
    @FXML
    private TextField totalServiceAmountStatementField;
    @FXML
    private TextField totalOrderAmountStatementField;
    @FXML
    private TableView<Service> serviceStatementTable;
    @FXML
    private TableColumn<Service, Number> serviceIdStatementColumn;
    @FXML
    private TableColumn<Service, String> serviceDateStatementColumn;
    @FXML
    private TableColumn<Service, Number> serviceBillStatementColumn;
    @FXML
    private TableColumn<Service, String> servedByStatementColumn;
    @FXML
    private TableView<Order> orderStatementTable;
    @FXML
    private TableColumn<Order, Number> itemIdStatementColumn;
    @FXML
    private TableColumn<Order, Number> itemQuantityStatementColumn;
    @FXML
    private TableColumn<Order, Number> itemPriceStatementColumn;
    
    private ObservableList<Integer> Dates;
    private ObservableList<String> WaiterList;
    private ObservableList<Service> ServiceList;
    private ObservableList<Order> OrderList;
    @FXML
    private TextField serviceChargeStatementField;
    @FXML
    private TextField vatStatementField;
    @FXML
    private TextField grandTotalStatementField;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mealHourManageItemBox.getItems().addAll(MealHour.values());
        FoodList = FXCollections.observableArrayList();
        
        itemDetailsManageItemTable.setItems(FoodList);
        itemIdManageItemColumn.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getItemId()));
        itemNameManageItemColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getItemName()));
        itemPriceManageItemPrice.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getItemPrice()));
        
        UserList = FXCollections.observableArrayList();
        
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query = "select * from userList";
            ResultSet userList = statement.executeQuery(query);
            
            while(userList.next()){
                User user = new User(userList.getInt("id"), userList.getString("userName"), userList.getString("fullName"), userList.getString("password"));
                UserList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerPanelUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        userListManageUserTable.setItems(UserList);
        userIdManageUserColumn.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getUserId()));
        userNameManageUserColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getUserName()));
        fullNameManageUserColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFullName()));
        
        monthStatementBox.getItems().addAll(Month.values());
        Dates = FXCollections.observableArrayList();
        WaiterList = FXCollections.observableArrayList();
        ServiceList = FXCollections.observableArrayList();
        OrderList = FXCollections.observableArrayList();
        
        try{
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query = "select * from serviceDetails";
            ResultSet serviceDetails = statement.executeQuery(query);
            
            double totalServiceAmount = 0.0;
            
            while(serviceDetails.next()){
                Service service = new Service(serviceDetails.getInt("serviceId"), serviceDetails.getString("date"), serviceDetails.getDouble("totalBill"), serviceDetails.getString("servedBy"));
                ServiceList.add(service);
                totalServiceAmount = totalServiceAmount + service.getTotalBill();
            }
            
            totalServiceAmountStatementField.setText(totalServiceAmount + "");
        } catch (SQLException ex) {
            Logger.getLogger(ManagerPanelUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        serviceStatementTable.setItems(ServiceList);
        serviceIdStatementColumn.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getServiceId()));
        serviceDateStatementColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDate()));
        serviceBillStatementColumn.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getTotalBill()));
        servedByStatementColumn.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getServedBy()));
    }    

    @FXML
    private void handleLogOutManageItemAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
            
            Scene scene = new Scene(root);
            
            RestaurantPOS.getMainStage().setScene(scene);
            RestaurantPOS.getMainStage().show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleAddFoodItemAction(ActionEvent event) {
        String mealHour = mealHourManageItemBox.getSelectionModel().getSelectedItem() + "";
        int itemId = Integer.parseInt(itemIdManageItemField.getText());
        String itemName = itemNameManageItemField.getText();
        double itemPrice = Double.parseDouble(itemPriceManageItemField.getText());
        
        Food food = new Food(itemId, itemName, itemPrice, mealHour);
        
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query = "insert into foodItems values(" + food.getItemId() + ", '" + food.getItemName() + "', " + food.getItemPrice() + ", '" + food.getMealHour() + "');";
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ManagerPanelUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        itemIdManageItemField.setText("");
        itemNameManageItemField.setText("");
        itemPriceManageItemField.setText("");
        FoodList.add(food);
    }

    @FXML
    private void handleUpdateFoodItemAction(ActionEvent event) {
        Food food = itemDetailsManageItemTable.getSelectionModel().getSelectedItem();
        
        FoodList.remove(food);
        
        int itemId = Integer.parseInt(itemIdManageItemField.getText());
        String itemName = itemNameManageItemField.getText();
        Double itemPrice = Double.parseDouble(itemPriceManageItemField.getText());
        String mealHour = mealHourManageItemBox.getSelectionModel().getSelectedItem() + "";
        
        Food foodUpdt = new Food(itemId, itemName, itemPrice, mealHour);
        FoodList.add(foodUpdt);
        
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query = "update foodItems set itemName='" + foodUpdt.getItemName() + "', price=" + foodUpdt.getItemPrice() + "where itemId=" + food.getItemId() + ";";
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ManagerPanelUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        itemIdManageItemField.setText("");
        itemNameManageItemField.setText("");
        itemPriceManageItemField.setText("");
    }

    @FXML
    private void handleRemoveFoodItemAction(ActionEvent event) {
        Food food = itemDetailsManageItemTable.getSelectionModel().getSelectedItem();
        
        FoodList.remove(food);
        
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query = "delete from foodItems where itemId=" + food.getItemId() + ";";
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ManagerPanelUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        itemIdManageItemField.setText("");
        itemNameManageItemField.setText("");
        itemPriceManageItemField.setText("");
        
    }

    @FXML
    private void handleNewFoodItemAction(ActionEvent event) {
        mealHourManageItemBox.getSelectionModel().clearSelection();
        itemIdManageItemField.setText("");
        itemNameManageItemField.setText("");
        itemPriceManageItemField.setText("");
    }

    @FXML
    private void handleMealHourMangeItemAction(ActionEvent event) {
        FoodList.remove(0, FoodList.size());
        String mealHour = mealHourManageItemBox.getSelectionModel().getSelectedItem() + "";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query = "select * from foodItems;";
            ResultSet foodItems = statement.executeQuery(query);
            
            while(foodItems.next()){
                if(foodItems.getString("mealHour").equalsIgnoreCase(mealHour)){
                    Food food = new Food(foodItems.getInt("itemId"), foodItems.getString("itemName"), foodItems.getDouble("price"), foodItems.getString("mealHour"));
                    FoodList.add(food);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerPanelUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleSelectFoodManageItemAction(MouseEvent event) {
        Food food = itemDetailsManageItemTable.getSelectionModel().getSelectedItem();
        itemIdManageItemField.setText(food.getItemId() + "");
        itemNameManageItemField.setText(food.getItemName());
        itemPriceManageItemField.setText(food.getItemPrice() + "");
    }

    @FXML
    private void handleAddUserManageUserAction(ActionEvent event) {
        String fullName = fullNameManageUserField.getText();
        String userName = userNameManageUserField.getText();
        String password = passwordManageUserField.getText();
        String rePassword = repeatPasswordManageUserField.getText();
        
        User user = new User(userName, fullName, password);
        
        if(password.equalsIgnoreCase(rePassword)){
            try {
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                Statement statement = connection.createStatement();
                
                String query;
                if(isManagerManageUserCheckBox.isSelected()){
                    query = "insert into userList values(null,'" + user.getUserName() + "', '" + user.getPassword() + "', '" + user.getFullName() + "'," + 1 + ");";
                }
                else{
                    query = "insert into userList values(null,'" + user.getUserName() + "', '" + user.getPassword() + "', '" + user.getFullName() + "'," + 0 + ");";
                }
                statement.executeUpdate(query);
                
                String query2 = "select * from userList;";
                ResultSet newUser = statement.executeQuery(query2);
                
                while(newUser.next()){
                    if(newUser.getString("userName").equalsIgnoreCase(user.getUserName())){
                        User addedUser = new User(newUser.getInt("id"), newUser.getString("userName"), newUser.getString("fullName"), newUser.getString("password"));
                        UserList.add(addedUser);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManagerPanelUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        fullNameManageUserField.setText("");
        userNameManageUserField.setText("");
        passwordManageUserField.setText("");
        repeatPasswordManageUserField.setText("");
    }

    @FXML
    private void handleUpdateUserManageUserAction(ActionEvent event) {
        User user = userListManageUserTable.getSelectionModel().getSelectedItem();
        
        String fullName = fullNameManageUserField.getText();
        String userName = userNameManageUserField.getText();
        String password = passwordManageUserField.getText();
        String rePassword = repeatPasswordManageUserField.getText();
        
        if(password.equalsIgnoreCase(rePassword)){
            UserList.remove(user);
            user = new User(user.getUserId(), userName, fullName, password);
            
            try {
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                Statement statement = connection.createStatement();
                
                String query = "update userList set userName='" + user.getUserName() + "', fullName='" + user.getFullName() + "', password='" + user.getPassword() + "' where id=" + user.getUserId() + ";";
                statement.executeUpdate(query);
                
                UserList.add(user);
            } catch (SQLException ex) {
                Logger.getLogger(ManagerPanelUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        fullNameManageUserField.setText("");
        userNameManageUserField.setText("");
        passwordManageUserField.setText("");
        repeatPasswordManageUserField.setText("");
        
    }

    @FXML
    private void handleRemoveUserManageUserAction(ActionEvent event) {
        User user = userListManageUserTable.getSelectionModel().getSelectedItem();
        UserList.remove(user);
        
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query = "delete from userList where id = " + user.getUserId() + ";";
            statement.executeUpdate(query);
                        
        } catch (SQLException ex) {
            Logger.getLogger(ManagerPanelUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        fullNameManageUserField.setText("");
        userNameManageUserField.setText("");
    }

    @FXML
    private void handleNewUserManageUserAction(ActionEvent event) {
        fullNameManageUserField.setText("");
        userNameManageUserField.setText("");
        passwordManageUserField.setText("");
        repeatPasswordManageUserField.setText("");
    }

    @FXML
    private void handleSelectUserManageUserAction(MouseEvent event) {
        User user = userListManageUserTable.getSelectionModel().getSelectedItem();
        fullNameManageUserField.setText(user.getFullName());
        userNameManageUserField.setText(user.getUserName());
    }

    @FXML
    private void handleMonthStatementAction(ActionEvent event) {
    }

    @FXML
    private void handleServedByStatementAction(ActionEvent event) {
    }

    @FXML
    private void handleDateStatementAction(ActionEvent event) {
    }

    @FXML
    private void handleSelectServiceAction(MouseEvent event) {
        OrderList.remove(0, OrderList.size());
        Service service = serviceStatementTable.getSelectionModel().getSelectedItem();
        double totalOrderAmount = 0.0;
        
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query = "select * from orderDetails";
            ResultSet orders = statement.executeQuery(query);
            
            while(orders.next()){
                if(orders.getInt("serviceId") == service.getServiceId()){
                    Order order = new Order(orders.getInt("serviceId"), orders.getInt("itemId"), orders.getInt("quantity"), orders.getDouble("orderPrice"));
                    OrderList.add(order);
                    totalOrderAmount = totalOrderAmount + orders.getDouble("orderPrice");
                }
            }
            orderStatementTable.setItems(OrderList);
            itemIdStatementColumn.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getItemIdFromOrders()));
            itemQuantityStatementColumn.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getItemQuantity()));
            itemPriceStatementColumn.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getOrderPriceFromOrders()));

            totalOrderAmountStatementField.setText(totalOrderAmount + "");
            
            double vat = (totalOrderAmount * 15) / 100;
            double serviceCharge = (totalOrderAmount * 10) / 100;
            double grandTotal = totalOrderAmount + vat + serviceCharge;
            
            vatStatementField.setText(vat + "");
            serviceChargeStatementField.setText(serviceCharge + "");
            grandTotalStatementField.setText(grandTotal + "");
        } catch (SQLException ex) {
            Logger.getLogger(ManagerPanelUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
