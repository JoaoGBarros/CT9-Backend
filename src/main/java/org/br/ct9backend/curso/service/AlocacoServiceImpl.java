package org.br.ct9backend.curso.service;

import org.br.ct9backend.curso.model.Alocacao;
import org.br.ct9backend.curso.model.DisciplinaOfertada;
import org.br.ct9backend.curso.model.Semestre;
import org.br.ct9backend.curso.model.dto.AlocacaoResponseDTO;
import org.br.ct9backend.curso.repository.AlocacaoRepository;
import org.br.ct9backend.curso.repository.DisciplinaOfertadaRepository;
import org.br.ct9backend.curso.repository.SemestreRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AlocacoServiceImpl implements AlocacaoService {

    @Autowired
    private AlocacaoRepository alocacaoRepository;

    @Autowired
    private SemestreRepository semestreRepository;
    @Autowired
    private DisciplinaOfertadaRepository disciplinaOfertadaRepository;

    @Override
    public List<Alocacao> getAlocacoesFromSemester(Long semestreId) {
        Optional<Semestre> semestre = semestreRepository.findById(semestreId);

        if(semestre.isEmpty()) {
            throw new RuntimeException("Semestre não encontrado");
        }

        return Collections.singletonList(new Alocacao());
    }

    @Override
    public Alocacao getAlocacao(Long alocacaoId) {
        Optional<Alocacao> alocacao = alocacaoRepository.findById(alocacaoId);

        if(alocacao.isEmpty()) {
            throw new RuntimeException("Alocação não encontrada");
        }

        return alocacao.get();
    }

    @Override
    public Alocacao saveAlocacao(Long disciplinaId, Alocacao alocacao) {
        Optional<DisciplinaOfertada> disciplina = disciplinaOfertadaRepository.findById(disciplinaId);

        if (disciplina.isEmpty()) {
            throw new RuntimeException("Disciplina não encontrada");
        }

        disciplina.get().getAlocacoes().add(alocacao);

        return alocacaoRepository.save(alocacao);
    }

    @Override
    public Alocacao updateAlocacao(Long alocacaoId, Alocacao alocacao) {
        Optional<Alocacao> savedAloc = alocacaoRepository.findById(alocacaoId);

        if (savedAloc.isEmpty()) {
            throw new RuntimeException("Disciplina não encontrada");
        }

        BeanUtils.copyProperties(alocacao, savedAloc.get(), "id");

        return alocacaoRepository.save(savedAloc.get());
    }

    @Override
    public void deleteAlocacao(Long alocacaoId) {
        Optional<Alocacao> alocacao = alocacaoRepository.findById(alocacaoId);

        if(alocacao.isEmpty()) {
            throw new RuntimeException("Alocação não encontrada");
        }

        alocacaoRepository.delete(alocacao.get());
    }

    @Override
    public List<AlocacaoResponseDTO> getAlocaoSalaHoje(String predio, String sala) {

        LocalDate hoje = LocalDate.now();

        DayOfWeek diaDaSemana = DayOfWeek.TUESDAY;

        LocalTime horario = LocalTime.now();

        List<AlocacaoResponseDTO> aloc = alocacaoRepository.findAlocacoesComDisciplina(diaDaSemana, predio, sala, horario);

        return aloc;
    }


}
