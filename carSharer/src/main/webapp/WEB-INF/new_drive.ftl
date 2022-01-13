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
        <h1> Fahrt erstellen </h1>
    </div>

    <div id="site">
        <form method="post" action="/new_drive">

            <label for="start">Von:</label> <input name="start" type="text">
            <br>
            <label for="destination">Bis:</label> <input name="destination" type="text">
            <br>
            <label for=maxCapacity>Maximale Kapazitaet:</label> <input class="maxCapacity" name="maxCapacity" type="number" required max="10">
            <br>
            <label for="tripCost">Fahrtkosten</label> <input name="tripCost" pattern=" 0+\.[0-9]*[1-9][0-9]*$" type="number" required min="1">
            <br>
            <div>
                <label>Transportmittel:</label>
                <input type="radio" name="transportmittel" value="1"> <label for="transportmittel">Auto</label>
                <input type="radio" name="transportmittel" value="2"> <label for="transportmittel">Bus</label>
                <input type="radio" name="transportmittel" value="3"> <label for="transportmittel">Kleintransporter</label>
                <br>
            </div>

            <label>Fahrtdatum </label> <input name="date" type="date"> <input name="time" type="time">

            <br>

            <label>Beschreibung: </label>
            <br>
            <textarea name="description" cols="25" rows="5" maxlength="50">
                Enter the description here!
            </textarea>

            <br>
            <input type="submit" value="Erstellen" >

        </form>

    </div>


    <br/>
</div>
</body>
</html>
