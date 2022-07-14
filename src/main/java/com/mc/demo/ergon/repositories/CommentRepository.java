package com.mc.demo.ergon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mc.demo.ergon.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Iterable<Comment> findAllByActivityIdOrderByCreationDateDesc(Long id);
    Iterable<Comment> findAllByIdOrderByCreationDateDesc(Long id);
}