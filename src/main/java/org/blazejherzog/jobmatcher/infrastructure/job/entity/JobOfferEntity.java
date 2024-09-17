package org.blazejherzog.jobmatcher.infrastructure.job.entity;

import org.blazejherzog.jobmatcher.domain.job.JobOfferDuration;
import org.blazejherzog.jobmatcher.infrastructure.image.ImageEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Table(name = "JOB_OFFERS")
@Builder
@AllArgsConstructor
public class JobOfferEntity {

    private static final String SEQ_NAME = "JOB_OFFERS_ID_SEQ";

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "DURATION", nullable = false)
    private JobOfferDuration duration;

    @OneToOne(optional = false)
    @JoinColumn(name = "IMAGE_ID", nullable = false, referencedColumnName = "ID")
    private ImageEntity companyLogo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "JOB_BRANCH_ID", nullable = false)
    private JobBranchEntity branch;

    @ManyToMany
    @JoinTable(
            name = "JOB_OFFERS_JOB_LOCATIONS",
            joinColumns = {@JoinColumn(name = "JOB_OFFER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "JOB_LOCATION_ID")}
    )
    private Set<JobLocationEntity> jobLocations;

    @Column(name = "ADD_DATE", nullable = false)
    private LocalDateTime addDate;

    JobOfferEntity() {
    }
}
