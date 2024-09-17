package org.blazejherzog.jobmatcher.controller.user;

import org.blazejherzog.jobmatcher.domain.job.JobOffer;
import org.blazejherzog.jobmatcher.domain.user.User;
import org.blazejherzog.jobmatcher.domain.user.favourite.FavouriteService;
import org.blazejherzog.jobmatcher.web.PageView;
import org.blazejherzog.jobmatcher.web.ResponseView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.util.Objects.requireNonNullElse;

@Controller
@RequestMapping(path = "/user/favourites")
@RequiredArgsConstructor
public class FavouriteController {

    private static final int NO_PAGE_NUMBER = 0;

    private static final String DEFAULT_FAVOURITES_URL = "/user/favourites/1";

    private final FavouriteService service;

    @GetMapping({"/{page}", "/", ""})
    public ResponseView getFavourites(@AuthenticationPrincipal User user, @PathVariable(name = "page", required = false) Integer pageNumber) {
        if (requireNonNullElse(pageNumber, NO_PAGE_NUMBER) <= NO_PAGE_NUMBER) {
            return ResponseView.redirect(DEFAULT_FAVOURITES_URL);
        }

        Page<JobOffer> page = service.getFavourites(user.getId(), pageNumber);

        return ResponseView.of("user/favourites")
                .add("page", PageView.of(page));
    }

    @PostMapping
    public ResponseEntity<Void> addFavourite(@AuthenticationPrincipal User user, Long jobOfferId) {
        service.addFavourite(user.getId(), jobOfferId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFavourite(@AuthenticationPrincipal User user, Long jobOfferId) {
        service.deleteFavourite(user.getId(), jobOfferId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
