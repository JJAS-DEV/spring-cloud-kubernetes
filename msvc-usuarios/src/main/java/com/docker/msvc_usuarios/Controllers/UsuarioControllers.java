package com.docker.msvc_usuarios.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.docker.msvc_usuarios.Entity.Usuario;
import com.docker.msvc_usuarios.service.UsuarioService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UsuarioControllers {

    @Autowired
    private UsuarioService usuarioservice;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioservice.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioservice.getUsuarioById(id);
        if(usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        } else {
            return ResponseEntity.notFound().build(); // or throw an exception, or return a default value
        }
    }

    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioservice.saveUsuario(usuario));
        //TODO: process POST request

        
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@RequestBody Usuario usuario, @PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioservice.getUsuarioById(id);
        if(usuarioOptional.isPresent()) {
            Usuario usuarioExistente = usuarioOptional.get();
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setEmail(usuario.getEmail());
            return ResponseEntity.ok(usuarioservice.saveUsuario(usuarioExistente));
        } else {
            return ResponseEntity.notFound().build(); // or throw an exception, or return a default value
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioservice.getUsuarioById(id);
        if(usuarioOptional.isPresent()) {
            usuarioservice.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build(); // or throw an exception, or return a default value
        }
    }
    
}
