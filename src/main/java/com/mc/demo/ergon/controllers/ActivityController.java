package com.mc.demo.ergon.controllers;

import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mc.demo.ergon.dtos.ActivityDetailDto;
import com.mc.demo.ergon.dtos.ActivityDto;
import com.mc.demo.ergon.dtos.UserDto;
import com.mc.demo.ergon.services.ActivityService;

@RestController
public class ActivityController {
    private ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/activity/{id}/detail")
	public ActivityDetailDto getDetail(@PathVariable("id") Long id) {
        return activityService.getActivityDetail(id);
	}

    @GetMapping("/activity/{id}")
	public ActivityDto getSingle(@PathVariable("id") Long id) {
        return activityService.GetActivityById(id);
	}

    @GetMapping("/activity")
	public ArrayList<ActivityDto> getAll(@RequestParam(required = false) String username, @RequestParam(required = false) String status) {
        if(username == ""){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            username = ((UserDto)authentication.getPrincipal()).getUsername();
        }
        
        return activityService.GetActvities(username, status);
	}

    @PostMapping("/activity")
	public boolean post(@RequestBody ActivityDto activity) {
        return activityService.AddActivity(activity);
	}

    @PatchMapping("/activity/{id}")
	public boolean patch(@PathVariable("id") Long id, @RequestBody ActivityDto activity) {
        return activityService.UpdateActivity(id, activity);
	}

    @DeleteMapping("/activity/{id}")
	public boolean delete(@PathVariable("id") Long id) {
        return activityService.DeleteActivity(id);
	}
}
