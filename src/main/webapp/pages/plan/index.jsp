<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Okay &check;: Plans</title>
</head>
<body>

    <style>
        table{
            width:100%;
            margin:30px auto;
        }
        th{
            font-family: roboto-slab-black !important;
        }
        td{
            text-align: center;
        }
    </style>

    <h1>Plans</h1>

    <a href="${pageContext.request.contextPath}/plan/create" class="href-dotted">New Plan</a>

    <table>
        <tr>
            <th>Id</th>
            <th>Nickname</th>
            <th>Website Limit</th>
            <th>Amount</th>
        </tr>
        <c:forEach var="okayPlan" items="${okayPlans}">
            <tr>
                <td>${okayPlan.id}</td>
                <td>${okayPlan.nickname}</td>
                <td>${okayPlan.projectLimit}</td>
                <td>${okayPlan.amount}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/plan/delete/${okayPlan.id}" method="post">
                        <input type="submit" value="&nbsp;-&nbsp;" class="button tiny" onclick="return confirm('Are you sure you want to delete plan?');" />
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
