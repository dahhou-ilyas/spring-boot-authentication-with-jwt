package com.learn.learn.secr;

import com.learn.learn.secr.entities.AppUser;
import com.learn.learn.secr.secr.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
@Service
public class SecurityConfig implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.csrf().disable(); //--> il faut la désactivé car on a utilisé authontification statless
        http.headers().frameOptions().disable();
        http.formLogin(); //--> la fomulaire il na pas utile dans authontification statless (il faut utiliser la formulair a coté front end)
        http.authorizeHttpRequests().anyRequest().authenticated();
        return http.build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser=accountService.loadUserByUsername(username);
        Collection<GrantedAuthority> authorities=new ArrayList<>();
        appUser.getAppRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });
        User user=new User(appUser.getUsername(),appUser.getPassword(),authorities);
        return user;
    }
}
