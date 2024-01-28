package com.betheagent.betheagent.authorization.repository;


import com.betheagent.betheagent.authorization.entity.UserInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInstance, String> {
    @Query("select u from UserInstance u where u.username = ?1 or u.email = ?2")
    Optional<UserInstance> findByUsernameOrEmail(String username, String email);

    @Query("select (count(u) > 0) from UserInstance u where u.username = ?1 or u.email = ?2")
    boolean existsByUsernameOrEmail(String username, String email);


    Optional<UserInstance> findByUsername(String username);
}
