package com.elloquito.baseloquito.service;

// Scheduler para actualizar el progreso automático de los videos.

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LikesAutomaticosScheduler {

    private final LikesAutomaticosService likesAutomaticosService;

    public LikesAutomaticosScheduler(
            LikesAutomaticosService likesAutomaticosService) {
        this.likesAutomaticosService = likesAutomaticosService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ejecutarAlIniciar() {
        procesarCrecimiento();
    }

    @Scheduled(cron = "0 * * * * *")
    public void ejecutarGeneracionDeLikes() {
        procesarCrecimiento();
    }

    private synchronized void procesarCrecimiento() {
        likesAutomaticosService.generarLikesPorMinuto();
    }
}
