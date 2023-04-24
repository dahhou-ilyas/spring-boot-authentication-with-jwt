package com.learn.learn.secr.web;

import com.learn.learn.secr.entities.AppRoles;
import com.learn.learn.secr.entities.AppUser;
import com.learn.learn.secr.secr.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppRestController {
    private AccountService accountService;

    public AppRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/users")
    public List<AppUser> appUsers(){
        return accountService.ListUser();
    }


    @GetMapping(path = "user/{id}")
    public List<AppRoles> findRoleByUS(@PathVariable("id") Long id){
        return accountService.findRoleByIdUsername(id);
    }

    @PostMapping(path = "/users")
    public AppUser saveUser(@RequestBody AppUser appUser){
        return accountService.addNewUser(appUser);
    }


    @PostMapping(path = "/roles")
    public AppRoles saveRole(@RequestBody AppRoles appRoles){
        return accountService.addNewRole(appRoles);
    }



    record RoleToUser(String username,String roleName){}
    @PostMapping(path = "/addRoleToUser")
    public void addRoleTouser(@RequestBody RoleToUser roleToUser ){
        accountService.addRoleToUser(roleToUser.username(), roleToUser.roleName());
    }



}
