package tech.itpark.roben.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.itpark.roben.domain.Media;
import tech.itpark.roben.manager.MediaManager;


@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaController {
    private final MediaManager manager;

    @PostMapping
    public Media save(@RequestParam MultipartFile file) { // пришлёт multipart file
        return manager.save(file);
    }
}
