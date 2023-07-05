package com.sc.accounting_smart_cookies.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
//@Where(clause = "is_deleted=false")
public class Category extends BaseEntity{

    //@Column(unique = true)
    private String description;

    @ManyToOne
    private Company company;
}
