package de.unidue.inf.is.domain;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class Fahrt {

    private int maxPlaetze;
    private String beschreibung;
    private int fahrtKosten;
    private int fahrtId;
    private String startOrt;
    private String zielOrt;
    private Timestamp fahrtDatumZeit;
    private String status;
    private int transportMittelId;
    private int anbieter;

    public int getMaxPlaetze() {
        return maxPlaetze;
    }

    public void setMaxPlaetze(int maxPlaetze) {
        this.maxPlaetze = maxPlaetze;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public int getFahrtKosten() {
        return fahrtKosten;
    }

    public void setFahrtKosten(int fahrtKosten) {
        this.fahrtKosten = fahrtKosten;
    }

    public int getFahrtId() {
        return fahrtId;
    }

    public void setFahrtId(int fahrtId) {
        this.fahrtId = fahrtId;
    }

    public String getStartOrt() {
        return startOrt;
    }

    public void setStartOrt(String startOrt) {
        this.startOrt = startOrt;
    }

    public String getZielOrt() {
        return zielOrt;
    }

    public void setZielOrt(String zielOrt) {
        this.zielOrt = zielOrt;
    }

    public Timestamp getFahrtDatumZeit() {
        return fahrtDatumZeit;
    }

    public void setFahrtDatumZeit(Timestamp fahrtDatumZeit) {
        this.fahrtDatumZeit = fahrtDatumZeit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTransportMittelId() {
        return transportMittelId;
    }

    public void setTransportMittelId(int transportMittelId) {
        this.transportMittelId = transportMittelId;
    }

    public int getAnbieter() {
        return anbieter;
    }

    public void setAnbieter(int anbieter) {
        this.anbieter = anbieter;
    }


    @Override
    public String toString() {
        return "Fahrt{" +
                "startOrt='" + startOrt + '\'' +
                ", zielOrt='" + zielOrt + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
