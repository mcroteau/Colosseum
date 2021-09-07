<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Okay! ${video.name}</title>
</head>
<body>

<style>
    #video-page-wrapper{
        text-align: center;
        padding:10px 20px 30px;
        background: #f7f9f9;
        background: #FFF8E9;
        background: #f5f4cc;
        border:solid 0px #C9DCDC;
        border-radius: 20px;
    }
    #uri{
        font-size:29px;
    }
    #video-page-actions{
        margin-top:30px;
        text-align: right;
    }
    .video-stats{
        width:100%;
        margin:20px 0px 0px;
    }
    .video-stats th{
        text-align: right;
        font-family: Roboto !important;
        font-weight:300;
    }
    .video-stats td{
        text-align: left;
        font-family: Roboto !important;
    }
    .video-stats th,
    .video-stats td{
        width:calc(46%);
        padding:13px 5px;
        font-size:23px;
        vertical-align: middle;
    }
    #video-phones-list{
        text-align: center;
    }
    #video-phones-list h3{
        color:#1e4f4f;
        color:#000;
        margin:30px 0px 10px;
    }
    #video-phones-list form{
        font-size:25px;
        font-family: "Roboto Slab" !important;
    }
    ul li{
        margin:10px 0px;
    }
    label{
        color: #3e464a;
        font-size: 19px;
        vertical-align: bottom;
        font-weight:300;
    }
    label span{
        font-size:23px;
        vertical-align: bottom;
        font-family: Roboto !important;
        font-weight: 900;
    }

</style>

    <c:if test="${not empty message}">
        <div class="notify">${message}</div>
    </c:if>


    <div id="video-page-wrapper">

        <a href="${pageContext.request.contextPath}/" class="href-dotted left-float clear">&larr; Back</a>
        <br class="clear"/>

        <h1>${video.name}
            <c:if test="${video.status == '200'}">&check;</c:if>
        </h1>
        <p class="regular" id="uri">${video.uri}
            <a href="${pageContext.request.contextPath}/sigma/edit/${video.id}" class="href-dotted">Edit</a></p>


        <c:if test="${!video.initial}">
            <table class="video-stats">
                <tr>
                    <th>Avg Resp:</th>
                    <td>${video.avgResponse}</td>
                </tr>
                <tr>
                    <th>Status:</th>
                    <td>
                        <span class="ok">${video.status}</span>
                        <c:if test="${video.status == '200'}"> <span class="bold">Ok</span></c:if>
                    </td>
                </tr>
                <tr>
                    <th>Percent Uptime:</th>
                    <td>${video.percentUptime}%</td>
                </tr>
                <tr>
                    <th>Last Monitored:</th>
                    <td>${video.prettyTime}&nbsp;${video.actualDate}</td>
                </tr>
            </table>
        </c:if>

        <c:if test="${video.initial}">
            <table class="video-stats">
                <tr>
                    <th>Avg Resp:</th>
                    <td><span class="ok not" style="font-size: 12px;">getting data...</span></td>
                </tr>
                <tr>
                    <th>Status:</th>
                    <td></td>
                </tr>
                <tr>
                    <th>Percent Uptime:</th>
                    <td></td>
                </tr>
                <tr>
                    <th>Last Monitored:</th>
                    <td></td>
                </tr>
            </table>
        </c:if>

        <div id="video-phones-list">
            <h3>Contact Phones</h3>
            <ul>
                <c:forEach var="projectPhone" items="${video.projectPhones}">
                    <li>
                        <form action="${pageContext.request.contextPath}/sigma/phone/delete/${projectPhone.id}" method="post">
                            ${projectPhone.phone}
                            <input type="submit" value="&nbsp;-&nbsp;" class="button remove tiny" onclick="return confirm('Are you sure you want to delete Phone?');" />
                        </form>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <div id="video-page-actions">
            <a href="${pageContext.request.contextPath}/sigma/phone/add/${video.id}" class="href-dotted">+ Add Phone</a>
        </div>
    </div>



<script>
    $(document).ready(function(){

        var g = {},
            data = [],
            processing = false,
            pings = 0;

        var $pingDate = $("#ping-date"),
            $projectStatus = $("#sigma-status"),
            $projectResponseTime = $("#sigma-response"),
            $projectUptime = $("#sigma-uptime"),
            $downStatus = $("#down-status"),
            $projectName = $("#sigma-name")

        // var pingInterval = setInterval(ping, 1000);


        function ping(){
            if(!processing){
                processing = true
                $.ajax({
                    method:'post',
                    url : "${pageContext.request.contextPath}/video/validate?uri=${video.uri}",
                    success : success,
                    error: error
                })
            }
        }


        function success(resp, n){
            var data = JSON.parse(resp);

            $projectStatus.html(data.status + (data.status == "200" ? " Ok" : ""))
            $projectResponseTime.html(data.responseTime)

            if(data.statusCode != 200){
                $downStatus.show()
                $projectName.addClass("down")
            }

            if(data.StatusCode == 200){
                $downStatus.hide()
                $projectName.removeClass("down")
            }

            processing = false
        }

        function error(){
            console.log("error...")
            processing = false
        }

        function clean() {
            clearInterval(pingInterval);
        }

    });
</script>

</body>
</html>
