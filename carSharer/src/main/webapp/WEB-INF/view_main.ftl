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
        <h1>
            Car Sharer <br>
           <h2>Hello ${nameUser}</h2>
        </h1>
        <h3>
            Meine reservierten Fahrten:
        </h3>
        <form method="post" name="view_main_form" action="/hello_servlet">
            <div>
                <#list reservedTrips as rtrip>
                    <div>
                        Start= ${rtrip.getStartOrt()}<br/>
                        Nach= ${rtrip.getZielOrt()}<br/>
                        Status= ${rtrip.getStatus()}<br/>
                    </div>
                    <input type="submit" value="view">
                </#list>
            </div>
        </form>

        <h3>
            Offene Fahrten:
        </h3>
        <form method="post" name="view_main_form" action="/hello_servlet">
            <div>
                <#list openTrips as otrip>
                    <div>
                        Start= ${otrip.getStartOrt()}<br/>
                        Nach= ${otrip.getZielOrt()}<br/>
                        Freie Plätze= ${otrip.getMaxPlaetze()}<br/>
                        Fahrkosten= ${otrip.getFahrtKosten()}<br/>
                    </div>
                    <input type="submit" value="view">
                </#list>
            </div>
        </form>
    </div>


</div>
</body>
</html>
