package org.br.ct9backend.curso.service;


import org.br.ct9backend.curso.model.Alocacao;
import org.br.ct9backend.curso.model.Semestre;

import java.util.List;

public interface SemestreService {

    List<Semestre> getSemestres(Long idCurso);

    Semestre saveSemestre(Long cursoId, Semestre semestre);

    Semestre getSemestre(Long semestreId);

}
