package com.docker.msvc_usuarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.docker.msvc_usuarios.Entity.Usuario;
@Repository 
public interface UsuarioRepositry extends JpaRepository<Usuario, Long> {



}
