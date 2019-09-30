package com.cz2006.helloworld.models;

/**
 * Represents User using the application
 *
 * @author Rosario Gelli Ann
 *
 */
public class User {

    private int userID;
    private String userName;
    private String userEmail;
    private String userPassword;
    private Points points;

    public User(){

    }

    public User(int userID, String userName, String userEmail, String userPassword, Points points) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.points = points;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Points getPoints() {
        return points;
    }

    public void setPoints(Points points) {
        this.points = points;
    }
}
