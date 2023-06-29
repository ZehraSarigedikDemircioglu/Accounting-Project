package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    List<Role>findAll();
}
