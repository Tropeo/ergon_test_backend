package com.mc.demo.ergon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mc.demo.ergon.models.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long>{
    @Query(
        "SELECT A FROM Activity A " +
        "INNER JOIN UserActivity UA ON UA.activityId = A.id " +
        "INNER JOIN User U ON UA.userId = U.id " +
        "WHERE (:username is null OR U.username = :username) AND (:status is null OR A.status.id=:status) "
    )
    Iterable<Activity> getActivitiesByUsernameOrStatus(@Param("username") String username, @Param("status") String status);
}
