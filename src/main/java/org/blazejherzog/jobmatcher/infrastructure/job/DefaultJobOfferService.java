package org.blazejherzog.jobmatcher.infrastructure.job;

import com.github.slugify.Slugify;
import org.blazejherzog.jobmatcher.domain.job.*;
import org.blazejherzog.jobmatcher.infrastructure.job.entity.JobOfferEntity;
import org.blazejherzog.jobmatcher.infrastructure.job.entity.JobLocationEntity;
import org.blazejherzog.jobmatcher.infrastructure.image.ImageEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultJobOfferService implements JobOfferService {

    @Value("${jobmatcher.job-offers.page-size}")
    private int pageSize;

    private static final String SLUG_TEMPLATE = "%d,%s-%s";

    private final JobOfferRepository jobOfferRepository;
    private final Clock clock;

    @Override
    public Page<JobOffer> getAllJobOffers(@NonNull Integer page) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Direction.DESC, "addDate", "id");

        return jobOfferRepository.findAll(pageRequest)
                .map(this::toJobOffer);
    }

    private JobOffer toJobOffer(JobOfferEntity entity) {
        return JobOffer.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .companyLogo(createPoster(entity))
                .slug(createSlug(entity))
                .addDate(entity.getAddDate().toLocalDate())
                .build();
    }

    private String createSlug(JobOfferEntity entity) {
        Slugify slugify = Slugify.builder().build();
        return String.format(SLUG_TEMPLATE, entity.getId(), slugify.slugify(entity.getTitle()),
                entity.getAddDate().getSecond());
    }

    @Override
    public JobOfferDetails getJobOffer(@NonNull Long jobOfferId) {
        JobOfferEntity jobOffer = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(() -> new IllegalArgumentException("Job offer not found for id=" + jobOfferId));

        return JobOfferDetails.builder()
                .id(jobOffer.getId())
                .title(jobOffer.getTitle())
                .description(jobOffer.getDescription())
                .remainingDays(calculateRemainingDays(jobOffer.getDuration(), jobOffer.getAddDate()))
                .companyLogo(createPoster(jobOffer))
                .branch(jobOffer.getBranch().getName())
                .locations(getProductionCountries(jobOffer))
                .build();
    }

    private JobOfferDuration calculateRemainingDays(JobOfferDuration duration, LocalDateTime addDate) {
        long daysElapsed = ChronoUnit.DAYS.between(addDate.toLocalDate(), LocalDate.now(clock));
        long remainingDays = duration.getValue() - daysElapsed;
        remainingDays = Math.max(remainingDays, 0);
        return JobOfferDuration.ofDays((int) remainingDays);
    }

    private List<String> getProductionCountries(JobOfferEntity entity) {
        return entity.getJobLocations()
                .stream()
                .map(JobLocationEntity::getName)
                .sorted()
                .toList();
    }

    private CompanyLogo createPoster(JobOfferEntity entity) {
        ImageEntity image = entity.getCompanyLogo();
        return new CompanyLogo(image.getId(), image.getFilename());
    }
}
