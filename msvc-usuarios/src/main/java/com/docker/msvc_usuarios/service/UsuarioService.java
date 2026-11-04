package com.docker.msvc_usuarios.service;

import java.util.List;
import java.util.Optional;

import com.docker.msvc_usuarios.Entity.Usuario;

public interface UsuarioService {

    List<Usuario> getAllUsuarios();
    Optional<Usuario> getUsuarioById(Long id);
    Usuario saveUsuario(Usuario usuario);
    void deleteUsuario(Long id);

}
