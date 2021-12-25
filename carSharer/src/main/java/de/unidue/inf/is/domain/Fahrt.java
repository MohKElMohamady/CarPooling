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
    private Boolean status;

    private int transportMittelId;

    private int anbieter;


}
