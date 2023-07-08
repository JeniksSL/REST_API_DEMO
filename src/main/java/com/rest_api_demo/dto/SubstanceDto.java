package com.rest_api_demo.dto;

import com.rest_api_demo.dto.core.BaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class SubstanceDto extends BaseDto {

    @NotBlank
    private String id;
    @NotBlank
    private String name;
    @NonNull
    private Integer color;

    private String description;

    private String image;

}
