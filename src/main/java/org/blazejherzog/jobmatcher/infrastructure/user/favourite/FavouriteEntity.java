package org.blazejherzog.jobmatcher.infrastructure.user.favourite;

import org.blazejherzog.jobmatcher.domain.user.UserId;
import org.blazejherzog.jobmatcher.infrastructure.job.entity.JobOfferEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.annotations.SQLRestriction;

import java.time.Clock;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "OBSERVED_OFFERS")
@SQLRestriction("deleted <> true")
public class FavouriteEntity {

    private static final String SEQ_NAME = "OBSERVED_OFFERS_ID_SEQ";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private UserId userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_offer_id", nullable = false)
    private JobOfferEntity jobOffer;

    @Column(name = "add_date", nullable = false)
    private LocalDateTime addDate;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    FavouriteEntity() {
    }

    FavouriteEntity(@NonNull UserId userId, @NonNull JobOfferEntity jobOffer, @NonNull Clock clock) {
        this.userId = userId;
        this.jobOffer = jobOffer;
        this.addDate = LocalDateTime.now(clock);
        this.deleted = false;
    }

    public void delete() {
        this.deleted = true;
    }
}
