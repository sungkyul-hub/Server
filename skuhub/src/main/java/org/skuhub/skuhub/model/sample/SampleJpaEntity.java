package org.skuhub.skuhub.model.sample;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.skuhub.skuhub.model.BaseTime;

@Entity
@Getter
@Setter
@Table(name = "USER_INFO_TB")
public class SampleJpaEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_key")
    private int userKey;  // 'user_key' 컬럼에 맞춰 AUTO_INCREMENT

    @Column(name = "user_id", nullable = false, length = 20, unique = true)
    private String userId;  // 'user_id' 컬럼에 맞춤

    @Column(name = "email", nullable = false, length = 100)
    private String email;  // 'email' 컬럼에 맞춤

    @Column(name = "password", nullable = false, length = 20)
    private String password;  // 'password' 컬럼에 맞춤

    @Column(name = "year", nullable = false)
    private int year;  // 'year' 컬럼에 맞춤

    @Column(name = "department", nullable = false, length = 20)
    private String department;  // 'department' 컬럼에 맞춤

    @Column(name = "name", nullable = false, length = 20, unique = true)
    private String name;  // 'name' 컬럼에 맞춤, 유니크 제약 추가

    @Column(name = "created_at", nullable = false)
    private java.time.OffsetDateTime createdAt;  // 'created_at' 컬럼에 맞춤

    @Column(name = "withdrawal_date", nullable = false)
    private java.time.OffsetDateTime withdrawalDate;  // 'withdrawal_date' 컬럼에 맞춤
}
