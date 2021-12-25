package de.unidue.inf.is.domain;

import java.sql.Date;

public class Rating {

    private int ratingId;
    private String textMessage;
    private Date creationDate;
    private int ratingNumber;

    // One to one relationship with the user may not have one


    public Rating(int ratingId) {
        this.ratingId = ratingId;
    }

    public Rating(int ratingId, String textMessage, Date creationDate, int ratingNumber) {
        this.ratingId = ratingId;
        this.textMessage = textMessage;
        this.creationDate = creationDate;
        this.ratingNumber = ratingNumber;
    }


    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getRatingNumber() {
        return ratingNumber;
    }

    public void setRatingNumber(int ratingNumber) {
        this.ratingNumber = ratingNumber;
    }
}
