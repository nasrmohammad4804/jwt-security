package com.example.demo.domain;

import com.example.demo.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Role extends BaseEntity<Long> {

    private String name;

    public Role(String name) {
        this.name = name;
    }
}
