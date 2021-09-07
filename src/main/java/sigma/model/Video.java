package sigma.model;

import java.util.List;

public class Video {

    Long id;
    String title;
    Integer amount;
    String key;
    String status;
    Long startTime;
    Long endTime;
    List<VideoAttendant> videoAttendants;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public List<VideoAttendant> getVideoUsers() {
        return videoAttendants;
    }

    public void setVideoUsers(List<VideoAttendant> videoAttendants) {
        this.videoAttendants = videoAttendants;
    }
}
