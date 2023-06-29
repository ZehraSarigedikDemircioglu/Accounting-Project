package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {

    List<Role>findAll();
}
