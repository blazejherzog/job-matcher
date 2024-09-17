package org.blazejherzog.jobmatcher.infrastructure.user.favourite;

import org.blazejherzog.jobmatcher.domain.user.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<FavouriteEntity, Long> {

    Page<FavouriteEntity> findAllByUserId(UserId userId, Pageable pageable);

    Optional<FavouriteEntity> findByUserIdAndJobOfferId(UserId userId, Long jobOfferId);

    boolean existsByUserIdAndJobOfferId(UserId userId, Long jobOfferId);
}
