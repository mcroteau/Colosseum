<html>
<head>
    <title>Okay! Pricing</title>
</head>
<body>
<style>
    #pricing-wrapper p{
        margin:0px;
    }
    #pricing-wrapper ul{
        margin:0px 0px 50px 50px;
    }
    #pricing-wrapper ul li{
        list-style: disc;
        font-size:27px;
        margin:16px 0px;
        font-family: "Roboto Slab" !important;
    }
    #pricing-wrapper ul li:hover{
        cursor: pointer;
        cursor: hand;
    }
    #pricing-wrapper ul li:hover a{
        display:inline-block;
    }
    #pricing-everything{
        font-size:29px !important;
    }
    .buttons-right{
        text-align: center;
    }
</style>

    <div id="pricing-wrapper">

        <h1>Pricing</h1>

        <p id="pricing-everything">Each plan includes 24x7 monitoring &amp; unlimited SMS Messages.</p>
        <p>Just in case your website is unresponsive.</p>

        <p style="margin-top:40px;font-size:27px;">Plans:</p>

        <ul>
            <li>3 Websites @ Free <a href="${pageContext.request.contextPath}/signup" class="button tiny yellow">Start</a></li>
            <li>5 Websites @ $13/month <a href="${pageContext.request.contextPath}/signup?p=5" class="button tiny yellow">Start</a></li>
            <li>12 Websites @ $24/month <a href="${pageContext.request.contextPath}/signup?p=12" class="button tiny yellow">Start</a></li>
        </ul>


        <div class="buttons-right">
            <a href="${pageContext.request.contextPath}/signup" class="button purple">Start Monitoring Projects !</a>
        </div>
    </div>
</body>
</html>
