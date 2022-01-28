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
        <h1> Offene Fahrten des "besten Fahrers" </h1>
    </div>

    <div id="site">
        Fahrer: ${Email}
        <br/>
        Durchschnittsrating: ${AvgRating}
    </div>

    <div>
        <#list ListOfOpenTrips as OpenTrip>
            <input type="image" src=${OpenTrip.getIconPath()} alt="picture"  class="icon"/><br/>
            Fahrt-ID: ${OpenTrip.getFahrtId()}
            <br>
            Von: ${OpenTrip.getStartOrt()}
            <br>
            Nach: ${OpenTrip.getZielOrt()}
            <br>
            Rating: ${OpenTrip.getRating()}
            <br>


        </#list>

        <br>
        <br>
        <form method="get" action="/returnToMainPage">
            <input type="submit" value="return to main page" />
        </form>
    </div>
    <br/>
</div>
</body>
</html>
