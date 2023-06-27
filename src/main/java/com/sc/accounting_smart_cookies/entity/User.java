package com.sc.accounting_smart_cookies.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    private Long id;
    @Column(unique = true)
    private String userName;
    private String password;
    private String lastName;
    private String phone;
    private boolean enabled;
    @ManyToOne
    private Role role;
//    @ManyToOne
//    private Company company;
}
