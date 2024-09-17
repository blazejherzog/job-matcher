package org.blazejherzog.jobmatcher.controller;

import org.blazejherzog.jobmatcher.domain.job.JobOffer;
import org.blazejherzog.jobmatcher.domain.job.JobOfferDetails;
import org.blazejherzog.jobmatcher.domain.job.JobOfferService;
import org.blazejherzog.jobmatcher.web.PageView;
import org.blazejherzog.jobmatcher.web.ResponseView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static java.util.Objects.requireNonNullElse;

@Controller
@RequiredArgsConstructor
public class JobOfferController {

    private static final String DEFAULT_JOB_OFFERS_URL = "/jobs/offers/1";

    private static final int NO_PAGE_NUMBER = 0;

    private final JobOfferService jobOfferService;

    @GetMapping({"/jobs/offers/{page}", "/jobs/offers/", "/jobs/offers"})
    public ResponseView jobOffers(@PathVariable(name = "page", required = false) Integer pageNumber) {
        if (requireNonNullElse(pageNumber, NO_PAGE_NUMBER) <= NO_PAGE_NUMBER) {
            return ResponseView.redirect(DEFAULT_JOB_OFFERS_URL);
        }

        Page<JobOffer> page = jobOfferService.getAllJobOffers(pageNumber);

        if (!page.hasContent()) {
            return ResponseView.redirect(DEFAULT_JOB_OFFERS_URL);
        }

        return ResponseView.of("job/offers")
                .add("page", PageView.of(page));
    }

    @GetMapping("/jobs/offer/{slug}")
    public ResponseView jobOffer(@PathVariable(name = "slug") String slug) {
        JobOfferDetails jobOffer = jobOfferService.getJobOffer(getId(slug));
        return ResponseView.of("job/offer-details")
                .add("jobOffer", jobOffer);
    }

    public Long getId(String slug) {
        return Long.parseLong(slug.split(",")[0]);
    }
}
