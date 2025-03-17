package org.br.ct9backend.curso.service;

import jakarta.transaction.Transactional;
import org.br.ct9backend.aluno.model.Aluno;
import org.br.ct9backend.aluno.repository.AlunoRepository;
import org.br.ct9backend.curso.model.*;
import org.br.ct9backend.curso.model.dto.AvaliarDisciplinaRequest;
import org.br.ct9backend.curso.model.dto.DisciplinaOfertadaDTO;
import org.br.ct9backend.curso.repository.*;
import org.br.ct9backend.curso.repository.auxTables.PPCDisciplinasRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaServiceImpl implements DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private SemestreRepository semestreRepository;
    @Autowired
    private DisciplinaOfertadaRepository disciplinaOfertadaRepository;
    @Autowired
    private AlocacaoRepository alocacaoRepository;

    @Autowired
    private PPCRepository ppcRepository;

    @Autowired
    private PPCDisciplinasRepository ppcdisciplinaRepository;
    @Autowired
    private SituacaoDisciplinaRepository situacaoDisciplinaRepository;
    @Autowired
    private AlunoRepository alunoRepository;


    @Override
    public Disciplina getDisciplina(Long id) {

        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);

        if(disciplina.isEmpty()) {
            throw new RuntimeException("Disciplina não encontrada");
        }

        return disciplina.get();
    }

    @Override
    public Disciplina editarDisciplina(Long id, Disciplina disciplina) {
        Optional<Disciplina> disciplinaSalva = disciplinaRepository.findById(id);
        if(disciplinaSalva.isEmpty()){
            return null;
        }

        try {
            BeanUtils.copyProperties(disciplina, disciplinaSalva.get());
            disciplinaRepository.save(disciplina);
            return disciplina;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao editar disciplina");
        }
    }

    @Override
    public List<DisciplinaOfertada> getDisciplinasOfertadas(Long semestreId) {
        Optional<Semestre> semestre = semestreRepository.findById(semestreId);

        if (semestre.isEmpty()) {
            throw new RuntimeException("Semestre não encontrado");
        }

        return semestre.get().getDisciplinasOfertadas();
    }

    @Transactional
    @Override
    public List<DisciplinaOfertada> importDisciplinasOfertadas(Long semestreId, Long cursoId, List<DisciplinaOfertadaDTO> disciplinas) {
        List<DisciplinaOfertada> disciplinasOfertadas = new ArrayList<DisciplinaOfertada>();

        Optional<Semestre> semestre = semestreRepository.findById(semestreId);

        if(semestre.isEmpty()){
            throw new RuntimeException("Semestre não encontrado");
        }

        for(DisciplinaOfertadaDTO disciplina : disciplinas) {
            Optional<Disciplina> disciplinaSalva = disciplinaRepository.findByCodigoDisciplinaAndCursoCodigo(disciplina.getCodigoDisciplina(), cursoId);
            if(disciplinaSalva.isEmpty()){
                throw new RuntimeException("Disciplina não encontrada");
            }

            disciplinasOfertadas.add(disciplinaOfertadaRepository.save(new DisciplinaOfertada(
                    disciplina.getId(),
                    disciplinaSalva.get(),
                    disciplina.getTurma(),
                    disciplina.getAlocacoes()
            )));


        }

        semestre.get().getDisciplinasOfertadas().addAll(disciplinasOfertadas);

        return semestreRepository.save(semestre.get()).getDisciplinasOfertadas();

    }

    @Transactional
    @Override
    public DisciplinaOfertada saveDisciplinasOfertadas(Long semestreId, Long cursoId, DisciplinaOfertadaDTO disciplinas) {
        Optional<Semestre> semestre = semestreRepository.findById(semestreId);

        if(semestre.isEmpty()){
            throw new RuntimeException("Semestre não encontrado");
        }

        Optional<Disciplina> disciplinaSalva = disciplinaRepository.findByCodigoDisciplinaAndCursoCodigo(disciplinas.getCodigoDisciplina(), cursoId);
        if(disciplinaSalva.isEmpty()){
            throw new RuntimeException("Disciplina não encontrada");
        }

        DisciplinaOfertada disciplinaOfertada = new DisciplinaOfertada(
                disciplinas.getId(),
                disciplinaSalva.get(),
                disciplinas.getTurma(),
                disciplinas.getAlocacoes()
        );

        semestre.get().getDisciplinasOfertadas().add(disciplinaOfertada);

        return disciplinaOfertadaRepository.save(disciplinaOfertada);
    }

    @Override
    public DisciplinaOfertada getDisciplinaOfertada(Long disciplinaId) {
        return disciplinaOfertadaRepository.findById(disciplinaId).orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
    }

    @Override
    public List<Alocacao> getAlocacao(Long disciplinaId) {
        return alocacaoRepository.findByDisciplinaOfertadaId(disciplinaId);
    }

    @Override
    public DisciplinaOfertada updateDisciplinaOfertada(DisciplinaOfertadaDTO disciplina, Long disciplinaId) {
        DisciplinaOfertada disciplinaOfertada =  disciplinaOfertadaRepository.findById(disciplinaId).orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
        Disciplina disciplinaSalva = disciplinaRepository.findByCodigoDisciplina(disciplina.getCodigoDisciplina())
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));

        BeanUtils.copyProperties(disciplina, disciplinaOfertada, "id", "disciplina", "alocacoes");
        disciplinaOfertada.setDisciplina(disciplinaSalva);
        return disciplinaOfertadaRepository.save(disciplinaOfertada);
    }

    @Override
    public Disciplina saveDisciplina(Long ppcId, Disciplina disciplina) {
        Optional<PPC> ppc = ppcRepository.findById(ppcId);

        if(ppc.isEmpty()) {
            throw new RuntimeException("PPC não encontrado");
        }

        ppc.get().getDisciplinas().add(disciplina);

        return disciplinaRepository.save(disciplina);
    }

    @Override
    public void deleteAlocacao(Long alocacaoId) {
        Optional<Alocacao> alocacao = alocacaoRepository.findById(alocacaoId);

        if(alocacao.isEmpty()) {
            throw new RuntimeException("Alocação não encontrada");
        }

        alocacaoRepository.delete(alocacao.get());
    }

    @Transactional
    @Override
    public void deleteDisciplina(Long id) {
        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);

        if(disciplina.isEmpty()) {
            throw new RuntimeException("Disciplina não encontrada");
        }

        ppcdisciplinaRepository.deleteByDisciplinaId(id);
        disciplinaRepository.delete(disciplina.get());
    }

    @Override
    public void avaliarDisciplina(AvaliarDisciplinaRequest avaliacaoRequest, String userId) {
        Optional<Disciplina> disciplinaOpt = disciplinaRepository.findById(avaliacaoRequest.getDisciplinaId());
        Optional<Aluno> aluno = alunoRepository.findByFirebaseUid(userId);
        if (disciplinaOpt.isEmpty() || aluno.isEmpty()) {
            throw new RuntimeException("Entidade não encontrada");
        }

        Disciplina disciplina = disciplinaOpt.get();
        SituacaoDisciplina situacaoDisciplina = situacaoDisciplinaRepository
                .findByDisciplinaIdAndAlunoId(avaliacaoRequest.getDisciplinaId(), aluno.get().getId())
                .orElseThrow(() -> new RuntimeException("Situação não encontrada"));

        if (situacaoDisciplina.getAvaliou()) {
            throw new RuntimeException("Usuário já avaliou esta disciplina");
        }


        disciplina.getNotaDificuldade().add(avaliacaoRequest.getNotaDificuldade());
        disciplina.getNotaTempoGasto().add(avaliacaoRequest.getNotaTempo());

        disciplinaRepository.save(disciplina);

        situacaoDisciplina.setAvaliou(true);
        situacaoDisciplinaRepository.save(situacaoDisciplina);
    }
}
