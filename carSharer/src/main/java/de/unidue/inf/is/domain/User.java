package de.unidue.inf.is.domain;

public final class User {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private Fahrerlaubnis fahrerlaubnis;


    public User() {
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}