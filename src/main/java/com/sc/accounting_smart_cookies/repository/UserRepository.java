package com.sc.accounting_smart_cookies.repository;

import com.sc.accounting_smart_cookies.entity.Company;
import com.sc.accounting_smart_cookies.entity.Role;
import com.sc.accounting_smart_cookies.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
@Query("SELECT u FROM User u WHERE u.company=?1 AND u.isDeleted=?2 ORDER BY u.role.description asc ")
    List<User> listAllUserWithCompanyAndIsDeleted(Company company, boolean isDeleted);

    @Query("SELECT u FROM User u WHERE u.role.description=?1")
    List<User>findAllAdminRole(String role);

    User findByUsername(String username);

    User findByIdAndIsDeleted(Long id,Boolean isDeleted);

    boolean existsByUsername(String username);

    @Query("SELECT COUNT(u) FROM User u WHERE u.company = ?1 AND u.role.id = 2")
    Integer isUserOnlyAdmin(Company company, Role role);

}
