package sigma.model;

public class VideoUser {
    Long userId;
    Long videoId;
    Long timeJoined;
    Long timeExited;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Long getTimeJoined() {
        return timeJoined;
    }

    public void setTimeJoined(Long timeJoined) {
        this.timeJoined = timeJoined;
    }

    public Long getTimeExited() {
        return timeExited;
    }

    public void setTimeExited(Long timeExited) {
        this.timeExited = timeExited;
    }
}
