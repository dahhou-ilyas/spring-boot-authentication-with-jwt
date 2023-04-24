package com.learn.learn.secr.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtAthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    public JwtAthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("test !!!!!!!!!!!!!!!!!!!!!");
        String username =request.getParameter("username");
        String password=request.getParameter("password");
        System.out.println(username);
        System.out.println(password);
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
        System.out.println(authenticationToken);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        System.out.println("!!!!!!!!!!!!!!!!!!!!! succé");
        User user =(User) authResult.getPrincipal();
        Algorithm algorithm=Algorithm.HMAC256("akdpcv1258");
        String jwtAccessToke = JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                                .withClaim("roles",user.getAuthorities().stream().map(ga->ga.getAuthority()).collect(Collectors.toList()))
                                        .sign(algorithm);
        response.setHeader("Authorization",jwtAccessToke);
    }
}
