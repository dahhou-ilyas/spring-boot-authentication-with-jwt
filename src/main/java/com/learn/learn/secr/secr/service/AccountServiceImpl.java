package com.learn.learn.secr.secr.service;

import com.learn.learn.secr.entities.AppRoles;
import com.learn.learn.secr.entities.AppUser;
import com.learn.learn.secr.repo.AppRoleRepository;
import com.learn.learn.secr.repo.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {


    private AppRoleRepository appRoleRepository;
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;

    //injection des dependencies
    public AccountServiceImpl(AppRoleRepository appRoleRepository, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appRoleRepository = appRoleRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public AppUser addNewUser(AppUser appUser) {
        String pw= appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(pw));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRoles addNewRole(AppRoles appRoles) {
        return appRoleRepository.save(appRoles);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user=appUserRepository.findByUsername(username);
        AppRoles role=appRoleRepository.findByRoleName(roleName);
        user.getAppRoles().add(role);
    }

    @Override
    public AppUser loadUserByUsername(String username) {

        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> ListUser() {

        return appUserRepository.findAll();
    }

    @Override
    public List<AppRoles> findRoleByIdUsername(Long id) {
        AppUser user=appUserRepository.findById(id).get();

        return user.getAppRoles().stream().toList();
    }
}
