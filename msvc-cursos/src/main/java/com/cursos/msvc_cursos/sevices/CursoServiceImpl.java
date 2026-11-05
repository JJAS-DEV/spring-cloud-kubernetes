package com.cursos.msvc_cursos.sevices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursos.msvc_cursos.entity.Curso;
import com.cursos.msvc_cursos.repository.CursoRepository;

@Service 
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    @Transactional (readOnly = true
    )
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @Override
    @Transactional (readOnly = true)
    public Optional<Curso> porId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }

 
    

}
