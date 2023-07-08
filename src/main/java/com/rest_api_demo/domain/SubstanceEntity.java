package com.rest_api_demo.domain;

import com.rest_api_demo.domain.core.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "substances")
public class SubstanceEntity extends BaseEntity<String> {


    @Id
    @Column(name = "id", nullable = false, length = 25)
    private String id;

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Basic
    @Column(name = "color", nullable = false)
    private Integer color;

    @Basic
    @Column(name = "descript", nullable = true)
    private String description;

    @Basic
    @Column(name = "image_url", nullable = true)
    private String image;


}