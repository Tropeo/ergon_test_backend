package com.mc.demo.ergon.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mc.demo.ergon.models.CompositeKeys.UserActivityPK;

import javax.persistence.JoinColumn;

@Entity
@Table(name = "User_Activity")
@IdClass(UserActivityPK.class)
public class UserActivity {
    @Id
    @Column(name ="userId")
    private long userId;

    @Id
    @Column(name ="activityId")
    private long activityId;

    @ManyToOne
    @JoinColumn(name = "userId", updatable = false, insertable = false, referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "activityId", updatable = false, insertable = false, referencedColumnName = "id")
    private Activity activity;

    @Column(name = "effortTime", nullable = false)
    private Long effortTime;

    @Column(name = "creationDate", nullable = false)
    private Date creationDate;

    protected UserActivity() {}

    public UserActivity(Long userId, Long activityId, Long effortTime, Date creationDate) {
        this.userId = userId;
        this.activityId = activityId;
        this.effortTime = effortTime;
        this.creationDate = creationDate;
        this.user = null;
        this.activity = null;
    }

    public UserActivity(Long userId, Long activityId, User user, Activity activity, Long effortTime, Date creationDate) {
        this.userId = userId;
        this.activityId = activityId;
        this.user = user;
        this.activity = activity;
        this.effortTime = effortTime;
        this.creationDate = creationDate;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getActivityId() {
        return this.activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Long getEffortTime() {
        return this.effortTime;
    }

    public void setEffortTime(Long effortTime) {
        this.effortTime = effortTime;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
       
}


