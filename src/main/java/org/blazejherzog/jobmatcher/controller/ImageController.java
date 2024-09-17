package org.blazejherzog.jobmatcher.controller;

import org.blazejherzog.jobmatcher.domain.image.Image;
import org.blazejherzog.jobmatcher.domain.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.asMediaType;

@RestController
@RequestMapping(path = "/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping(path = "/{id}/{filename}")
    ResponseEntity<Resource> getImage(@PathVariable("id") Long imageId) {
        Image image = imageService.getImage(imageId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-store");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(image.length())
                .contentType(asMediaType(image.mimeType()))
                .body(new ByteArrayResource(image.content()));
    }
}
