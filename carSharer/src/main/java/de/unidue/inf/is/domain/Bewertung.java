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

    public Bewertung(Builder builder){

        this.rating = builder.rating;
        this.textNachricht = builder.textNachricht;

    }

    public Bewertung(int rating, String textNachricht) {
        this.rating = rating;
        this.textNachricht = textNachricht;
    }

    public double getRatingAsDouble(){
        return (double)this.rating;
    }

    public static class Builder{

        private int rating;
        private String textNachricht;

        public Builder rating(int val){
            rating = val;
            return this;
        }

        public Builder textNachricht(String val){
            textNachricht = val;
            return this;
        }

        public Bewertung build(){
            return new Bewertung(this);
        }


    }

}
