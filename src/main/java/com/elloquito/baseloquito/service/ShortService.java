
package com.elloquito.baseloquito.service;

import com.elloquito.baseloquito.model.Short;
import com.elloquito.baseloquito.model.Usuario;
import com.elloquito.baseloquito.repository.ShortRepository;

import jakarta.persistence.EntityManager;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ShortService {

    private final ShortRepository shortRepository;
    private final EntityManager entityManager;

    public ShortService(
            ShortRepository shortRepository,
            EntityManager entityManager) {

        this.shortRepository = shortRepository;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<Short> obtenerShorts() {
        return shortRepository.findAll();
    }

    @Transactional
    public Short crearShort(
            String titulo,
            Integer vistas,
            Integer likes,
            Integer idUsuario) {

        Usuario usuario = entityManager.find(
                Usuario.class,
                idUsuario
        );

        if (usuario == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El usuario no existe"
            );
        }

        Short shortNuevo = new Short();
        shortNuevo.setTitulo(titulo);
        shortNuevo.setVistas(vistas);
        shortNuevo.setLikes(likes);
        shortNuevo.setUsuario(usuario);

        return shortRepository.save(shortNuevo);
    }
}
