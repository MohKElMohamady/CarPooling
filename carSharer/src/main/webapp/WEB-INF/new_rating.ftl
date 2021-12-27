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
            Fahrt Bewertung
            Please enter your ratings!
        </p>

        <form name="enterRating" action="/view_main_servlet" method="post">
            <label for="bewertungsTextId">Bewertungstext:</label>
            <br>
            <textarea id="bewertungsTextId" class="bewertungsTextClass" name="bewertungsText" cols="10" rows="20">
                Enter the description here!
            </textarea>

            <br>

            <div class="ratingNumberBlock">
                <p>
                    Bewertungsrating
                </p>

                <!-- Ratings can be either from 0 to 5 and will be captured from a radio button  -->
                <label for="bewertungsValueId">0</label>
                <input id="bewertungValueId" name="bewertungAddedByUser" type="radio" id="rating" value="0" >

                <label for="bewertungsValueId">1</label>
                <input id="bewertungValueId" name="bewertungAddedByUser" type="radio" id="rating" value="1" >

                <label for="bewertungsValueId">2</label>
                <input id="bewertungValueId" name="bewertungAddedByUser" type="radio" id="rating" value="2" >

                <label for="bewertungsValueId">3</label>
                <input id="bewertungValueId" name="bewertungAddedByUser" type="radio" id="rating" value="3" >

                <label for="bewertungsValueId">4</label>
                <input id="bewertungValueId" name="bewertungAddedByUser" type="radio" id="rating" value="4" >

                <label for="bewertungsValueId">5</label>
                <input id="bewertungValueId" name="bewertungAddedByUser" type="radio" id="rating" value="5" >



            </div>

            <input type="submit">

        </form>

    </div>
</div>
</body>
</html>