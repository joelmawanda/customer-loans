package com.example.demo.repositories;


import com.example.demo.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByName(String username);

    @Modifying
    @Transactional
    @Query("DELETE from users u where u.name=:name")
    public int deleteUser(@Param("name") String name);
}
