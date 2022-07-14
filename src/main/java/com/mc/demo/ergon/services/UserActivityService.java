package com.mc.demo.ergon.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mc.demo.ergon.dtos.UserActivityDto;
import com.mc.demo.ergon.models.Activity;
import com.mc.demo.ergon.models.UserActivity;
import com.mc.demo.ergon.models.CompositeKeys.UserActivityPK;
import com.mc.demo.ergon.models.User;
import com.mc.demo.ergon.repositories.ActivityRepository;
import com.mc.demo.ergon.repositories.UserActivityRepository;
import com.mc.demo.ergon.repositories.UserRepository;

@Service
public class UserActivityService {
    private UserActivityRepository userActivityRepository;
    private UserRepository userRepo;
    private ActivityRepository activityRepo;

    public UserActivityService(UserActivityRepository userActivityRepository, UserRepository userRepo, ActivityRepository activityRepo) {
        this.userActivityRepository = userActivityRepository;
        this.userRepo = userRepo;
        this.activityRepo = activityRepo;
    }

    public ArrayList<UserActivityDto> GetUsersByActivityId(Long activityId){
        Iterable<UserActivity> activityUsers = userActivityRepository.findByActivityId(activityId);

        ArrayList<UserActivityDto> ret = new ArrayList<UserActivityDto>();

        activityUsers.forEach(activityUser -> {
            ret.add(
                new UserActivityDto(
                    activityUser.getUser().getId(), 
                    activityUser.getUser().getUsername(), 
                    activityUser.getActivity().getId(),
                    activityUser.getActivity().getTitle(),
                    activityUser.getEffortTime(),
                    activityUser.getCreationDate()
                )
            );
        });

        return ret;
    }

    public boolean UpdateEffortUserOnActivity(Long activityId, Long userId, Long effort){
        Optional<UserActivity> optUserActi = userActivityRepository.findById(new UserActivityPK(userId, activityId));

        if (optUserActi.isEmpty())
            return false;

        UserActivity userActivity = optUserActi.get();

        userActivity.setEffortTime(effort);

        UserActivity createdAssociation = userActivityRepository.save(userActivity);

        return createdAssociation.getUser() != null && createdAssociation.getActivity() != null;
    }

    public boolean SetUserOnActivity(Long activityId, Long userId, Long effort){
        Optional<User> optUser = userRepo.findById(userId);

        if (optUser.isEmpty())
            return false;

        Optional<Activity> optActivity = activityRepo.findById(activityId);

        if (optActivity.isEmpty())
            return false;

        User user = optUser.get();
        Activity activity = optActivity.get();

        UserActivity createdAssociation = userActivityRepository.save(
            new UserActivity(
                user.getId(),
                activity.getId(),
                user,
                activity,
                Optional.ofNullable(effort).orElse((long)0),
                new java.sql.Date(new Date().getTime())
            )
        );

        return createdAssociation.getUser() != null && createdAssociation.getActivity() != null;
    }

    public boolean RemoveUserOnActivity(Long activityId, Long userId){
        Optional<UserActivity> optUserActi = userActivityRepository.findById(new UserActivityPK(userId, activityId));

        if (optUserActi.isEmpty())
            return false;

        UserActivity userActivity = optUserActi.get();

        try {
            userActivityRepository.delete(userActivity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
