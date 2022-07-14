package com.mc.demo.ergon.controllers;

import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mc.demo.ergon.dtos.UserActivityDto;
import com.mc.demo.ergon.dtos.UserDto;
import com.mc.demo.ergon.services.UserActivityService;

@RestController
public class ActivityUserController {
    private UserActivityService activityUserService;

    public ActivityUserController(UserActivityService activityUserService) {
        this.activityUserService = activityUserService;
    }

    @GetMapping("/activity/{activityId}/user")
	public ArrayList<UserActivityDto> get(@PathVariable("activityId") Long id) {
        return activityUserService.GetUsersByActivityId(id);
	}

    @PostMapping({"/activity/{activityId}/user/{userId}", "/activity/{activityId}/user"})
	public boolean post(
        @PathVariable("activityId") Long activityId, 
        @PathVariable(required = false) Long userId, 
        @RequestParam(name = "effort", required = false) Long effort
    ) {
        if(userId == null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = ((UserDto) authentication.getPrincipal()).getId();
        }

        return activityUserService.SetUserOnActivity(activityId, userId, effort);
	}

    @PatchMapping({"/activity/{activityId}/user/{userId}", "/activity/{activityId}/user"})
	public boolean path(
        @PathVariable("activityId") Long activityId, 
        @PathVariable(required = false) Long userId, 
        @RequestParam(name = "effort") Long effort
    ) {
        if(userId == null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = ((UserDto) authentication.getPrincipal()).getId();
        }

        return activityUserService.UpdateEffortUserOnActivity(activityId, userId, effort);
	}

    @DeleteMapping({"/activity/{activityId}/user/{userId}", "/activity/{activityId}/user"})
	public boolean delete(
        @PathVariable("activityId") Long activityId, 
        @PathVariable(required = false) Long userId
    ) {
        if(userId == null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userId = ((UserDto) authentication.getPrincipal()).getId();
        }

        return activityUserService.RemoveUserOnActivity(activityId, userId);
	}
}
