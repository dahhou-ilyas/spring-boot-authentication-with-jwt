package com.learn.learn.secr.secr.service;

import com.learn.learn.secr.entities.AppRoles;
import com.learn.learn.secr.entities.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    AppRoles addNewRole(AppRoles appRoles);
    void addRoleToUser(String username,String roleName);
    AppUser loadUserByUsername(String username);
    List<AppUser> ListUser();

    List<AppRoles> findRoleByIdUsername(Long id);

}
