package com.elloquito.baseloquito.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger =
            LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        long inicio = System.nanoTime();

        Runtime runtime = Runtime.getRuntime();
        long memoriaAntes = runtime.totalMemory()
                - runtime.freeMemory();

        try {
            filterChain.doFilter(request, response);
        } finally {

            long tiempoMs =
                    (System.nanoTime() - inicio) / 1_000_000;

            long memoriaDespues = runtime.totalMemory()
                    - runtime.freeMemory();

            logger.info(
                    "LABORATORIO | metodo={} | ruta={} | HTTP={} | tiempo={}ms | memoriaAntes={}KB | memoriaDespues={}KB",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    tiempoMs,
                    memoriaAntes / 1024,
                    memoriaDespues / 1024
            );
        }
    }
}
