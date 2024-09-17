package org.blazejherzog.jobmatcher.domain.job;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class JobOfferDurationTest {

    @ParameterizedTest
    @CsvSource({
            "45, 45d",
            "125, 125d",
            "0, 0d",
            "-1, 0d",
            "2147483647, 2147483647d"
    })
    public void shouldReturnCorrectStringValue(int value, String expected) {
        JobOfferDuration duration = JobOfferDuration.ofDays(value);
        assertThat(duration.toString()).isEqualTo(expected);
    }
}