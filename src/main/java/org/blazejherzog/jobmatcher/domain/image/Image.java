package org.blazejherzog.jobmatcher.domain.image;

import lombok.Builder;
import org.springframework.util.MimeType;

@Builder
public record Image(long length, MimeType mimeType, byte[] content) {
}
