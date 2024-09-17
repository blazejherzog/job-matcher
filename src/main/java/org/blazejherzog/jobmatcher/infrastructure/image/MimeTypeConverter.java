package org.blazejherzog.jobmatcher.infrastructure.image;

import jakarta.persistence.AttributeConverter;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

public class MimeTypeConverter implements AttributeConverter<MimeType, String> {

    @Override
    public String convertToDatabaseColumn(MimeType mimeType) {
        return mimeType.toString();
    }

    @Override
    public MimeType convertToEntityAttribute(String mimeType) {
        return MimeTypeUtils.parseMimeType(mimeType);
    }
}
