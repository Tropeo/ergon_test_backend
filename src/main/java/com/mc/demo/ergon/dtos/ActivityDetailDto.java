package com.mc.demo.ergon.dtos;

import java.util.ArrayList;
import java.util.Date;

public class ActivityDetailDto extends ActivityDto {

    private ArrayList<CommentDto> comments;
    private ArrayList<UserActivityDto> users;
    private Long totalEffort;

    public ActivityDetailDto() { super(); }

    public ActivityDetailDto(Long id, String title, String description, Date creationDate, String status) {
        super(id, title, description, creationDate, status);
    }

    public ActivityDetailDto(Long id, String title, String description, Date creationDate, String status, ArrayList<CommentDto> comments, ArrayList<UserActivityDto> users, Long totalEffort) {
        super(id, title, description, creationDate, status);
        this.comments = comments;
        this.users = users;
        this.totalEffort = totalEffort;
    }

    public ArrayList<CommentDto> getComments() {
        return this.comments;
    }

    public void setComments(ArrayList<CommentDto> comments) {
        this.comments = comments;
    }

    public ArrayList<UserActivityDto> getUsers() {
        return this.users;
    }

    public void setUsers(ArrayList<UserActivityDto> users) {
        this.users = users;
    }

    public Long getTotalEffort() {
        return this.totalEffort;
    }

    public void setTotalEffort(Long totalEffort) {
        this.totalEffort = totalEffort;
    }


}
