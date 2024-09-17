package org.blazejherzog.jobmatcher.domain.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
public class UserId {

    private static final UserId EMPTY = UserId.of(-1L);

    private final Long value;

    public static UserId empty() {
        return EMPTY;
    }

    public boolean isEmpty() {
        return equals(EMPTY);
    }
}
