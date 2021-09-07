<html>
<head>
    <title>Okay! Website Monitoring</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/media/icon.png?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/pastel.css?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css?v=<%=System.currentTimeMillis()%>">

    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/packages/jquery.js"></script>

</head>
<body>

<div id="outer-wrapper">

    <div id="header-wrapper">
        <a href="${pageContext.request.contextPath}" class="logo logo-basic">Okay<span class="apostrophe">&check;</span>
            <span id="tagline">Http Monitoring</span>
        </a>

        <div id="navigation">
            <a href="${pageContext.request.contextPath}/pricing" class="href-dotted">Pricing</a>
            <a href="${pageContext.request.contextPath}/signin" class="href-dotted">Signin</a>
        </div>
        <br class="clear"/>
    </div>

    <div id="content-wrapper">
        <jsp:include page="${page}"/>
    </div>

    <div id="footer-wrapper">
        &copy; 2021 Okay&check;
    </div>

    <style>
        #header-wrapper{
            margin-bottom:30px;
        }
        #footer-wrapper{
            margin:103px auto 200px;
            text-align: center;
        }
    </style>
</div>

</body>
</html>