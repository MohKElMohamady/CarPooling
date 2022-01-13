package de.unidue.inf.is.domain;

public class EmailBeschreibungRating {

    private String email;
    private String beschreibung;
    private int rating;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "EmailBeschreibungRating{" +
                "email='" + email + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", rating=" + rating +
                '}';
    }
}
