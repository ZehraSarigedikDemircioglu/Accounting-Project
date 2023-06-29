package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByIsDeleted(boolean isDeleted);

    User findByUsername(String username);

    User findByIdAndIsDeleted(Long id,Boolean isDeleted);
}
