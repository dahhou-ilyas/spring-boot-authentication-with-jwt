package com.learn.learn.secr;

import com.learn.learn.secr.entities.AppUser;
import com.learn.learn.secr.filters.JwtAthenticationFilter;
import com.learn.learn.secr.secr.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
@Service
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
    private AccountService accountService;

   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!-->");
                System.out.println("!!!!!!!!!!!!!!!! Im here");

                AppUser appUser=accountService.loadUserByUsername(username);

                Collection<GrantedAuthority> authorities=new ArrayList<>();
                appUser.getAppRoles().forEach(r->{
                    authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
                });
                User user=new User(appUser.getUsername(),appUser.getPassword(),authorities);
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!-->");
                return user;
            }
        });


    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.headers().frameOptions().disable();
            http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
            http.authorizeRequests().anyRequest().authenticated();
            http.addFilter(new JwtAthenticationFilter(authenticationManagerBean()));
    }
    



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
