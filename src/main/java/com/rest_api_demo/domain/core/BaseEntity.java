package com.rest_api_demo.domain.core;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class BaseEntity<ID> {
    public abstract ID getId();
    public  abstract void setId(ID email);
}
