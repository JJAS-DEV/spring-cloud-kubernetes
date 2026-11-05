package com.cursos.msvc_cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cursos.msvc_cursos.entity.Curso;

@Repository 
public interface CursoRepository  extends JpaRepository<Curso, Long> {
    

}

