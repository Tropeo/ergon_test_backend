package com.mc.demo.ergon.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mc.demo.ergon.dtos.ActivityDetailDto;
import com.mc.demo.ergon.dtos.ActivityDto;
import com.mc.demo.ergon.dtos.CommentDto;
import com.mc.demo.ergon.dtos.UserActivityDto;
import com.mc.demo.ergon.models.Activity;
import com.mc.demo.ergon.models.ActivityStatus;
import com.mc.demo.ergon.models.Comment;
import com.mc.demo.ergon.repositories.ActivityRepository;
import com.mc.demo.ergon.repositories.ActivityStatusRepository;

import com.mc.demo.ergon.utils.ACTIVITY_STATUSES;

@Service
public class ActivityService {
    private ActivityRepository activityRepo;
    private ActivityStatusRepository activityStatusRepo;

    public ActivityService(ActivityRepository activityRepo, ActivityStatusRepository activityStatusRepo) {
        this.activityRepo = activityRepo;
        this.activityStatusRepo = activityStatusRepo;
    }

    public ActivityDto GetActivityById(Long id) {
        Optional<Activity> optActivity = activityRepo.findById(id);

        if (optActivity.isEmpty())
            return null;

        Activity activity = optActivity.get();

        return new ActivityDto(
                activity.getId(),
                activity.getTitle(),
                activity.getDescription(),
                activity.getCreationDate(),
                activity.getStatus().getId());
    }

    public ArrayList<ActivityDto> GetActvities(String username, String status) {
        ArrayList<ActivityDto> ret = new ArrayList<ActivityDto>();
        Iterable<Activity> activities;

        if((username != null && username.trim() != "") || (status != null && status.trim() != "")){
            if(username != null && username.trim() == "")
                username = null;

            if(status != null && status.trim() == "")
                status = null;

            activities = activityRepo.getActivitiesByUsernameOrStatus(username, status);
        }else{
            activities = activityRepo.findAll();
        }

        activities.forEach(activity -> {
            ret.add(
                new ActivityDto(
                    activity.getId(),
                    activity.getTitle(),
                    activity.getDescription(),
                    activity.getCreationDate(),
                    activity.getStatus().getId()
                )
            );
        });

        return ret;
    }

    public boolean AddActivity(ActivityDto newUser) {
        ActivityStatus status = activityStatusRepo.findById(ACTIVITY_STATUSES.BACKLOG_STATUS).get();

        Activity createdActivity = activityRepo.save(
                new Activity(
                        newUser.getTitle(),
                        newUser.getDescription(),
                        new java.sql.Date(new Date().getTime()),
                        status));

        return createdActivity.getId() != null;
    }

    public boolean DeleteActivity(Long id) {
        Optional<Activity> optActivity = activityRepo.findById(id);

        if (optActivity.isEmpty())
            return false;

        Activity activity = optActivity.get();

        if (activity.getRelatedUsers().size() > 0)
            return false;

        if (activity.getComments().size() > 0)
            return false;

        try {
            activityRepo.delete(activity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean UpdateActivity(Long id, ActivityDto selectedActivity) {
        Optional<Activity> optActivity = activityRepo.findById(id);

        if (optActivity.isEmpty())
            return false;

        Activity activity = optActivity.get();

        Optional.ofNullable(selectedActivity.getTitle()).ifPresent(title -> activity.setTitle(title));
        Optional.ofNullable(selectedActivity.getDescription()).ifPresent(descr -> activity.setDescription(descr));

        Optional.ofNullable(selectedActivity.getStatus()).ifPresent(
            selectedStatus -> activityStatusRepo.findById(selectedStatus).ifPresent(status -> {
                if(isStatusUpdatable(activity.getStatus(), status)){
                    activity.setStatus(status);
                }
            })
        );

        Activity updatedActivity = activityRepo.save(activity);

        return updatedActivity.getId() != null;
    }

    public ActivityDetailDto getActivityDetail(Long activityId){
        Optional<Activity> optActivity = activityRepo.findById(activityId);

        if (optActivity.isEmpty())
            return null;

        Activity activity = optActivity.get();

        ArrayList<CommentDto> comments = new ArrayList<CommentDto>();

        ArrayList<Comment> rawComments = new ArrayList<Comment>(activity.getComments());

        rawComments.sort(new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return o2.getCreationDate().compareTo(o1.getCreationDate());
            }
        });
        
        rawComments.forEach(comment -> {
            comments.add(
                new CommentDto(
                    comment.getId(),
                    Optional.ofNullable(comment.getUser()).map(x -> x.getId()).orElse((long) 0),
                    Optional.ofNullable(comment.getActivity()).map(x -> x.getId()).orElse((long) 0),
                    comment.getContent(),
                    comment.getCreationDate()
                )
            );
        });

        ArrayList<UserActivityDto> relatedUsers = new ArrayList<UserActivityDto>();
        activity.getRelatedUsers().forEach(relatedUser -> {
            relatedUsers.add(
                new UserActivityDto(
                    relatedUser.getUser().getId(), 
                    relatedUser.getUser().getUsername(), 
                    relatedUser.getActivity().getId(),
                    relatedUser.getActivity().getTitle(),
                    relatedUser.getEffortTime(),
                    relatedUser.getCreationDate()
                )
            );
        });

        Long totalEffort = relatedUsers.stream().mapToLong(x -> x.getEffort()).sum();

        return new ActivityDetailDto(
            activity.getId(),
            activity.getTitle(),
            activity.getDescription(),
            activity.getCreationDate(),
            activity.getStatus().getId(),
            comments,
            relatedUsers,
            totalEffort
        );
    }

    private boolean isStatusUpdatable (ActivityStatus current, ActivityStatus next){
        ActivityStatus currentSuitableNextStatus = Optional.ofNullable(current).map(x -> x.getNextStatus()).orElse(null);
        String nextId = Optional.ofNullable(next).map(x -> x.getId()).orElse(null);

        return currentSuitableNextStatus != null && currentSuitableNextStatus.getId() == nextId;
    }

    
}
