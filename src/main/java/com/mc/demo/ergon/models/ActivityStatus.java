package com.mc.demo.ergon.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ActivityStatus")
public class ActivityStatus {
    
    @Id
    @Column(name = "id") 
    private String id;

    @Column(name = "description") 
    private String description;

    @Column(name = "color") 
    private String color;

    @OneToMany(mappedBy = "status")
    private List<Activity> Activities;

    @ManyToOne()
    @JoinColumn(name = "next_status_fk", referencedColumnName="id", nullable = true)
    private ActivityStatus nextStatus;

    @OneToMany(mappedBy = "nextStatus", fetch = FetchType.LAZY)
    private List<ActivityStatus> previousStatuses;

    protected ActivityStatus() {}

    public ActivityStatus(String id, String description, String color) {
        this.id = id;
        this.description = description;
        this.color = color;
        this.nextStatus = null;
        this.Activities = new ArrayList<Activity>();
        this.previousStatuses = new ArrayList<ActivityStatus>();
    }

    public ActivityStatus(String id, String description, String color, ActivityStatus nextStatus) {
        this.id = id;
        this.description = description;
        this.color = color;
        this.nextStatus = nextStatus;
        this.Activities = new ArrayList<Activity>();
        this.previousStatuses = new ArrayList<ActivityStatus>();
    }

    public ActivityStatus(String id, String description, String color, ActivityStatus nextStatus, List<Activity> Activities, List<ActivityStatus> previousStatuses) {
        this.id = id;
        this.description = description;
        this.color = color;
        this.Activities = Activities;
        this.nextStatus = nextStatus;
        this.previousStatuses = previousStatuses;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Activity> getActivities() {
        return this.Activities;
    }

    public void setActivities(List<Activity> Activities) {
        this.Activities = Activities;
    }

    public ActivityStatus getNextStatus() {
        return this.nextStatus;
    }

    public void setNextStatus(ActivityStatus nextStatus) {
        this.nextStatus = nextStatus;
    }

    public List<ActivityStatus> getPreviousStatuses() {
        return this.previousStatuses;
    }

    public void setPreviousStatuses(List<ActivityStatus> previousStatuses) {
        this.previousStatuses = previousStatuses;
    }
    
}