package org.skuhub.skuhub.model.timetable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.skuhub.skuhub.model.BaseTime;

@Getter
@Setter
@Entity
@Table(name = "USER_INFO_TB")
public class UserInfoTb extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_key", nullable = false)
    private Integer userKey;
}
