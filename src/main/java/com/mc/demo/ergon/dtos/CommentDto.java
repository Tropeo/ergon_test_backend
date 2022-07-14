package com.mc.demo.ergon.dtos;

import java.util.Date;

public class CommentDto {
    private Long id;

    private Long userId;

    private Long activityId;

    private String content;
    private Date creationDate;

    public CommentDto() { }

    public CommentDto(Long id, Long userId, Long activityId, String content, Date creationDate) {
        this.id = id;
        this.userId = userId;
        this.activityId = activityId;
        this.content = content;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getActivityId() {
        return this.activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
