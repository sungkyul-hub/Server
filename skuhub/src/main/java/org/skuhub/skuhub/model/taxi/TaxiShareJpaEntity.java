package org.skuhub.skuhub.model.taxi;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.skuhub.skuhub.model.BaseTime;
import org.skuhub.skuhub.model.sample.SampleJpaEntity;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "TAXI_SHARE_TB")
public class TaxiShareJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_key", nullable = false)
    private SampleJpaEntity userKey;

    @Size(max = 20)
    @NotNull
    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @Size(max = 20)
    @NotNull
    @Column(name = "departure_location", nullable = false, length = 20)
    private String departureLocation;

    @NotNull
    @Column(name = "ride_time", nullable = false)
    private Instant rideTime;

    @NotNull
    @Column(name = "number_of_people", nullable = false)
    private Integer numberOfPeople;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "head_count", nullable = false)
    private Integer headCount;

    @Size(max = 200)
    @NotNull
    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private java.time.OffsetDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private java.time.OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "post")
    private Set<TaxiCommentJpaEntity> commentTbs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "post")
    private Set<TaxiJoinJpaEntity> taxiJoinTbs = new LinkedHashSet<>();

}
