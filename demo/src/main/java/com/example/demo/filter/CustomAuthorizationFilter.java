package com.example.demo.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/login")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
               try {

                   Pattern pattern = Pattern.compile("\\b(\\S+)\\.(\\S+)\\.(\\S+)");
                   Matcher matcher = pattern.matcher(authorizationHeader);
                   String token = "";
                   if (matcher.find())
                           token = matcher.group();

                   Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                   JWTVerifier verifier= JWT.require(algorithm).build();
                   DecodedJWT decodedJWT=verifier.verify(token);
                   String username=decodedJWT.getSubject();
                   String[] roleList=decodedJWT.getClaim("roleList").asArray(String.class);
                   Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
                   Arrays.stream(roleList).forEach(role -> {
                       authorities.add(new SimpleGrantedAuthority(role));
                   });
                   UsernamePasswordAuthenticationToken authenticationToken=
                           new UsernamePasswordAuthenticationToken(username,null,authorities);

                   SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                   filterChain.doFilter(request,response);
               }catch (Exception e) {
                   log.error("error logging in : {}",e.getMessage());
                   response.setHeader("error",e.getMessage());
                   response.setStatus(HttpStatus.FORBIDDEN.value());
//                   response.sendError(HttpStatus.FORBIDDEN.value());
                   Map<String,String> error=new HashMap<>();
                   error.put("error_message",e.getMessage());

                   response.setContentType(APPLICATION_JSON_VALUE);

                   new ObjectMapper().writeValue(response.getOutputStream(),error);
               }
            }
            else {
                filterChain.doFilter(request,response);
            }
        }
    }
}
