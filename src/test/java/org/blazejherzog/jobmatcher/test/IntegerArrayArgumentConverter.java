package org.blazejherzog.jobmatcher.test;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import java.util.Arrays;

public class IntegerArrayArgumentConverter extends SimpleArgumentConverter {

    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
        if (source instanceof String && Integer[].class.isAssignableFrom(targetType)) {
            return Arrays.stream(((String) source).split("\\s*,\\s*"))
                    .map(Integer::valueOf)
                    .toArray(Integer[]::new);
        }

        throw new IllegalArgumentException(String.format("Conversion from %s to %s not supported.",
                source.getClass(), targetType));
    }
}
