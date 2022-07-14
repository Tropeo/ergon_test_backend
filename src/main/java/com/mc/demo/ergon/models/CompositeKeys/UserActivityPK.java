package com.mc.demo.ergon.models.CompositeKeys;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class UserActivityPK implements Serializable {
    private long userId;
    private long activityId;

    protected UserActivityPK() {}

    public UserActivityPK(long userId, long activityId) {
        this.userId = userId;
        this.activityId = activityId;
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


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserActivityPK)) {
            return false;
        }
        UserActivityPK userActivityPK = (UserActivityPK) o;
        return userId == userActivityPK.userId && activityId == userActivityPK.activityId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, activityId);
    }
    
}
