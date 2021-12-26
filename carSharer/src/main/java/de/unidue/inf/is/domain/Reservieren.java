package de.unidue.inf.is.domain;

public class Reservieren {

    private int reservierenderUserId;
    private int fahrtId;
    private int anzahlPlaetze;

    public int getReservierenderUserId() {
        return reservierenderUserId;
    }

    public void setReservierenderUserId(int reservierenderUserId) {
        this.reservierenderUserId = reservierenderUserId;
    }

    public int getFahrtId() {
        return fahrtId;
    }

    public void setFahrtId(int fahrtId) {
        this.fahrtId = fahrtId;
    }

    public int getAnzahlPlaetze() {
        return anzahlPlaetze;
    }

    public void setAnzahlPlaetze(int anzahlPlaetze) {
        this.anzahlPlaetze = anzahlPlaetze;
    }

    @Override
    public String toString() {
        return "Reservieren{" +
                "reservierenderUserId=" + reservierenderUserId +
                ", fahrtId=" + fahrtId +
                ", anzahlPlaetze=" + anzahlPlaetze +
                '}';
    }

}
