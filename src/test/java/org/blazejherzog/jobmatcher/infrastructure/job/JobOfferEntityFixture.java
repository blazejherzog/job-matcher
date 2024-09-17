package org.blazejherzog.jobmatcher.infrastructure.job;

import org.blazejherzog.jobmatcher.domain.job.JobOfferDuration;
import org.blazejherzog.jobmatcher.infrastructure.image.ImageEntity;
import org.blazejherzog.jobmatcher.infrastructure.job.entity.JobBranchEntity;
import org.blazejherzog.jobmatcher.infrastructure.job.entity.JobLocationEntity;
import org.blazejherzog.jobmatcher.infrastructure.job.entity.JobOfferEntity;

import java.time.LocalDateTime;
import java.util.Set;

public class JobOfferEntityFixture {

    public static final long JOB_OFFER_ID = 123L;
    public static final String TITLE = "Job Offer Title";
    public static final String DESCRIPTION = "Test Description";
    public static final JobOfferDuration DURATION = JobOfferDuration.ofDays(20);
    public static final String LOGO_FILENAME = "company_logo.jpg";
    public static final long LOGO_ID = 1L;

    private JobOfferEntityFixture() {
    }

    static JobOfferEntity createJobOfferEntity() {
        return JobOfferEntity.builder()
                .id(JOB_OFFER_ID)
                .title(TITLE)
                .description(DESCRIPTION)
                .duration(DURATION)
                .companyLogo(createPoster(LOGO_ID))
                .jobLocations(Set.of(
                        createLocation("USA"),
                        createLocation("Poland"),
                        createLocation("Argentina")
                ))
                .branch(createBranch("IT"))
                .addDate(LocalDateTime.of(2024, 9, 11, 8, 10))
                .build();
    }

    private static JobBranchEntity createBranch(String genre) {
        return JobBranchEntity.builder()
                .name(genre)
                .build();
    }

    private static JobLocationEntity createLocation(String location) {
        return JobLocationEntity.builder()
                .name(location)
                .build();
    }

    private static ImageEntity createPoster(Long posterId) {
        return ImageEntity.builder()
                .id(posterId)
                .filename(LOGO_FILENAME)
                .build();
    }
}
