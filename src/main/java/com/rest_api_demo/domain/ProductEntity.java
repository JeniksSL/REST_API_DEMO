package com.manager.api.model;


import jakarta.persistence.*;
import lombok.*;
import java.util.Map;


@NoArgsConstructor
@ToString
@Entity
@Getter
@Setter
@Table(name = "product")
public class ProductEntity {

    @Builder
    public ProductEntity(Long id, String name, String fullName, String image, UserEntity user, Map<SubstanceEntity, Double> substanceMap) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.image = image;
        this.user = user;
        this.substanceMap = substanceMap;
    }




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Basic
    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Basic
    @Column(name = "image_url", nullable = true)
    private String image;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "composition", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    @MapKeyJoinColumn(name = "substance_id", referencedColumnName = "id")
    @Column(table ="composition", name = "content", nullable = false)
    private Map<SubstanceEntity, Double> substanceMap;






}