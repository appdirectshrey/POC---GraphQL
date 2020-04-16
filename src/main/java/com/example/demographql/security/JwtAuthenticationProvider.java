package com.example.demographql.security;

import com.example.demographql.model.JwtAuthenticationToken;
import com.example.demographql.model.JwtUser;
import com.example.demographql.model.JwtUserDetails;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  @Autowired
  private JwtValidator validator;

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

  }

  @Override
  protected UserDetails retrieveUser(String username,
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;
    String token = jwtAuthenticationToken.getToken();
    JwtUser jwtUser = validator.validate(token);

    if (jwtUser == null) {
      throw new RuntimeException("JWT Token is incorrect");
    }

    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
        .commaSeparatedStringToAuthorityList(jwtUser.getRole());
    return new JwtUserDetails(jwtUser.getUserName(), jwtUser.getPassword(),
        token,
        grantedAuthorities);
  }

  /*@Override
  public boolean supports(Class<?> aClass) {
    return (JwtAuthenticationToken.class.isAssignableFrom(aClass));
  }*/
}
