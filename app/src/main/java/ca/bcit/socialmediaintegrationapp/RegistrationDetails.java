package ca.bcit.socialmediaintegrationapp;

public class RegistrationDetails {
    String userID, name, email, phoneNumber;

    public RegistrationDetails() {
    }

    public RegistrationDetails(String userID, String name, String email, String phoneNumber) {
        this.userID = userID;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
