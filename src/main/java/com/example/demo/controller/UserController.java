package com.example.demo.controller;

import com.example.demo.model.AuthenticationRequest;
import com.example.demo.model.AuthenticationResponse;
import com.example.demo.model.Userinfo;
import com.example.demo.service.MyUserDetailsService;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;


    @GetMapping(value="/api/user")
    public List<Userinfo> getUser() {
    return userService.getUser();
    }
    @PostMapping(value="api/register")
    public ResponseEntity<?> register(@RequestBody Userinfo userinfo)
    {
        Optional<Userinfo> checkUser= Optional.ofNullable(userService.findByUsername(userinfo.getUsername()));
        if(!checkUser.isPresent())
        {
            if(userService.isValidUserPassFormat(userinfo))
            {
                userService.register(userinfo);
                return ResponseEntity.status(HttpStatus.CREATED).body("user registered successfully");
            }
           else
            {
                return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body("username and password length should be more than 4");
            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("username already exists");
        }
    }


    @PostMapping(value="/api/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
    {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));

        }
        catch(BadCredentialsException e)
        {
            throw new Exception("incorrect username or password",e);
        }

        try{
            final UserDetails userDetails = myUserDetailsService.loadUserByUsername(
                    authenticationRequest.getUsername());
            final String jwt=jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        }
        catch (Exception e)
        {
            throw new Exception("Unable to generate token with error:", e);
        }


    }

    @PostMapping(value="api/logingdhty")
    public ResponseEntity<?> login(@RequestBody Userinfo userinfo)
    {
        Userinfo newUser=userService.findByUsername(userinfo.username);
        if(newUser.getPassword().equals(userinfo.getPassword())) {
            return new ResponseEntity<>( HttpStatus.OK);
        }
        else
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }


}

