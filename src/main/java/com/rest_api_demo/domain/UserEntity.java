package com.manager.api.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "user_entity")
public class UserEntity{

    @Builder
    public UserEntity(String email, String password, List<RoleType> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Id
    @Basic
    @Column(name = "id", nullable = false)
    private String email;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @Column(table ="roles", name = "role", nullable = false, length = 30)
    private List<RoleType> roles;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<ProductEntity> products;

}