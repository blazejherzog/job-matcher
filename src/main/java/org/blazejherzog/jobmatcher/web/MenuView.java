package org.blazejherzog.jobmatcher.web;

import com.google.common.collect.ImmutableMap;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
@EqualsAndHashCode
public class MenuView {

    private final String activeView;

    private static final Map<String, List<String>> PAGES_TO_VIEWS = ImmutableMap.of(
            "jobOffers", List.of("job/offers", "job/offer-details"),
            "home", List.of("home/home"),
            "panel", List.of("admin/panel"));

    private MenuView(@NonNull String activeView) {
        this.activeView = activeView;
    }

    public static MenuView of(@NonNull String activeView) {
        return new MenuView(activeView);
    }

    public boolean isActive(@NonNull String page) {
        List<String> views = PAGES_TO_VIEWS.get(page);
        return views.contains(activeView);
    }
}
