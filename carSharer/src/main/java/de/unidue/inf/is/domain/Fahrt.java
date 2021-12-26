package de.unidue.inf.is.domain;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class Fahrt {

    private int maxPlaetze;
    private String beschreibung;
    private double fahrtKosten;
    private int fahrtId;
    private String startOrt;
    private String zielOrt;
    private String time;
    private String date;
    private String status;
    private int transportMittelId;
    private int anbieter;
    //adding an additional attribute to the fahrt since it needs the transport icon a lot!
    private String iconPath;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String removePfadKeyword(String path){
        path= path.replace("pfad", "");
        System.out.println("#########################################################");
        System.out.println(path);
        System.out.println("#########################################################");
        return path;
    }


    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

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

    public double getFahrtKosten() {
        return fahrtKosten;
    }

    public void setFahrtKosten(double fahrtKosten) {
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
                "maxPlaetze=" + maxPlaetze +
                ", fahrtId=" + fahrtId +
                ", startOrt='" + startOrt + '\'' +
                ", zielOrt='" + zielOrt + '\'' +
                '}';
    }
}
