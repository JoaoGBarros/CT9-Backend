package org.br.ct9backend.agenda.service;


import jakarta.transaction.Transactional;
import org.br.ct9backend.agenda.model.Agenda;
import org.br.ct9backend.agenda.model.AgendaDTO;
import org.br.ct9backend.agenda.model.Tarefa;
import org.br.ct9backend.agenda.repository.AgendaRepository;
import org.br.ct9backend.agenda.repository.TarefaRepository;
import org.br.ct9backend.agenda.repository.auxTable.AgendaTarefasRepository;
import org.br.ct9backend.aluno.model.Aluno;
import org.br.ct9backend.aluno.repository.AlunoRepository;
import org.br.ct9backend.curso.model.Alocacao;
import org.br.ct9backend.curso.model.DisciplinaOfertada;
import org.br.ct9backend.curso.model.SituacaoDisciplina;
import org.br.ct9backend.curso.model.enums.Situacao;
import org.br.ct9backend.curso.repository.DisciplinaOfertadaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgendaServiceImpl implements AgendaService {


    private final AlunoRepository alunoRepository;
    private final DisciplinaOfertadaRepository disciplinaOfertadaRepository;
    private final TarefaRepository tarefaRepository;
    private final AgendaTarefasRepository agendaTarefasRepository;
    private final AgendaRepository agendaRepository;

    public AgendaServiceImpl(AlunoRepository alunoRepository, DisciplinaOfertadaRepository disciplinaOfertadaRepository, TarefaRepository tarefaRepository, AgendaTarefasRepository agendaTarefasRepository, AgendaRepository agendaRepository) {
        this.alunoRepository = alunoRepository;
        this.disciplinaOfertadaRepository = disciplinaOfertadaRepository;
        this.tarefaRepository = tarefaRepository;
        this.agendaTarefasRepository = agendaTarefasRepository;
        this.agendaRepository = agendaRepository;
    }


    @Override
    public List<AgendaDTO> getDisciplinasCursando(String firebaseUid) {
        Optional<Aluno> aluno = alunoRepository.findByFirebaseUid(firebaseUid);
        List<AgendaDTO> agendaDTO = new ArrayList<>();

        if (aluno.isEmpty()) {
            throw new RuntimeException("Aluno não encontrado");
        }

        Aluno alunoEtity = aluno.get();
        DayOfWeek currentDay = LocalDate.now().getDayOfWeek();

        for (SituacaoDisciplina situacao : alunoEtity.getSituacoesDisciplina()) {
            if (situacao.getSituacao().equals(Situacao.CURSANDO) || situacao.getSituacao().equals(Situacao.QUEBRA)) {

                Optional<DisciplinaOfertada> ofertada = disciplinaOfertadaRepository.findById(situacao.getDisciplina().getId());

                if (ofertada.isEmpty()) {
                    continue;
                }

                DisciplinaOfertada disciplinaOfertada = ofertada.get();


                for (Alocacao alocacao : disciplinaOfertada.getAlocacoes()) {
                    if (alocacao.getDiaSemana().equals(currentDay)) {
                        agendaDTO.add(new AgendaDTO(
                                situacao.getDisciplina().getId(),
                                situacao.getDisciplina().getNome(),
                                situacao.getDisciplina().getCodigo(),
                                disciplinaOfertada.getTurma(),
                                alocacao.getPredio(),
                                alocacao.getSala(),
                                alocacao.getHoraInicio(),
                                alocacao.getHoraFim()
                                )
                        );
                    }
                }
            }
        }

        return agendaDTO;
    }

    @Override
    public List<Tarefa> getTarefas(String firebaseUid) {
        Optional<Aluno> aluno = alunoRepository.findByFirebaseUid(firebaseUid);

        if (aluno.isEmpty()) {
            throw new RuntimeException("Aluno não encontrado");
        }

        Boolean a = aluno.get().getAgenda().getTarefas().get(0).getConcluido();

        return aluno.get().getAgenda().getTarefas().stream()
                .filter(tarefa -> !tarefa.getConcluido())
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Tarefa saveTarefa(String firebaseUid, Tarefa tarefa) {
        Optional<Aluno> aluno = alunoRepository.findByFirebaseUid(firebaseUid);

        if (aluno.isEmpty()) {
            throw new RuntimeException("Aluno não encontrado");
        }

        Aluno alunoEntity = aluno.get();

        alunoEntity.getAgenda().getTarefas().add(tarefa);

        Tarefa t = tarefaRepository.save(tarefa);
        alunoRepository.save(alunoEntity);

        return t;
    }

    @Transactional
    @Override
    public Boolean deletarTarefa(Long tarefaId) {
        Optional<Tarefa> tarefaOpt = tarefaRepository.findById(tarefaId);

        if (tarefaOpt.isEmpty()) {
            throw new RuntimeException("Tarefa não encontrada");
        }

        Tarefa tarefa = tarefaOpt.get();
        Optional<Agenda> agendaOpt = agendaRepository.findByTarefasId(tarefaId);

        if (agendaOpt.isPresent()) {
            Agenda agenda = agendaOpt.get();
            agenda.getTarefas().remove(tarefa);
            agendaRepository.save(agenda);
        }

        tarefaRepository.deleteById(tarefaId);

        return true;
    }

    @Transactional
    @Override
    public Tarefa editarTarefa(Long tarefaId, Tarefa tarefa) {
        Optional<Tarefa> tarefaOpt = tarefaRepository.findById(tarefaId);

        if (tarefaOpt.isEmpty()) {
            throw new RuntimeException("Tarefa não encontrada");
        }

        Tarefa tarefaEntity = tarefaOpt.get();

        BeanUtils.copyProperties(tarefa, tarefaEntity, "id");

        return tarefaRepository.save(tarefaEntity);
    }
}
