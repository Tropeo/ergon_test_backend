package com.mc.demo.ergon.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mc.demo.ergon.models.UserActivity;
import com.mc.demo.ergon.models.CompositeKeys.UserActivityPK;

public interface UserActivityRepository extends CrudRepository<UserActivity, UserActivityPK> {
    Iterable<UserActivity> findByActivityId(Long id);
}