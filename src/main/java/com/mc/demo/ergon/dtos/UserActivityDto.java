package com.mc.demo.ergon.dtos;

import java.util.Date;

public class UserActivityDto {
    private Long userId;
    private String username;

    private Long activityId;
    private String activityTitle;

    private Long effort;
    private Date creationDate;

    public UserActivityDto() {
    }

    public UserActivityDto(Long userId, String username, Long activityId, String activityTitle, Long effort, Date creationDate) {
        this.userId = userId;
        this.username = username;
        this.activityId = activityId;
        this.activityTitle = activityTitle;
        this.effort = effort;
        this.creationDate = creationDate;
    }


    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getActivityId() {
        return this.activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityTitle() {
        return this.activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public Long getEffort() {
        return this.effort;
    }

    public void setEffort(Long effort) {
        this.effort = effort;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
