package com.mc.demo.ergon.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "creationDate", nullable = false)
    private Date creationDate;

    @ManyToOne()
    @JoinColumn(name = "status_fk", nullable = false)
    private ActivityStatus status;

    @OneToMany(mappedBy = "activity")
    private List<Comment> comments;

    @OneToMany(mappedBy = "activity")
    private List<UserActivity> relatedUsers;

    protected Activity() {
    }

    public Activity(String title, String description, Date creationDate, ActivityStatus status) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.status = status;
        this.comments = new ArrayList<Comment>();
        this.relatedUsers = new ArrayList<UserActivity>();
    }

    public Activity(String title, String description, Date creationDate, ActivityStatus status,
            List<Comment> comments, List<UserActivity> relatedUsers) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.status = status;
        this.comments = comments;
        this.relatedUsers = relatedUsers;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public ActivityStatus getStatus() {
        return this.status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<UserActivity> getRelatedUsers() {
        return this.relatedUsers;
    }

    public void setRelatedUsers(List<UserActivity> relatedUsers) {
        this.relatedUsers = relatedUsers;
    }

    
}
