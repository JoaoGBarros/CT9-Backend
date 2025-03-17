package org.br.ct9backend.curso.service;

import org.br.ct9backend.curso.model.Curso;
import org.br.ct9backend.curso.model.Semestre;
import org.br.ct9backend.curso.repository.CursoRepository;
import org.br.ct9backend.curso.repository.SemestreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SemestreServiceImpl implements SemestreService {

    @Autowired
    private SemestreRepository semestreRepository;

    @Autowired
    private CursoRepository cursoRepository;


    @Override
    public List<Semestre> getSemestres(Long idCurso) {
        Optional<Curso> curso = cursoRepository.findById(idCurso);

        if(curso.isEmpty()) {
            throw new RuntimeException("Curso não encontrado");
        }

        return curso.get().getSemestres();
    }

    @Override
    public Semestre saveSemestre(Long cursoId, Semestre semestre) {
        Optional<Curso> curso = cursoRepository.findById(cursoId);
        if(curso.isEmpty()) {
            throw new RuntimeException("Curso não encontrado");
        }

        curso.get().getSemestres().add(semestre);

        cursoRepository.save(curso.get());

        return semestre;

    }

    @Override
    public Semestre getSemestre(Long semestreId) {
        Optional<Semestre> semestre = semestreRepository.findById(semestreId);

        if(semestre.isEmpty()) {
            throw new RuntimeException("Semestre não encontrado");
        }

        return semestre.get();
    }
}
