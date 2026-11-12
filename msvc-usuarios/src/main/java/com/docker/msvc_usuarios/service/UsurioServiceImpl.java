package com.docker.msvc_usuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docker.msvc_usuarios.Entity.Usuario;
import com.docker.msvc_usuarios.repositories.UsuarioRepositry;

@Service 
public class UsurioServiceImpl implements UsuarioService {
    @Autowired 
    private  UsuarioRepositry usuarioRepositry;

   

    @Override
    @Transactional (readOnly = true)
    public List<Usuario> getAllUsuarios() {
        return usuarioRepositry.findAll();
    }

    @Override
    @Transactional (readOnly = true)
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepositry.findById(id);
    }

    @Override
    @Transactional
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepositry.save(usuario);
    }

    @Override
    @Transactional
    public void deleteUsuario(Long id) {
        usuarioRepositry.deleteById(id);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        
        return  usuarioRepositry.findByEmail(email);

        // TODO Auto-generated method stub
    }

    @Override
    public boolean existsByEmail(String email) {
        // TODO Auto-generated method stub

        return  usuarioRepositry.existsByEmail(email);
    }

    @Override
    public Optional<Usuario> porEmail(String email) {

        return  usuarioRepositry.porEmail(email);
        // TODO Auto-generated method stub
    }

  

}
