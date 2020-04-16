package com.example.demographql.repository;

import com.example.demographql.model.JwtUser;
import org.springframework.data.mongodb.repository.MongoRepository;

//import com.graphqlspringdemo.pojo.User;


public interface UserRepository extends MongoRepository<JwtUser, String> {
    JwtUser findByUserName(String userName);
}