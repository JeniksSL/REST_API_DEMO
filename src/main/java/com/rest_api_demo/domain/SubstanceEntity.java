package com.manager.api.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "substance")
@NoArgsConstructor
@Getter
@Setter
public class SubstanceEntity {

    @Builder
    public SubstanceEntity(String id, String name, Integer color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }


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