package com.mc.demo.ergon;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mc.demo.ergon.models.Activity;
import com.mc.demo.ergon.models.ActivityStatus;
import com.mc.demo.ergon.models.Comment;
import com.mc.demo.ergon.models.User;
import com.mc.demo.ergon.models.UserActivity;
import com.mc.demo.ergon.repositories.ActivityRepository;
import com.mc.demo.ergon.repositories.ActivityStatusRepository;
import com.mc.demo.ergon.repositories.CommentRepository;
import com.mc.demo.ergon.repositories.UserActivityRepository;
import com.mc.demo.ergon.repositories.UserRepository;

@SpringBootTest
public class PopulateDbTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ActivityStatusRepository activityStatusRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserActivityRepository userActivityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
	public void contextLoads() {
        if(userRepository.findByUsername("admin").isPresent()){
            return;
        }

        //Activity Status
        ActivityStatus backlog = activityStatusRepository.save(new ActivityStatus("backlog", "Backlog", "#BCBCBC"));
        ActivityStatus inprogress = activityStatusRepository.save(new ActivityStatus("inprogress", "In Progress", "#3C69E7"));
        ActivityStatus completed = activityStatusRepository.save(new ActivityStatus("completed", "Completed", "#3C69E7"));

        backlog.setNextStatus(inprogress);
        inprogress.setNextStatus(completed);
        completed.setNextStatus(inprogress);

        activityStatusRepository.save(backlog);
        activityStatusRepository.save(inprogress);
        activityStatusRepository.save(completed);

        //users
        User user1 = userRepository.save(
            new User("admin", passwordEncoder.encode("123"), "Prova", "Provetti", Date.valueOf(LocalDate.of(2021, 12, 22)))
        );
        User user2 = userRepository.save(
            new User("user1", passwordEncoder.encode("456"), "Laura", "Biagiotti", Date.valueOf(LocalDate.of(2022, 1, 15)))
        );
        User user3 = userRepository.save(
            new User("user1", passwordEncoder.encode("456"), "Laura", "Biagiotti", Date.valueOf(LocalDate.of(2022, 5, 21)))
        );

        //Activity
        Activity activity1 = activityRepository.save(
            new Activity("Activity 1", "Creare il progetto angular", Date.valueOf(LocalDate.of(2022, 6, 22)), backlog)
        );
        Activity activity2 = activityRepository.save(
            new Activity("Prova attivita 2", "Creare il progetto Spring boot", Date.valueOf(LocalDate.of(2022, 7, 14)), inprogress)
        );
        Activity activity3 = activityRepository.save(
            new Activity("Creare autenticazione", "Creare servizio autenticazione", Date.valueOf(LocalDate.of(2022, 8, 14)), backlog)
        );

        //Comment
        commentRepository.save(
            new Comment(user1, activity1, "buuu non funziona buuu", Date.valueOf(LocalDate.of(2022, 6, 22)))
        );
        commentRepository.save(
            new Comment(user1, activity1, "buuu non funziona buuu part 2", Date.valueOf(LocalDate.of(2022, 6, 23)))
        );
        commentRepository.save(
            new Comment(user1, activity1, "buuu non funziona buuu part 3", Date.valueOf(LocalDate.of(2022, 6, 24)))
        );
        commentRepository.save(
            new Comment(user2, activity2, "Commentone", Date.valueOf(LocalDate.of(2022, 7, 24)))
        );

        //UserActivity
        //(Long userId, Long activityId, Long effortTime, Date creationDate)
        userActivityRepository.save(
            new UserActivity(user1.getId(), activity1.getId(), 12L, Date.valueOf(LocalDate.of(2022, 8, 24)))
        );
        userActivityRepository.save(
            new UserActivity(user1.getId(), activity2.getId(), 2L, Date.valueOf(LocalDate.of(2022, 8, 24)))
        );
        userActivityRepository.save(
            new UserActivity(user1.getId(), activity3.getId(), 16L, Date.valueOf(LocalDate.of(2022, 8, 24)))
        );
        userActivityRepository.save(
            new UserActivity(user2.getId(), activity2.getId(), 16L, Date.valueOf(LocalDate.of(2022, 8, 24)))
        );
        userActivityRepository.save(
            new UserActivity(user3.getId(), activity1.getId(), 1L, Date.valueOf(LocalDate.of(2022, 8, 24)))
        );
	}
}
