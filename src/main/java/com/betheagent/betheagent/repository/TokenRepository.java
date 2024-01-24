package com.betheagent.betheagent.repository;

import com.betheagent.betheagent.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, String> {

    Optional<TokenEntity> findByToken(String token);

    @Query("select t from TokenEntity t where t.isExpired = false and t.isRevoked = false and t.user.id = ?1")
    List<TokenEntity> findAllValidTokensByUserId(String id);
}
