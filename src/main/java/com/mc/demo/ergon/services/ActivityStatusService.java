package com.mc.demo.ergon.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.mc.demo.ergon.dtos.ActivityStatusDto;
import com.mc.demo.ergon.repositories.ActivityStatusRepository;

@Service
public class ActivityStatusService {
    private ActivityStatusRepository activityStatusRepo;

    public ActivityStatusService(ActivityStatusRepository activityStatusRepo) {
        this.activityStatusRepo = activityStatusRepo;
    }

    public ArrayList<ActivityStatusDto> GetAllStatuses() {
        ArrayList<ActivityStatusDto> ret = new ArrayList<ActivityStatusDto>();

        activityStatusRepo.findAll().forEach(x -> {
            ret.add(new ActivityStatusDto(x.getId(), x.getDescription(), x.getColor()));
        });

        return ret;
    }
}
