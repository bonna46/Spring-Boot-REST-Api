package com.example.demo.repository;

import com.example.demo.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StoryRepository extends JpaRepository<Story, Integer> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Story s SET s.author=:author,s.title=:title,s.story=:story WHERE s.id=:id")
    void updateStory(@Param("id") Integer id, @Param("author") String author,@Param("title") String title,@Param("story") String story);


    @Query("SELECT u FROM Story u WHERE u.id= :val")
    Story selectStory(@Param("val") Integer val);
}
