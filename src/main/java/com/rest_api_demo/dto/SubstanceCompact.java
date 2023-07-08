package com.rest_api_demo.dto;

import com.rest_api_demo.dto.core.CompactDto;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class SubstanceCompact extends CompactDto implements Comparable<SubstanceDto> {



    private String id;
private BigDecimal content;



    @Override
    public int compareTo(SubstanceDto o) {
        return id.compareTo(o.getId());
    }
}
