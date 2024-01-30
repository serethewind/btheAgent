package com.betheagent.betheagent.properties.entity;

import com.betheagent.betheagent.authorization.entity.UserInstance;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Favourites {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserInstance user;
    @OneToOne
    @JoinColumn(name = "propertyId")
    private PropertyEntity property;
}
