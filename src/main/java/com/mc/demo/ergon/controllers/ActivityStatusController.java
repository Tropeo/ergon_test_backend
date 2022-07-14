package com.mc.demo.ergon.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mc.demo.ergon.dtos.ActivityStatusDto;
import com.mc.demo.ergon.services.ActivityStatusService;

@RestController
public class ActivityStatusController {
    private ActivityStatusService activityStatusService;

    public ActivityStatusController(ActivityStatusService activityStatusService) {
        this.activityStatusService = activityStatusService;
    }

    @GetMapping("/activity/statuses")
	public ArrayList<ActivityStatusDto> get() {
        return activityStatusService.GetAllStatuses();
	}
}
