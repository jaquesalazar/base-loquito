package com.elloquito.baseloquito.service;

import com.elloquito.baseloquito.model.Usuario;
import com.elloquito.baseloquito.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void obtenerUsuariosDebeDevolverLaListaDeUsuarios() {
        Usuario usuario = new Usuario();
        usuario.setUsername("el_loquito");
        usuario.setSeguidores(1200);
        usuario.setDinero(500);
        usuario.setCohesion(80);

        given(usuarioRepository.findAll()).willReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.obtenerUsuarios();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getUsername()).isEqualTo("el_loquito");
        verify(usuarioRepository).findAll();
    }

    @Test
    void crearUsuarioDebeGuardarYDevolverElUsuario() {
        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setUsername("el_loquito");
        usuarioGuardado.setSeguidores(1200);
        usuarioGuardado.setDinero(500);
        usuarioGuardado.setCohesion(80);

        given(usuarioRepository.save(org.mockito.ArgumentMatchers.any(Usuario.class)))
                .willReturn(usuarioGuardado);

        Usuario resultado = usuarioService.crearUsuario(
                "el_loquito",
                1200,
                500,
                80
        );

        assertThat(resultado.getUsername()).isEqualTo("el_loquito");
        assertThat(resultado.getSeguidores()).isEqualTo(1200);
        assertThat(resultado.getDinero()).isEqualTo(500);
        assertThat(resultado.getCohesion()).isEqualTo(80);
        verify(usuarioRepository).save(org.mockito.ArgumentMatchers.any(Usuario.class));
    }
}
