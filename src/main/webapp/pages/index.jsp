<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>E=mc² Auditorium! : A colosseum based approach to learning.</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/media/icon.png?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/pasetl.css?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css?v=<%=System.currentTimeMillis()%>">

    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/packages/jquery.js"></script>

</head>
<body>
    <div id="homepage-wrapper">

        <c:if test="${not empty message}">
            <p class="notify">${message}</p>
        </c:if>

        <p class="welcome-text">Education made simple. Teach, learn, go...
            its the only way to grow!
        </p>

        <p style="width:75%">Auditorium is a community driven education
            outlet where people like you can teach or learn as they desire.
            Registration is free for Teachers, Professors and Instructors.
            Start your educational journey today!
        </p>

        <p class="center-align" style="margin-top:30px;"><a href="${pageContext.request.contextPath}/signup" class="button yellow super">Signup Up !</a></p>


        <p style="margin-top:50px;"><strong>Professors, Teachers & Instructors</strong> do you know how to speak mandarin?
            Program or write? You can share your skills with the world.
            You decide how long the course or class should be and the fees associated with them.</p>


        <p><strong>Scholars, Students & Attendees</strong> do you want to learn
            something new in your spare time without getting out of your sweats?
            Auditorium is for you.</p>

        <style>
            #homepage-wrapper p{
                line-height: 1.4em;
                margin:10px 0px;
                font-size: 19px;
            }
            .welcome-text{
                line-height: 1.1em !important;
                font-size:34px !important;
                width:60%;
                font-family: "Roboto Slab" !important;
            }
            .welcome-text span{
                font-size:19px !important;
                display: block;
                font-family: "Roboto Slab" !important;
            }

            @media screen and (max-width: 690px) {
                p{
                    width:calc(100% - 40px);
                }
            }
        </style>
    </div>

</body>