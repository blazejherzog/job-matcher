package org.blazejherzog.jobmatcher.infrastructure.job;

import org.blazejherzog.jobmatcher.domain.job.JobOfferDetails;
import org.blazejherzog.jobmatcher.domain.job.JobOfferDuration;
import org.blazejherzog.jobmatcher.domain.job.CompanyLogo;
import org.blazejherzog.jobmatcher.infrastructure.job.entity.JobOfferEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import static org.blazejherzog.jobmatcher.infrastructure.job.JobOfferEntityFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultJobOfferServiceTest {

    @Mock
    private JobOfferRepository jobOfferRepository;

    @InjectMocks
    private DefaultJobOfferService jobOfferService;

    @Mock
    private Clock clock;

    @BeforeEach
    void setUp() {
        Clock fixedClock = Clock.fixed(Instant.parse("2024-09-13T00:00:00Z"), ZoneId.systemDefault());
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
    }

    @Test
    void shouldReturnJobOfferDetailsInCorrectOrder() {
        // given
        JobOfferEntity jobOfferEntity = createJobOfferEntity();
        when(jobOfferRepository.findById(JOB_OFFER_ID)).thenReturn(Optional.of(jobOfferEntity));

        // when
        JobOfferDetails result = jobOfferService.getJobOffer(JOB_OFFER_ID);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(JOB_OFFER_ID);
        assertThat(result.getTitle()).isEqualTo(jobOfferEntity.getTitle());
        assertThat(result.getDescription()).isEqualTo(jobOfferEntity.getDescription());
        assertThat(result.getRemainingDays()).isEqualTo(JobOfferDuration.ofDays(18));
        assertThat(result.getCompanyLogo()).isEqualTo(expectedPoster(jobOfferEntity));
        assertThat(result.getBranch()).isEqualTo("IT");
        assertThat(result.getLocations()).containsExactly("Argentina", "Poland", "USA");
    }

    private static CompanyLogo expectedPoster(JobOfferEntity jobOfferEntity) {
        return new CompanyLogo(jobOfferEntity.getCompanyLogo().getId(), jobOfferEntity.getCompanyLogo().getFilename());
    }
}