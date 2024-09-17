package org.blazejherzog.jobmatcher.infrastructure.user.favourite;

import com.github.slugify.Slugify;
import org.blazejherzog.jobmatcher.domain.job.JobOffer;
import org.blazejherzog.jobmatcher.domain.job.CompanyLogo;
import org.blazejherzog.jobmatcher.domain.user.UserId;
import org.blazejherzog.jobmatcher.domain.user.favourite.FavouriteService;
import org.blazejherzog.jobmatcher.infrastructure.job.JobOfferRepository;
import org.blazejherzog.jobmatcher.infrastructure.job.entity.JobOfferEntity;
import org.blazejherzog.jobmatcher.infrastructure.image.ImageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultFavouriteService implements FavouriteService {

    private static final int PAGE_SIZE = 48;

    private static final String SLUG_TEMPLATE = "%d,%s-%s";

    private final FavouriteRepository favouriteRepository;

    private final JobOfferRepository jobOfferRepository;
    
    private final Clock clock;

    @Override
    public Page<JobOffer> getFavourites(UserId userId, Integer page) {
        PageRequest pageRequest = PageRequest.of(page - 1, PAGE_SIZE, Sort.Direction.DESC, "addDate", "id");

        return favouriteRepository.findAllByUserId(userId, pageRequest)
                .map(this::toJobOffer);
    }

    private JobOffer toJobOffer(FavouriteEntity favouriteEntity) {
        JobOfferEntity jobOfferEntity = favouriteEntity.getJobOffer();
        return JobOffer.builder()
                .id(jobOfferEntity.getId())
                .title(jobOfferEntity.getTitle())
                .companyLogo(createPoster(jobOfferEntity))
                .slug(createSlug(jobOfferEntity))
                .build();
    }

    private String createSlug(JobOfferEntity entity) {
        Slugify slugify = Slugify.builder().build();
        return String.format(SLUG_TEMPLATE, entity.getId(), slugify.slugify(entity.getTitle()),
                entity.getAddDate().getSecond());
    }

    private CompanyLogo createPoster(JobOfferEntity entity) {
        ImageEntity image = entity.getCompanyLogo();
        return new CompanyLogo(image.getId(), image.getFilename());
    }

    @Override
    public void addFavourite(UserId userId, Long jobOfferId) {
        if (!favouriteRepository.existsByUserIdAndJobOfferId(userId, jobOfferId)) {
            JobOfferEntity jobOffer = jobOfferRepository.findById(jobOfferId).orElseThrow(() -> new IllegalArgumentException("Job offer not found"));
            favouriteRepository.save(new FavouriteEntity(userId, jobOffer, clock));
        }
    }

    @Override
    @Transactional
    public void deleteFavourite(UserId userId, Long jobOfferId) {
        Optional<FavouriteEntity> optionalFavourite = favouriteRepository.findByUserIdAndJobOfferId(userId, jobOfferId);
        optionalFavourite.ifPresent(this::deleteFavourite);
    }

    private void deleteFavourite(FavouriteEntity entity) {
        entity.delete();
        favouriteRepository.save(entity);
    }
}
