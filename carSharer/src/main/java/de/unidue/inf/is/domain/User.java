package de.unidue.inf.is.domain;

public final class User {

    private int userId;
    private String Name;
    private String email;
    private Fahrerlaubnis fahrerlaubnis;


    public User() {
    }

    public User(String firstName, String email) {
        this.Name = firstName;
        this.email= email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String firstName) {
        this.Name = firstName;
    }




}