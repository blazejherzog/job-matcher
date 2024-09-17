package org.blazejherzog.jobmatcher.domain.job;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;

@Builder
@Getter
public class JobOffer {

    @NonNull
    private final Long id;

    @NonNull
    private final String title;

    @NonNull
    private final LocalDate addDate;

    @NonNull
    private final CompanyLogo companyLogo;

    @NonNull
    private final String slug;
}
