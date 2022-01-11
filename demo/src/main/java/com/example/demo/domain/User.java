package com.example.demo.domain;

import com.example.demo.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity<Long> {

    private static final String USER_ROLE="user_role";
    private String firstName;
    private String username;
    private String password;

    @ManyToMany
    @JoinTable(name =USER_ROLE,joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roleList=new ArrayList<>();
}
