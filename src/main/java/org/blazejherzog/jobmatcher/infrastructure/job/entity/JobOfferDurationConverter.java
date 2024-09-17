package org.blazejherzog.jobmatcher.infrastructure.job.entity;

import org.blazejherzog.jobmatcher.domain.job.JobOfferDuration;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class JobOfferDurationConverter implements AttributeConverter<JobOfferDuration, Integer> {

    @Override
    public Integer convertToDatabaseColumn(JobOfferDuration attribute) {
        return attribute.getValue();
    }

    @Override
    public JobOfferDuration convertToEntityAttribute(Integer duration) {
        return JobOfferDuration.ofDays(duration);
    }
}
