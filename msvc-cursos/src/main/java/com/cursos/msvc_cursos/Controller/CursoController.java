package com.cursos.msvc_cursos.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cursos.msvc_cursos.entity.Curso;
import com.cursos.msvc_cursos.sevices.CursoService;

import jakarta.validation.Valid;
@RestController 
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return new ResponseEntity<>(cursoService.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id) {
        return cursoService.porId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody Curso curso,BindingResult result)
    
    {
        if(result.hasErrors()){
            return getErrores(result);
        }
        return new ResponseEntity<>(cursoService.guardar(curso), HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid  @RequestBody Curso curso,BindingResult result) {

        if(result.hasErrors()){
            getErrores(result);
        }

        Optional<Curso> cursoOptional = cursoService.porId(id);
        if (!cursoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(cursoService.guardar(curso), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cursoService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

        private ResponseEntity<?> getErrores(BindingResult result) {
        Map <String,String> errores= new HashMap<>();
        result.getFieldErrors().forEach(err->{
            errores.put(err.getField(), err.getDefaultMessage());

        });

        return ResponseEntity.badRequest().body(errores);
    }
}