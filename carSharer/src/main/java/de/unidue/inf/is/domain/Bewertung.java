package de.unidue.inf.is.domain;

import java.sql.Date;
import java.sql.Timestamp;

public class Bewertung {

    private int ratingId;
    private Timestamp erstellungsDatum;
    private int rating;
    private String textNachricht;

    // private int schreibenderUserId;
    // private int fahrtId;

    // One to one relationship with the user may not have one


    public Bewertung() {
    }



}
