package org.blazejherzog.jobmatcher.domain.job;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "ofDays")
public class JobOfferDuration {

    private final Integer value;

    @Override
    public String toString() {
        if (value == null || value < 0) return "0d";
        return String.format("%dd", value);
    }
}
