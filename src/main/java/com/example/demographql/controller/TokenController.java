package com.example.demographql.controller;

import com.example.demographql.model.JwtUser;
import com.example.demographql.model.LoginCred;
import com.example.demographql.repository.UserRepository;
import com.example.demographql.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class TokenController {

  private final UserRepository userRepository;
  private JwtGenerator jwtGenerator;

  public TokenController(UserRepository userRepository,
      JwtGenerator jwtGenerator) {
    this.userRepository = userRepository;
    this.jwtGenerator = jwtGenerator;
  }

  @PostMapping
  public String generate(@RequestBody LoginCred loginCred) {

//    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginCred.getUserName(),loginCred.getPassword()));
    JwtUser user = userRepository.findByUserName(loginCred.getUserName());
    if (user != null && user.getPassword().equals(loginCred.getPassword())) {
      JwtUser jwtUser = new JwtUser(loginCred.getUserName(), loginCred.getPassword());
      System.out.println("JwtUserName: "+jwtUser.getUserName());
      return jwtGenerator.generate(jwtUser);

    }
    return null;
  }

}
