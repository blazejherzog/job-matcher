package org.blazejherzog.jobmatcher.domain.job;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Builder
@Getter
public class JobOfferDetails {

    @NonNull
    private final Long id;

    @NonNull
    private final String title;

    @NonNull
    private final String description;

    @NonNull
    private final JobOfferDuration remainingDays;

    @NonNull
    private final CompanyLogo companyLogo;

    @NonNull
    private final String branch;

    @NonNull
    private final List<String> locations;
}
