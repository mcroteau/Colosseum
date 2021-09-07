<html>
<head>
    <title>Okay! : We are on it!</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/icon.png?v=<%=System.currentTimeMillis()%>">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css?v=<%=System.currentTimeMillis()%>">

    <style>
        body{
            text-align:center;
        }
        #outer-wrapper{
            width:960px;
            text-align:left;
            margin:10px auto 120px 10px;
        }
    </style>

</head>
<body>

    <div id="outer-wrapper">
        <div id="navigation">
            <a href="${pageContext.request.contextPath}/admin/user/list" class="href-dotted">Users</a>&nbsp;
        </div>
        <div id="content-wrapper">
            <jsp:include page="${page}"/>
        </div>
    </div>

</body>
</html>