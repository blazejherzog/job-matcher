package org.blazejherzog.jobmatcher.infrastructure.image;

import org.blazejherzog.jobmatcher.domain.image.Image;
import org.blazejherzog.jobmatcher.domain.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class DefaultImageService implements ImageService {

    private final ImageRepository repository;

    @Value("${jobmatcher.images.basePath}")
    private String imageBasePath;

    @Override
    public Image getImage(long imageId) {
        ImageEntity entity = repository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException(format("Image not found (imageId=%d)", imageId)));

        File imageFile = getImageFile(entity);

        return Image.builder()
                .length(imageFile.length())
                .mimeType(entity.getMimeType())
                .content(getContent(imageFile))
                .build();
    }

    private File getImageFile(ImageEntity entity) {
        File imageFile = new File(imageBasePath + File.separator + entity.getFilename());

        if (!imageFile.exists()) {
            throw new IllegalStateException(format("Image not found in the store (id=%s, filename=%s)",
                    entity.getId(), imageFile));
        }

        return imageFile;
    }

    private byte[] getContent(File imageFile) {
        try {
            return Files.readAllBytes(Paths.get(imageFile.getAbsolutePath()));
        } catch (IOException e) {
            throw new IllegalStateException(format("Exception occurred during reading image file content: %s", imageFile));
        }
    }
}
