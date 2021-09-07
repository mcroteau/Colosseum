<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Okay! Monitor Project</title>

    <style type="text/css">
        #monitor-video-container{
            width:775px;
        }
        .button.disabled{
            opacity:0.4 !important;
        }
    </style>

</head>
<body>

<div id="monitor-video-container">

    <a href="${pageContext.request.contextPath}/sigma/${video.id}" class="href-dotted left-float clear">&larr; Back</a>
    <br class="clear"/>

    <h1>Edit Website</h1>

    <p>Ensure that the URL contains no redirects
        and starts with either http or https.</p>

    <c:if test="${not empty message}">
        <div class="notify">${message}</div>
    </c:if>


    <form action="${pageContext.request.contextPath}/video/update" method="post">

        <input type="hidden" name="id" value="${video.id}"/>
        <input type="hidden" name="userId" value="${video.userId}"/>

        <div class="form-group">
            <label for="name" class="nofloat">Name</label>
            <input type="text" name="name" id="name" value="${video.name}" placeholder="Example Project" style="width:70%">
        </div>


        <div class="form-group">
            <label for="name">Url</label>
            <input type="text" name="uri" id="uri" value="${video.uri}" placeholder="http://sample.io" style="width:100%;"><br/>
            <span style="display:block;margin-top:20px;font-size:14px;">Status :
                <span id="status" style="font-size:19px;"></span>
                <span id="processing" style="display:none">Processing...</span>
                <span id="success" style="display:none">Good to start monitoring</span>
            </span>
            <br class="clear"/>
        </div>

        <input type="submit" id="submit" name="submit" value="Update Website" class="button retro disabled" disabled="disabled"/>
    </form>


    <div id="video-page-actions">

        <form action="${pageContext.request.contextPath}/sigma/delete/${video.id}" method="post">
            <input onclick="return confirm('Are you sure you want to delete this web address?');"  type="submit" class="button remove" value="Delete Website"/>
        </form>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        var $uri = $("#uri"),
            $status = $("#status"),
            $processing = $("#processing"),
            $success = $("#success"),
            $submit = $("#submit"),
            processing = false

        $uri.keyup(function(){
            validateUri()
        })

        function validateUri(){
            processing = true
            $processing.show()
            $.ajax({
                method: "post",
                url : "${pageContext.request.contextPath}/video/validate?uri=" + $uri.val(),
                success : success,
                error : error
            })
        }

        function success(resp, xhr){
            $status.html(resp.status)
            if(resp.status == 200){
                console.log($submit)
                $success.show()
                $submit.attr("disabled", false).removeClass("disabled")
            }
            if(resp.status != 200){
                $success.hide()
                $submit.attr("disabled", true).addClass("disabled")
            }

            $processing.hide()
            processing = false
        }

        function error(error, xhr){
            //console.log(error, xhr)
            $status.html(error.status)
            $processing.hide()
            processing = false
            $submit.attr("disabled", true)
        }
        validateUri();
    });
</script>
</body>
</html>
