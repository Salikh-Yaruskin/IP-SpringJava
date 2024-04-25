package com.example.demo.speaker.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.speaker.domain.Speaker;
import com.example.demo.speaker.domain.SpeakerEng;
import com.example.demo.speaker.domain.SpeakerRus;

@Configuration
public class SpeakerConfiguration {
    private final Logger log = LoggerFactory.getLogger(SpeakerConfiguration.class);

    @Bean(value = "ru", initMethod = "init", destroyMethod = "destroy")
    public SpeakerRus createRusSpeaker() {
        log.info("Call createRusSpeaker()");
        return new SpeakerRus();
    }

    @Bean(value = "en")
    public Speaker createEngSpeaker() {
        log.info("Call createEngSpeaker()");
        return new SpeakerEng();
    }
}
