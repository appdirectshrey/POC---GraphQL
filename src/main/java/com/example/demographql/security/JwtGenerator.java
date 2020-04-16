package com.example.demographql.security;

import com.example.demographql.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {

  public String generate(JwtUser jwtUser){
    Claims claims = Jwts.claims()
        .setSubject(jwtUser.getUserName());
    claims.put("password",String.valueOf(jwtUser.getPassword()));

    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, "Graphql")
        .compact();
  }

}
