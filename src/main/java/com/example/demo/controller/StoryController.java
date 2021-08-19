package com.example.demo.controller;

import com.example.demo.model.Story;
import com.example.demo.repository.StoryRepository;
import com.example.demo.service.MyUserDetailsService;
import com.example.demo.service.StoryService;
import com.example.demo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class StoryController {

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

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
    public ResponseEntity<?> postOneStory(@RequestBody Story newStory, Authentication authentication)
    {
         if(newStory.getAuthor()!=null)
             return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Author name shouldn't be included.Please login first");
       else
         {
             if(service.isExistChar(newStory.getTitle()) && service.isExistChar(newStory.getStory()))
             {
                 String storyAuthor=authentication.getName();
                 //String storyAuthor=((this.myUserDetailsService)authentication.getPrincipal()).getName();
                 newStory.setAuthor(storyAuthor);
                 service.postStory(newStory);
                 return ResponseEntity.status(HttpStatus.CREATED).body("Story created successfully");
             }
             else
             {
                 return new ResponseEntity<>("GIVE val title and story",HttpStatus.LENGTH_REQUIRED);
                 //return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body("Give valid title and story");
             }

         }
    }


    @PutMapping("/api/stories/{id}")
    public ResponseEntity<String> updateOneStory(@PathVariable Integer id,@RequestBody Story newStory )
    {
        Optional<Story> storyWithId=storyRepository.findById(id);
        if(storyWithId.isPresent())
        {
                service.updateAndSaveStory(id, newStory);
                return ResponseEntity.status(HttpStatus.OK).body("story updated successfully");
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
