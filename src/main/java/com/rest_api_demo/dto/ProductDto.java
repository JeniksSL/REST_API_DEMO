package com.rest_api_demo.dto;

import com.rest_api_demo.dto.core.BaseDto;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class ProductDto extends BaseDto {

    private Long id;

    private String name;

    private String fullName;

    private transient String userId;

    private String image;

    private Boolean isCommon;

    private Set<SubstanceCompact> substanceSet;

}
