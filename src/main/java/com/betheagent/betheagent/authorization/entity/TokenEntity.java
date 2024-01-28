package com.betheagent.betheagent.authorization.entity;

import com.betheagent.betheagent.audit.BaseEntity;
import com.betheagent.betheagent.authorization.entity.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private Boolean isExpired;
    private Boolean isRevoked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInstance user;
}
