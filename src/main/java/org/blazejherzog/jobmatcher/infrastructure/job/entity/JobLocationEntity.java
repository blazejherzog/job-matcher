package org.blazejherzog.jobmatcher.infrastructure.job.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Getter
@Table(name = "JOB_LOCATIONS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobLocationEntity {

    private static final String SEQ_NAME = "JOB_LOCATIONS_ID_SEQ";

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "jobLocations")
    private Set<JobOfferEntity> jobOffers;
}
