package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "roleList")
    User findUserByUsername(String userName);
}
