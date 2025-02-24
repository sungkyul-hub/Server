package org.skuhub.skuhub.model.timetable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USER_INFO_TB")
public class UserInfoTb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_key", nullable = false)
    private Integer userKey;
}
