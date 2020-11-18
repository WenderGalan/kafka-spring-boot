package io.github.wendergalan.streamingservice.resources;

import io.github.wendergalan.streamingservice.services.VideoStreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class VideoResource {
    private final VideoStreamService videoStreamService;

    @GetMapping("/video")
    public ResponseEntity<byte[]> watchVideo(@RequestHeader(value = "Range", required = false) String httpRangeList) {
        return videoStreamService.prepareContent("alo_ambev", "mp4", httpRangeList);
    }
}
