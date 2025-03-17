package org.br.ct9backend.curso.service;

import org.br.ct9backend.curso.model.Coordenacao;
import org.br.ct9backend.curso.model.Curso;
import org.br.ct9backend.curso.model.Disciplina;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public interface CursosService {

    Curso criarCurso(Curso curso);

    Iterable<Curso> getTodosCursos();

    Optional<Curso> getCurso(Long id);

    Curso editarCurso(Long id, Curso curso) throws InvocationTargetException, IllegalAccessException;

    List<Disciplina> getDisciplinas(Long id);

    List<Coordenacao> getCoordenacao(Long id);

    Coordenacao getCoordenador(Long id);

    Coordenacao saveCoordenacao(Long cursoId, Coordenacao coordenacao);

    Coordenacao editCoordenacao(Long id, Coordenacao coordenacao);

    void deleteCoordenador(Long id);
}
