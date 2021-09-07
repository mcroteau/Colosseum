<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Okay! Overview</title>
</head>
<body>

<c:if test="${not empty message}">
    <div class="notify">${message}</div>
</c:if>

<style>
    #videos-list{
        margin:30px auto 100px;
    }
    .video-wrapper{
        clear: both;
        display:block;
        padding:10px 10px;
        text-decoration: none;
        vertical-align: middle;
        margin:3px 0px;
        border-radius: 13px;
        background:#f5f4cc;
        background: inherit;
        border:solid 0px #C9DCDC
    }
    .video-wrapper:hover{
        background:#f5f4cc;
    }
    .video-wrapper.down{
        background:#CE6A6A;
    }
    .video-wrapper.down:hover{
        background: #e27e7e;
    }
    .video-wrapper.down .video-name,
    .video-wrapper.down .video-name span,
    .video-wrapper.down .video-time,
    .video-wrapper.down .video-stats{
        color: #ffbbbb;
    }
    .video-wrapper.down .operational{
        font-size:12px;
        color:#fff !important;
        font-family: Arial !important;
        font-weight: bold;
    }
    .video-name{
        float:left;
        width:50%;
        font-size:39px;
        font-family:Arial !important;
        font-weight: bold;
    }
    .video-name span{
        color:#3F4EAC;
        display:block;
        font-size:23px;
        font-weight: normal;
    }
    .video-time{
        font-size:12px;
    }
    .video-stats{
        float:right;
    }
    .video-stats label{
        display:block;
        margin:0;
        padding:0;
        line-height: 1.0;
    }
</style>

<c:if test="${videos.size() > 0}">
    <div id="videos-list">
        <c:forEach var="video" items="${videos}">

            <c:set var="operational" value=""/>

            <c:if test="${video.status != '200' && video.status != 'getting data...'}">
                <c:set var="operational" value="down"/>
            </c:if>

            <a href="${pageContext.request.contextPath}/sigma/${video.id}" class="video-wrapper ${operational}">
                <div class="video-name">
                        ${video.name}<c:if test="${video.status == '200'}">&check;</c:if>

                    <span>${video.uri}</span>

                    <c:if test="${operational == 'down'}">
                        <span class="operational">Down!</span>
                    </c:if>
                </div>
                <div class="video-stats">
                    <label>Status :
                            <c:if test="${video.status == '200'}">
                                <span class="ok">${video.status}</span> <span class="bold">Ok</span>
                            </c:if>
                            <c:if test="${video.status != '200'}">
                                <span class="ok not">${video.status}</span>
                            </c:if>
                    </label>
                    <label>Avg Resp :
                    </label>
                    <div class="video-time">
                        ${video.prettyTime}&nbsp;${video.actualDate}
                    </div>
                </div>
                <br class="clear"/>
            </a>
        </c:forEach>
    </div>
</c:if>
<c:if test="${videos.size() == 0}">
    <p>No web sites added to your portfolio yet!&nbsp;
        <a href="${pageContext.request.contextPath}/video/create" class="href-dotted">+ Website</a>
    </p>
</c:if>
</body>
</html>
