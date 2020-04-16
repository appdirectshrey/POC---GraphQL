package com.example.demographql.security;

import com.example.demographql.model.JwtUser;
import com.example.demographql.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

  private final UserRepository userRepository;

  @Autowired
  public JwtValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private String secret = "Graphql";


  public JwtUser validate(String token){

    JwtUser jwtUser = null;
    try {
      Claims body = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody();

      System.out.println("Claims Body: " + body.toString());
      jwtUser = new JwtUser();

      jwtUser.setUserName(body.getSubject());
      jwtUser.setPassword((String) body.get("userId"));

    } catch (Exception e){
      System.out.println("Exception in JwtValidator - validate() : "+e);
    }

    //hardcoded check for a particular user
    /*if(!"ABC".equalsIgnoreCase(jwtUser.getUserName()){
      return null;
    }*/

    //Fetching details for the token_userName from DB
    try {
      JwtUser searchUser = userRepository.findByUserName(jwtUser.getUserName());
      jwtUser.setRole(searchUser.getRole());
    } catch(NullPointerException e){
      System.out.println("Null Pointer Exception - incorrect Token!");
      jwtUser = null;
    }

    return jwtUser;
  }

}
