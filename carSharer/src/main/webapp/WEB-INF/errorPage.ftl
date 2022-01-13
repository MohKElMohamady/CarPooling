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
            <#if errorCode==1 >
                ERROR!! The amount of seats you are trying to book is more than the available seats!!
            </#if>
            <#if errorCode==2 >
                ERROR!! You have already reserved. You cannot reserve again!!
            </#if>
            <#if errorCode==3 >
                ERROR!! You cannot delete this RIDE, YOU ARE NOT THE CREATOR. SUS!!
            </#if>
            <#if errorCode==3 >
                ERROR!! You Cannot book
            </#if>


        </p>
        <form method="post" name="error_form" action="/fahrt_details_servlet?fid=${fid}">
            <input type="submit" value="Back to previous page">
        </form>
    </div>
</div>
</body>
</html>
