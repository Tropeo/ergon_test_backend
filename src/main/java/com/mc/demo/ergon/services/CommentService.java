package com.mc.demo.ergon.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mc.demo.ergon.dtos.CommentDto;
import com.mc.demo.ergon.models.Activity;
import com.mc.demo.ergon.models.Comment;
import com.mc.demo.ergon.models.User;
import com.mc.demo.ergon.repositories.ActivityRepository;
import com.mc.demo.ergon.repositories.CommentRepository;
import com.mc.demo.ergon.repositories.UserRepository;

@Service
public class CommentService {
    private CommentRepository commentRepo;
    private UserRepository userRepo;
    private ActivityRepository activityRepo;

    public CommentService(CommentRepository commentRepo, UserRepository userRepo, ActivityRepository activityRepo) {
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
        this.activityRepo = activityRepo;
    }

    public ArrayList<CommentDto> GetCommentsByActivityId(Long id) {
        Iterable<Comment> comments = commentRepo.findAllByActivityIdOrderByCreationDateDesc(id);

        ArrayList<CommentDto> ret = new ArrayList<CommentDto>();

        comments.forEach(comment -> ret.add(
            new CommentDto(
                comment.getId(),
                Optional.ofNullable(comment.getUser()).map(x -> x.getId()).orElse((long) 0),
                Optional.ofNullable(comment.getActivity()).map(x -> x.getId()).orElse((long) 0),
                comment.getContent(),
                comment.getCreationDate()
            ))
        );

        return ret;
    }

    public CommentDto GetCommentById(Long id) {
        Optional<Comment> optComment = commentRepo.findById(id);

        if (optComment.isEmpty())
            return null;

        Comment comment = optComment.get();

        return new CommentDto(
                comment.getId(),
                Optional.ofNullable(comment.getUser()).map(x -> x.getId()).orElse((long) 0),
                Optional.ofNullable(comment.getActivity()).map(x -> x.getId()).orElse((long) 0),
                comment.getContent(),
                comment.getCreationDate());
    }

    public boolean AddComment(CommentDto newComment) {
        Optional<User> optUser = userRepo.findById(newComment.getUserId());

        if (optUser.isEmpty())
            return false;

        Optional<Activity> optActivity = activityRepo.findById(newComment.getActivityId());

        if (optActivity.isEmpty())
            return false;

        User user = optUser.get();
        Activity activity = optActivity.get();

        // User user, Activity activity, String content, Date creationDate
        Comment createdComment = commentRepo.save(
                new Comment(
                        user,
                        activity,
                        newComment.getContent(),
                        new java.sql.Date(new Date().getTime())));

        return createdComment.getId() != null;
    }

    public boolean DeleteComment(Long id) {
        Optional<Comment> optComment = commentRepo.findById(id);

        if (optComment.isEmpty())
            return false;

        Comment comment = optComment.get();

        try {
            commentRepo.delete(comment);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean UpdateComment(Long id, CommentDto newComment) {
        Optional<Comment> optComment = commentRepo.findById(id);

        if (optComment.isEmpty())
            return false;

        Comment comment = optComment.get();

        Optional.ofNullable(newComment.getContent()).ifPresent(content -> comment.setContent(content));

        Comment updatedComment = commentRepo.save(comment);

        return updatedComment.getId() != null;
    }
}
