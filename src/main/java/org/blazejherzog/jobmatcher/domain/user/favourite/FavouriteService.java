package org.blazejherzog.jobmatcher.domain.user.favourite;

import org.blazejherzog.jobmatcher.domain.job.JobOffer;
import org.blazejherzog.jobmatcher.domain.user.UserId;
import org.springframework.data.domain.Page;

public interface FavouriteService {

    Page<JobOffer> getFavourites(UserId userId, Integer page);

    void addFavourite(UserId id, Long jobOfferId);

    void deleteFavourite(UserId id, Long jobOfferId);
}
