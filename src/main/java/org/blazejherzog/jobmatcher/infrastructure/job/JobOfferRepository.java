package org.blazejherzog.jobmatcher.infrastructure.job;

import org.blazejherzog.jobmatcher.infrastructure.job.entity.JobOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobOfferRepository extends JpaRepository<JobOfferEntity, Long> {
}
