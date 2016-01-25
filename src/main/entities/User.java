package main.entities;

import main.dao.Identified;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class to represent user.
 */
public class User implements Identified<Integer>{
    /**
     * Identifier to distinguish user, primary key in database.
     */
    private Integer id;
    /**
     * First name of user.
     */
    private String first_name;
    /**
     * Second name of user.
     */
    private String second_name;
    /**
     * User's post address.
     */
    private String address;
    /**
     * User's email.
     */
    private String email;
    /**
     * User's password.
     */
    private String password;
    /**
     * Administrator or not. Default value is false.
     */
    private boolean admin = false;

    /**
     * List of user's subscriptions.
     */
    private List<Subscription> subscriptions = new ArrayList<>();

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void addSubscription(Subscription subscription){
        if (!subscriptions.contains(subscription))
        subscriptions.add(subscription);
    }

}
