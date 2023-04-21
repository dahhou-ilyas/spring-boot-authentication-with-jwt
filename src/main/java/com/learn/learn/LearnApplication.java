package com.learn.learn;

import com.learn.learn.secr.entities.AppRoles;
import com.learn.learn.secr.entities.AppUser;
import com.learn.learn.secr.secr.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class LearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnApplication.class, args);
	}


	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CommandLineRunner start(AccountService accountService){
		return args -> {
			accountService.addNewRole(new AppRoles(null,"ETUDIANT"));
			accountService.addNewRole(new AppRoles(null,"CORRDINATEUR"));
			accountService.addNewRole(new AppRoles(null,"ENSEINGNANT"));
			accountService.addNewRole(new AppRoles(null,"ENTREPRISE"));
			accountService.addNewRole(new AppRoles(null,"ADMIN"));

			accountService.addNewUser(new AppUser(null,"user1","1234",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"user2","1234",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"user3","1234",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"user4","1234",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"user5","1234",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"admine","1234",new ArrayList<>()));

			accountService.addRoleToUser("user1","ETUDIANT");
			accountService.addRoleToUser("user5","ETUDIANT");
			accountService.addRoleToUser("user2","CORRDINATEUR");
			accountService.addRoleToUser("user2","ENSEINGNANT");
			accountService.addRoleToUser("user3","ENSEINGNANT");
			accountService.addRoleToUser("user4","ENTREPRISE");
			accountService.addRoleToUser("admine","ADMIN");
		};
	}
}
