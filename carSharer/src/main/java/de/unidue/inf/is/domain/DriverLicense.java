package de.unidue.inf.is.domain;

import java.sql.Date;

public class DriverLicense {

    private int Number;
    private Date expirationDate;
    private User user;

    public DriverLicense(int number, User user) {
        Number = number;
        this.user = user;
    }
}
