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
public class Waiter {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String voterIdNumber;

    public Waiter() {
    }

    public Waiter(int id, String name, String address, String phone, String voterIdNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.voterIdNumber = voterIdNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getVoterIdNumber() {
        return voterIdNumber;
    }
}
