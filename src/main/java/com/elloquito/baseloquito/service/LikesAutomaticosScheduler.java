package com.elloquito.baseloquito.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LikesAutomaticosScheduler {

    private final LikesAutomaticosService likesAutomaticosService;

    public LikesAutomaticosScheduler(
            LikesAutomaticosService likesAutomaticosService) {
        this.likesAutomaticosService = likesAutomaticosService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void ejecutarGeneracionDeLikes() {
        likesAutomaticosService.generarLikesPorMinuto();
    }
}
