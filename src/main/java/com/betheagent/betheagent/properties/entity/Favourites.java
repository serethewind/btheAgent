package com.betheagent.betheagent.properties.entity;

import com.betheagent.betheagent.audit.BaseEntity;
import com.betheagent.betheagent.authorization.entity.UserInstance;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Favourites extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserInstance user;
    @ManyToOne
    @JoinColumn(name = "propertyId")
    private PropertyInstance property;
}
