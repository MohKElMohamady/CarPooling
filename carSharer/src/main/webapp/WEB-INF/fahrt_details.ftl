<html>
<head><title>CarSharer</title>
    <style type="text/css">
        * {
            margin:0;
            padding:0;
        }

        body{
            text-align:center;
            background: #efe4bf none repeat scroll 0 0;
        }

        #wrapper{
            width:960px;
            margin:0 auto;
            text-align:left;
            background-color: #fff;
            border-radius: 0 0 10px 10px;
            padding: 20px;
            box-shadow: 1px -2px 14px rgba(0, 0, 0, 0.4);
        }

        #header{
            color: #fff;
            background-color: #2c5b9c;
            height: 3.5em;
            padding: 1em 0em 1em 1em;

        }

        #site{
            background-color: #fff;
            padding: 20px 0px 0px 0px;
        }
        .centerBlock{
            margin:0 auto;
        }
    </style>

<body>
<div id="wrapper">
    <div id="header">
        <h1> CarSharer Website </h1>
    </div>

    <div id="site">
        <p>
        <h3>
            Informationen
        </h3>

        <div>
            <#list trip as trip>
                <form method="post" name="view_main_form" action="/fahrt_details_servlet?fid=${trip.getFahrtId()}">
                    <div>
                        <img src=${trip.getIconPath()} alt="picture" class="icon"/><br/>

                        Anbieter= ${email} <br/>
                        Fahrtdatum uhr -uhrzeit= ${trip.getDate()}        ${trip.getTime()}  <br/>
                        Von= ${trip.getStartOrt()}<br/>
                        Nach= ${trip.getZielOrt()}<br/>
                        Anzahl freier Plätze= ${trip.getMaxPlaetze()}    <br/>
                        Preis = $${trip.getFahrtKosten()}  <br/>
                        Status= ${trip.getStatus()}<br/>
                        <#if trip.getBeschreibung()??>
                            Beschreibung= ${trip.getBeschreibung()}<br/>
                        </#if>
                    </div>

                </form>
            </#list>
            <br/>
            <br/>
            <br/>
            <hr/>
            <div>
                <h3>
                    Aktionsleiste
                </h3>
                <#list trip as trip>
                <form method="post" action="/reserve_servlet?fid=${trip.getFahrtId()}">
                    Anzahl Plaetze fuer Reservierung: <input type="number" value="Anz" name="anzahlPlaetze" min="1" max="2" />
                    <br>
                    <input type="submit" value="Fahrt reservieren" name="reserve button" />
                </form>
                </#list>

                <#list trip as trip>
                    <form method="post" action="/delete_servlet?fid=${trip.getFahrtId()}">
                        <input type="submit" value="Fahrt löschen" name="delete button" />
                    </form>
                </#list>

            </div>

            <br/>
            <br/>
            <br/>
            <hr/>
            <div>
                <h3>
                    Bewertung<br>
                    Durchschnittsrating: ${avgRating}

                </h3>
                <#list emailsAndTheirRatings as mbr>

                    <div>   Email: ${mbr.getEmail()}<br>
                            Beschreibung: ${mbr.getBeschreibung()}<br>
                            Rating: ${mbr.getRating()}<br>
                            <hr>
                    </div>

                </#list>

                <#list trip as trip>
                <form method="post" action="/new_rating?fid=${trip.getFahrtId()}">
                    <input type="submit" value="Fahrt bewerten" name="Fahrt_bewerten" />
                </form>
                </#list>
                <br>
                <br>

                <form method="get" action="/returnToMainPage">
                    <input type="submit" value="return to main page" />
                </form>

<#--                    <form method="post" action="/delete_servlet?fid=${trip.getFahrtId()}">-->
<#--                        <input type="submit" value="Fahrt löschen" name="delete button" />-->
<#--                    </form>-->
            </div>

        </div>

        </p>
    </div>
</div>
</body>
</html>
