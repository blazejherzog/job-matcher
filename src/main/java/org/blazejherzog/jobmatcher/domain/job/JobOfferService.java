package org.blazejherzog.jobmatcher.domain.job;

import org.springframework.data.domain.Page;

public interface JobOfferService {

    Page<JobOffer> getAllJobOffers(Integer page);

    JobOfferDetails getJobOffer(Long jobOfferId);
}
