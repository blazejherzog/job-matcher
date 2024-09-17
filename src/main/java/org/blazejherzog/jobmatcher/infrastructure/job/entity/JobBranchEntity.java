package org.blazejherzog.jobmatcher.infrastructure.job.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Getter
@Table(name = "JOB_BRANCHES")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobBranchEntity {

    private static final String SEQ_NAME = "JOB_BRANCHES_ID_SEQ";

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToMany(mappedBy = "branch")
    private Set<JobOfferEntity> jobOffers;
}
