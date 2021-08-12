package com.example.demo.service;

import com.example.demo.model.Story;
import com.example.demo.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;
    List<Story> stories= new ArrayList<>(
            Arrays.asList(
            new Story("oi","aa","kk"),
            new Story("oi","bb","ll"),
            new Story("oi","cc","pp")
    ));
    public List<Story> getAllStories()
    {
        List<Story> storyList =new ArrayList<>();
        storyRepository.findAll()
                .forEach(storyList::add);
        return storyList;
    }

    public Optional<Story> getOneStory(Integer id) {
        Optional<Story> oneStory= storyRepository.findById(id);
       // Story oneStory=stories.stream().filter(t -> t.getTitle().equals(title)).findFirst().get();
        return oneStory;
    }

    public void postStory(Story newStory)
    {
        storyRepository.save(newStory);
    }

    public void deleteStory(Integer id) {
        storyRepository.deleteById(id);

    }

    public String updateAndSaveStory(Integer id, Story newStory)
    {

        Optional<Story> storyWithId=storyRepository.findById(id);
        if(newStory.getAuthor()==null)
            newStory.setAuthor(storyWithId.get().getAuthor());
        if(newStory.getTitle()==null)
            newStory.setTitle(storyWithId.get().getTitle());
        if(newStory.getStory()==null)
            newStory.setStory(storyWithId.get().getStory());

        storyRepository.updateStory(id,newStory.getAuthor(), newStory.getTitle(), newStory.getStory());
        return "ok";
    }




}

