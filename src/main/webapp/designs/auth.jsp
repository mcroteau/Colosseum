<html>
<head>
    <title>E=mc² Auditorium! : A colosseum based approach to learning.</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/media/icon.png?v=<%=System.currentTimeMillis()%>">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/pastel.css?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css?v=<%=System.currentTimeMillis()%>">

    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/packages/jquery.js"></script>

    <style>
        body{
            background:#fffeda;
        }
    </style>

</head>
<body>

<div id="outer-wrapper">

    <div id="header-wrapper">
        <a href="${pageContext.request.contextPath}" class="logo">Okay<span class="black apostrophe">&check;</span>
            <span id="tagline">Http Monitoring</span>
        </a>

        <div id="navigation">
            <span id="welcome">Hello <a href="${pageContext.request.contextPath}/user/edit/${sessionScope.userId}" class="href-dotted-black zero"><strong>${sessionScope.username}</strong></a>!
            </span>
            <a href="${pageContext.request.contextPath}/video/overview" class="href-dotted">Overview</a>
            <a href="${pageContext.request.contextPath}/video/create" class="href-dotted">Add Website</a>
        </div>
        <br class="clear"/>
    </div>

    <div id="content-wrapper">
        <jsp:include page="${page}"/>
    </div>

    <div id="footer-wrapper">
        <a href="${pageContext.request.contextPath}/signout" class="href-dotted">Signout</a>
    </div>

</div>

</body>
</html>