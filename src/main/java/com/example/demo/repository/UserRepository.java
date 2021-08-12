package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.Userinfo;

public interface UserRepository extends CrudRepository<Userinfo, Integer> {
     Userinfo findByUsername(String name);

}
