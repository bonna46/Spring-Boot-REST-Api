package com.example.demo.service;

import com.example.demo.model.Userinfo;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    List<Userinfo> user=new ArrayList<>(
           Arrays.asList(new Userinfo("sa","pp"),
                   new Userinfo("de","rrp")
           )
    );
    public Userinfo findByUsername(String name)
    {
       return userRepository.findByUsername(name);

    }

    public boolean isValidUserPassFormat(Userinfo newUser)
    {
        if(newUser.getUsername().trim().length()>=5 && newUser.getPassword().trim().length()>=5)
            return true;
        else
            return false;
    }
    public List<Userinfo> getUser()
    {
        List<Userinfo> userList= new ArrayList<>();
        userRepository.findAll()
                .forEach(userList::add);
        return userList;
    }

    public void register(Userinfo newUser)
    {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
    }

}
