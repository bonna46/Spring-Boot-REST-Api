package com.example.demo.controller;

import com.example.demo.model.Story;
import com.example.demo.repository.StoryRepository;
import com.example.demo.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class StoryController {

    @Autowired
    private StoryService service;

    @Autowired
    private StoryRepository storyRepository;

    @GetMapping("/api/stories")
    public List<Story> getAllStories()
    {
            return service.getAllStories();
    }

    @GetMapping("/api/stories/{id}")
    public Optional<Story> getOneStory(@PathVariable Integer id)
    {
        return service.getOneStory(id);
    }

    @PostMapping(value = "/api/stories")
    public void postStory(@RequestBody Story newStory)
    {
         service.postStory(newStory);
    }


    @PutMapping("/api/stories/{id}")
    public ResponseEntity<String> updateOneStory(@PathVariable Integer id,@RequestBody Story newStory )
    {
        Optional<Story> storyWithId=storyRepository.findById(id);
        if(storyWithId.isPresent())
        {
            String s=service.updateAndSaveStory(id, newStory);
            return ResponseEntity.status(HttpStatus.OK).body(s);
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no story with this id");

    }



    @DeleteMapping("/api/stories/{id}")
    public ResponseEntity<String> deleteOneStory(@PathVariable Integer id)
    {
        Optional<Story> storyWithId=storyRepository.findById(id);
        if(storyWithId.isPresent())
        {
            service.deleteStory(id);
            return ResponseEntity.status(HttpStatus.OK).body("story deleted");
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no story with this id");


    }
}
