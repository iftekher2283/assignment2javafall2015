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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author iftekher
 */
public class LoginPanelController implements Initializable {
    @FXML
    private CheckBox isManagerCheckBox;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label loginMessageLabel;
    
    String DB_URL = "jdbc:mysql://127.0.0.1/restaurantPOSdb";
    String DB_USER = "root";
    String DB_PASS = "123";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleLogInAction(ActionEvent event) {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        
        int userCheck = 0;
        
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            
            String query = "select * from userList;";
            ResultSet userList = statement.executeQuery(query);
            
            while(userList.next()){
                if(isManagerCheckBox.isSelected()){
                    if(userName.equalsIgnoreCase(userList.getString("userName")) && password.equalsIgnoreCase(userList.getString("password")) && userList.getInt("isManager") == 1){
                        userCheck = 1;
                    }
                }
                else{
                    if(userName.equalsIgnoreCase(userList.getString("userName")) && password.equalsIgnoreCase(userList.getString("password"))){
                        userCheck = 2;
                    }
                }
            }
            
            if(userCheck==1){
                Parent root = FXMLLoader.load(getClass().getResource("ManagerPanelUI.fxml"));

                Scene scene = new Scene(root);

                RestaurantPOS.getMainStage().setScene(scene);
                RestaurantPOS.getMainStage().show();
            }
            else if(userCheck == 2){
                Parent root = FXMLLoader.load(getClass().getResource("OrderAndMenuUI.fxml"));

                Scene scene = new Scene(root);

                RestaurantPOS.getMainStage().setScene(scene);
                RestaurantPOS.getMainStage().show();
            }
            else{
                loginMessageLabel.setText("Login Failed! Please Check Username and Password");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginPanelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
