package org.skuhub.skuhub.model.taxi;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.skuhub.skuhub.model.BaseTime;
import org.skuhub.skuhub.model.sample.SampleJpaEntity;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "TAXI_JOIN_TB")
public class TaxiJoinJpaEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private TaxiShareJpaEntity post;

    @Id
    @Column(name = "join_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_key", nullable = false)
    private SampleJpaEntity userKey;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private java.time.OffsetDateTime createdAt;

}
