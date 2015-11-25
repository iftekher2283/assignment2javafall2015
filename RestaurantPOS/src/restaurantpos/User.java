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
public class User {
    private int userId;
    private String userName;
    private String fullName;
    private String password;

    public User() {
    }

    public User(String userName, String fullName, String password) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
    }
   
    public User(int userId, String userName, String fullName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }
    
}
