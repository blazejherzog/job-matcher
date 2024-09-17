package org.blazejherzog.jobmatcher.infrastructure.image;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.MimeType;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "IMAGES")
@Builder
@AllArgsConstructor
public class ImageEntity {

    private static final String SEQ_NAME = "IMAGES_ID_SEQ";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, allocationSize = 1)
    private long id;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "mime_type", nullable = false)
    @Convert(converter = MimeTypeConverter.class)
    private MimeType mimeType;

    @Column(name = "add_date", nullable = false)
    private LocalDateTime addDate;

    ImageEntity() {
    }
}
