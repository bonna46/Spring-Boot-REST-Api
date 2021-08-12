package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name="STORY")
public class Story {

    @Id
    @GeneratedValue
    @Column
    public Integer id;
    @Column
    public String author;
    @Column
    public String title;
    @Column
    public String story;
    public Story(String author,String title,String story)
    {
        this.author=author;
        this.title=title;
        this.story=story;
    }
    public Story() {
    }

    public Integer getId()
    {
        return this.id;
    }
    public void setId(Integer id)
    {
        this.id=id;
    }

    public String getAuthor()
    {
        return this.author;
    }
    public void setAuthor(String author)
    {
        this.author=author;
    }

    public String getTitle()
    {
        return this.title;
    }
    public void setTitle(String title)
    {
        this.title=title;
    }

    public String getStory()
    {
        return this.story;
    }
    public void setStory(String story)
    {
        this.story=story;
    }


}

