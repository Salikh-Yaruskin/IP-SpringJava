package com.example.demo.speaker.service;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.example.demo.speaker.domain.Speaker;

@Service
public class SpeakerService {
    private final ApplicationContext applicationContext;

    public SpeakerService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public String say(String name, String lang) {
        final Speaker speaker = (Speaker) applicationContext.getBean(lang);
        return String.format("%s, %s!", speaker.say(), name);
    }
}
