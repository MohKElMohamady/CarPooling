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


    public Fahrt(double fahrtKosten, String startOrt, String zielOrt, String iconPath) {
        this.fahrtKosten = fahrtKosten;
        this.startOrt = startOrt;
        this.zielOrt = zielOrt;
        this.iconPath = iconPath;
    }


    public Fahrt() {
    }

    public Fahrt(Builder builder){
        this.startOrt = builder.startOrt;
        this.zielOrt = builder.zielOrt;
        this.fahrtKosten = builder.fahrtKosten;
        this.status = builder.status;
        this.beschreibung = builder.beschreibung;

    }


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


    public static class Builder{

        private int maxPlaetze;
        private double fahrtKosten;
        private String startOrt;
        private String zielOrt;
        private String time;
        private String beschreibung;
        private String status;
        private String pfad;

        public Builder(){

        }

        public Builder startOrt(String val){
            startOrt = val;
            return this;
        }


        public Builder zielOrt(String val){
            zielOrt = val;
            return this;
        }


        public Builder time(String val){
            time = val;
            return this;
        }

        public Builder fahrtKosten(double val){
            fahrtKosten = val;
            return this;
        }

        public Builder beschreibung(String val){
            beschreibung = val;
            return this;
        }

        public Builder maxPlaetze(int val){
            maxPlaetze = val;
            return this;
        }

        public Builder status(String val){
            status = val;
            return this;
        }

        public Builder pfad(String val){
            pfad = val;
            return this;
        }

        public Fahrt build(){
            return new Fahrt(this);
        }

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
