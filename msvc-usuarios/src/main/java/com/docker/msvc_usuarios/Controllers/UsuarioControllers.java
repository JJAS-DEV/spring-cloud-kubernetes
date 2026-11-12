package com.docker.msvc_usuarios.Controllers;

import com.docker.msvc_usuarios.service.UsurioServiceImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import com.docker.msvc_usuarios.Entity.Usuario;
import com.docker.msvc_usuarios.service.UsuarioService;

import jakarta.validation.Valid;

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

    private final UsurioServiceImpl usurioServiceImpl;
    @Autowired
    private UsuarioService usuarioservice;

    UsuarioControllers(UsurioServiceImpl usurioServiceImpl) {
        this.usurioServiceImpl = usurioServiceImpl;
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioservice.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioservice.getUsuarioById(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        } else {
            return ResponseEntity.notFound().build(); // or throw an exception, or return a default value
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {

        
                if (result.hasErrors()) {
                    return getErrores(result);
        
                }
        if (!usuario.getEmail().isEmpty()&&usurioServiceImpl.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("errores", "correo registrado "));
        }
        return ResponseEntity.ok(usuarioservice.saveUsuario(usuario));
        // TODO: process POST request

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Usuario usuario, BindingResult result,
            @PathVariable Long id) {


        if (result.hasErrors()) {
            return getErrores(result);
        }
        Optional<Usuario> usuarioOptional = usuarioservice.getUsuarioById(id);
        if (usuarioOptional.isPresent()) {

            Usuario usuarioExistente = usuarioOptional.get();
            
        if (!usuario.getEmail().isEmpty()&& !usuario.getEmail().equalsIgnoreCase(usuarioExistente.getEmail())&&usurioServiceImpl.findByEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("errores", "correo registrado "));
        }  
            
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
        if (usuarioOptional.isPresent()) {
            usuarioservice.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build(); // or throw an exception, or return a default value
        }
    }

    private ResponseEntity<?> getErrores(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
