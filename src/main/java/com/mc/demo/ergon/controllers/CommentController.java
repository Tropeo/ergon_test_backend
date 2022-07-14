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
import org.springframework.web.bind.annotation.RestController;

import com.mc.demo.ergon.dtos.CommentDto;
import com.mc.demo.ergon.services.CommentService;
import com.mc.demo.ergon.dtos.UserDto;

@RestController
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/activity/{activityId}/comment")
	public ArrayList<CommentDto> getAllByActivity(@PathVariable("activityId") Long id) {
        return commentService.GetCommentsByActivityId(id);
	}

    @GetMapping("/comment/{id}")
	public CommentDto getSingle(@PathVariable("id") Long id) {
        return commentService.GetCommentById(id);
	}

    @PostMapping("/comment")
	public boolean post(@RequestBody CommentDto comment) {
        if(comment.getUserId() == null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            comment.setUserId(((UserDto) authentication.getPrincipal()).getId());
        }

        return commentService.AddComment(comment);
	}

    @PatchMapping("/comment/{id}")
	public boolean patch(@PathVariable("id") Long id, @RequestBody CommentDto comment) {
        return commentService.UpdateComment(id, comment);
	}

    @DeleteMapping("/comment/{id}")
	public boolean delete(@PathVariable("id") Long id) {
        return commentService.DeleteComment(id);
	}
}
