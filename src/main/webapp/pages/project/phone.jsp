<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Okay : Add Phone</title>
</head>
<body>

    <style>
        #phone{
            width:272px;
        }
    </style>

    <c:if test="${not empty message}">
        <div class="notify">${message}</div>
    </c:if>

    <h1>Add Phone</h1>
    <p>Enter a good cellphone number where we can contact you if
        something goes wrong. No spaces, no special characters.
    </p>
    <form action="${pageContext.request.contextPath}/sigma/phone/add/${video.id}" method="post">
        <input type="hidden" name="projectId" value="${video.id}"/>
        <input type="text" name="phone" value="" placeholder="" id="phone"/>
        <input type="submit" value="Add Phone" class="button purple"/>
    </form>
</body>
</html>
