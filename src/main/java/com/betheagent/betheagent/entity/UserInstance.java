package com.betheagent.betheagent.entity;

import com.betheagent.betheagent.audit.BaseEntity;
import com.betheagent.betheagent.entity.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserInstance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String firstname;
    private String lastname;
    private String middlename;
    private String dateOfBirth;
    private String gender;
    private String email;
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Set<RoleType> roleTypes;
    private Boolean isEnabled;

}
