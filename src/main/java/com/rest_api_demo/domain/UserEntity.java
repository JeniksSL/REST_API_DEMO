package com.rest_api_demo.domain;


import com.rest_api_demo.domain.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity<String> {

    @Id
    @Basic
    @Column(name = "id", nullable = false)
    private String email;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "roles",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(table = "roles", name = "role", nullable = false, length = 30)
    private Set<RoleType> roles;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<ProductEntity> products;


    @Override
    public String getId() {
        return email;
    }

    @Override
    public void setId(String email) {
        this.email = email;
    }
}