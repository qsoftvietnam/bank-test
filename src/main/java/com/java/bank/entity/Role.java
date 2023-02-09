package com.java.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java.bank.type.RoleConstants;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@ToString
@Table(name = "tbl_role")
public class Role {
    @Id
    @GeneratedValue()
    @Column(name = "id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private RoleConstants roleName;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
