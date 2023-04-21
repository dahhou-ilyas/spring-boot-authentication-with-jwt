package com.learn.learn.secr.repo;

import com.learn.learn.secr.entities.AppRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRoles,Long> {
    AppRoles findByRoleName(String roleName);
}
