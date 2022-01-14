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
           Enter the following details!
        </p>

        <form name="searchFahrt" action="/view_search" method="post">
            <label for="startId">Start:</label>
            <input required id="startId" type="text" name="start">
            <br>

            <label for="zielId">Ziel:</label>
            <input required id="zielId" type="text" name="ziel">

            <br>

            <label for="abdate" >ab: </label>
            <input required id="abId" type="date" name="abDate">

            <input type="submit">

            <br>

        </form>

        <div id="surchErgebnisse">
            <ul>

            </ul>
        </div>

    </div>
    <h3>Suchergebnisse</h3><br>
    <hr>

    <div id="searchResult">
        <#if isEmpty==true >
            sorry no results found!
        <#else>
            <#list fahrteFromSearch as trip>
                <div>
                    <form method="post" name="view_main_form_second" action="/fahrt_details_servlet?fid=${trip.getFahrtId()}">
                    <input type="image" src=${trip.getIconPath()} alt="picture"  class="icon"/><br/>
                    <br>
                    Von: ${trip.getStartOrt()} <br>
                    Nach: ${trip.getZielOrt()} <br>
                    </form>
                    <hr>

                </div>
            </#list>
        </#if>
    </div>

    <div>

    </div>

</div>
</body>
</html>