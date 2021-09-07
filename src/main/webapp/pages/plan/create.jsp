<html>
<head>
    <title>Okay &check;: Create Plan</title>
</head>
<body>

    <style>
        input,
        textarea{
            width:100%;
        }
    </style>

    <h1>Create Plan</h1>
    <form action="${pageContext.request.contextPath}/plan/save" modelAttribute="okayPlan" method="post">
        <label>Nickname</label>
        <input type="text" name="nickname" value=""/>

        <label>Amount</label>
        <input type="text" name="amount" value="" style="width:25%;"/>

        <label>Website Limit</label>
        <input type="text" name="projectLimit" value="" style="width:25%;"/>

        <label>Description</label>
        <textarea name="description"></textarea>

        <br/>
        <br/>
        <input type="submit" value="Create Okay Plan" class="button purple"/>
    </form>
</body>
</html>
