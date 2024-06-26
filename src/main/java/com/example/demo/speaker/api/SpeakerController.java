package com.example.demo.speaker.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.speaker.service.SpeakerService;

@RestController
public class SpeakerController {
    private final SpeakerService speakerService;

    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @GetMapping
    public String hello(
            @RequestParam(value = "name", defaultValue = "Мир") String name,
            @RequestParam(value = "lang", defaultValue = "ru") String lang) {
        return speakerService.say(name, lang);
    }
}
