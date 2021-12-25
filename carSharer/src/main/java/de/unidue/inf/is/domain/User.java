package de.unidue.inf.is.domain;

public final class User {

    private String firstName;
    private String lastName;
    private DriverLicense driverLicense;


    public User() {
    }


    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public DriverLicense getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(DriverLicense driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }

}