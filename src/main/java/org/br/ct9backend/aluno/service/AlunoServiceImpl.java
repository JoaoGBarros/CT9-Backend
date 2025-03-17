package org.br.ct9backend.aluno.service;

import jakarta.transaction.Transactional;
import org.br.ct9backend.agenda.model.Agenda;
import org.br.ct9backend.agenda.model.AgendaDisciplinas;
import org.br.ct9backend.agenda.repository.AgendaRepository;
import org.br.ct9backend.aluno.model.Aluno;
import org.br.ct9backend.aluno.model.dto.AlunoDTO;
import org.br.ct9backend.aluno.model.dto.AtualizaSituacaoRequestDTO;
import org.br.ct9backend.aluno.model.dto.PorcentagemCursoCompletoDTO;
import org.br.ct9backend.aluno.repository.AlunoRepository;
import org.br.ct9backend.curso.model.*;
import org.br.ct9backend.curso.model.dto.DisciplinaInfoDTO;
import org.br.ct9backend.curso.model.enums.Situacao;
import org.br.ct9backend.curso.repository.CursoRepository;
import org.br.ct9backend.curso.repository.DisciplinaRepository;
import org.br.ct9backend.curso.repository.SituacaoDisciplinaRepository;
import org.br.ct9backend.security.auth.model.Cargo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlunoServiceImpl implements AlunoService{

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private SituacaoDisciplinaRepository situacaoDisciplinaRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;


    @Transactional
    @Override
    public AlunoDTO criarAluno(AlunoDTO alunoDto) {
        Aluno aluno = new Aluno();
        BeanUtils.copyProperties(alunoDto, aluno);
        Optional<Curso> curso = cursoRepository.findById(alunoDto.getCurso());

        if (curso.isEmpty()) {
            throw new RuntimeException("Curso não encontrado");
        }

        aluno.setCargo(Cargo.ALUNO);
        aluno.setCurso(curso.get());
        aluno.setAgenda(new Agenda());
        aluno.setHorasCpl(0);
        aluno.setHorasExt(0);

        PPC activePPC = curso.get().getPpc().stream()
                .filter(PPC::getAtivo)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("PPC Ativo não encontrado"));

        List<SituacaoDisciplina> situacoesDisciplina = new ArrayList<>();
        List<Disciplina> disciplinas = activePPC.getDisciplinas();

        for (Disciplina disciplina : disciplinas) {
            SituacaoDisciplina situacaoDisciplina = new SituacaoDisciplina();
            situacaoDisciplina.setAluno(aluno);
            situacaoDisciplina.setDisciplina(disciplina);
            situacaoDisciplina.setAvaliou(false);
            if (disciplina.getPeriodo().equals("1º") || disciplina.getRequisitos().isEmpty()) {
                situacaoDisciplina.setSituacao(Situacao.NAO_CURSADA);
            } else {
                situacaoDisciplina.setSituacao(Situacao.TRANCADA); // Ensure this value is valid
            }
            situacoesDisciplina.add(situacaoDisciplina);
        }
        aluno.setSituacoesDisciplina(situacoesDisciplina);
        agendaRepository.save(aluno.getAgenda());
        aluno = alunoRepository.save(aluno);
        BeanUtils.copyProperties(aluno, alunoDto);

        return alunoDto;
    }

    @Override
    public AlunoDTO buscarAluno(String firebaseUid) {
        Optional<Aluno> aluno = alunoRepository.findByFirebaseUid(firebaseUid);

        if(aluno.isEmpty()){
            throw new RuntimeException("Aluno não encontrado");
        }

        AlunoDTO alunoDto = new AlunoDTO();
        BeanUtils.copyProperties(aluno.get(), alunoDto);
        return alunoDto;
    }

    @Override
    public HashMap<String, List<DisciplinaInfoDTO>> getDisciplinasInfo(String firebaseUid) {
        Optional<Aluno> alunoOpt = alunoRepository.findByFirebaseUid(firebaseUid);

        if (alunoOpt.isEmpty()) {
            throw new RuntimeException("Aluno não encontrado");
        }

        Aluno aluno = alunoOpt.get();
        return aluno.getSituacoesDisciplina().stream()
                .map(situacaoDisciplina -> {
                    DisciplinaInfoDTO dto = new DisciplinaInfoDTO();
                    dto.setId(situacaoDisciplina.getDisciplina().getId());
                    dto.setNome(situacaoDisciplina.getDisciplina().getNome());
                    dto.setCodigo(situacaoDisciplina.getDisciplina().getCodigo());
                    dto.setNotaDificuldade(situacaoDisciplina.getDisciplina().getMediaNotaDificuldade());
                    dto.setNotaTempoGasto(situacaoDisciplina.getDisciplina().getMediaNotaTempoGasto());
                    dto.setSituacao(situacaoDisciplina.getSituacao().name());
                    dto.setAvaliou(situacaoDisciplina.getAvaliou());
                    dto.setRequisitos(situacaoDisciplina.getDisciplina().getRequisitos().stream()
                            .map(Requisito::getCodigo)
                            .collect(Collectors.joining(", ")));
                    dto.setDepartamento(Departamento.fromCodigo(situacaoDisciplina.getDisciplina().getCodigo()).toString());
                    dto.setPeriodo(situacaoDisciplina.getDisciplina().getPeriodo());
                    dto.setEmenta(situacaoDisciplina.getDisciplina().getEmenta());
                    dto.setCargaHoraria(situacaoDisciplina.getDisciplina().getCargaHoraria());
                    return dto;
                })
                .collect(Collectors.groupingBy(
                        DisciplinaInfoDTO::getPeriodo,
                        LinkedHashMap::new,
                        Collectors.toList()
                ))
                .entrySet().stream()
                .sorted((e1, e2) -> {
                    String p1 = e1.getKey().replace("º", "");
                    String p2 = e2.getKey().replace("º", "");
                    if (p1.equals("-")) return 1;
                    if (p2.equals("-")) return -1;
                    try {
                        int period1 = Integer.parseInt(p1);
                        int period2 = Integer.parseInt(p2);
                        return Integer.compare(period1, period2);
                    } catch (NumberFormatException e) {
                        return p1.compareTo(p2);
                    }
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .sorted(Comparator.comparing((DisciplinaInfoDTO dto) -> {
                                    if (dto.getSituacao().equals("CURSANDO") || dto.getSituacao().equals("QUEBRA")) {
                                        return 0;
                                    } else {
                                        return 1;
                                    }
                                }).thenComparing(DisciplinaInfoDTO::getId))
                                .collect(Collectors.toList()),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    @Override
    public PorcentagemCursoCompletoDTO getPorcentagem(String firebaseUid) {
        Optional<Aluno> alunoOpt = alunoRepository.findByFirebaseUid(firebaseUid);

        if (alunoOpt.isEmpty()) {
            throw new RuntimeException("Aluno não encontrado");
        }

        Aluno aluno = alunoOpt.get();

        List<SituacaoDisciplina> situacoesDisciplina = aluno.getSituacoesDisciplina();

        Integer tempoTotal = aluno.getCurso().getCargaHorariaObrigatoria() + aluno.getCurso().getCargaHorariaComplementar();

        Integer tempoCursado = situacoesDisciplina.stream()
                .filter(situacaoDisciplina -> situacaoDisciplina.getSituacao().equals(Situacao.CURSADA))
                .map(situacaoDisciplina -> situacaoDisciplina.getDisciplina().getCargaHoraria())
                .reduce(0, Integer::sum);

        Integer porcentagem = (tempoCursado * 100) / tempoTotal;

        return new PorcentagemCursoCompletoDTO(tempoCursado, tempoTotal, porcentagem);
    }

    @Override
    public String updateSituacao(String firebaseUid, List<AtualizaSituacaoRequestDTO> situacoesRequest) {
        Aluno aluno = alunoRepository.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        for (AtualizaSituacaoRequestDTO request : situacoesRequest) {
            SituacaoDisciplina situacaoDisciplina = situacaoDisciplinaRepository
                    .findByDisciplinaIdAndAlunoId(request.getDisciplinaId(), aluno.getId())
                    .orElseThrow(() -> new RuntimeException("Situação não encontrada"));

            situacaoDisciplina.setSituacao(request.getSituacao());
            situacaoDisciplinaRepository.save(situacaoDisciplina);

            if (request.getSituacao() == Situacao.CURSANDO || request.getSituacao() == Situacao.QUEBRA) {
                AgendaDisciplinas agendaDisciplina = new AgendaDisciplinas();
                agendaDisciplina.setDisciplina(situacaoDisciplina.getDisciplina());
                agendaDisciplina.setFaltas(new ArrayList<>());
                agendaDisciplina.setAvaliacoes(new ArrayList<>());

                Agenda agenda = aluno.getAgenda();
                agenda.getConteudo().add(agendaDisciplina);
                agendaRepository.save(agenda);
            }

            if (request.getSituacao() == Situacao.CURSADA) {
                Disciplina disciplinaCursada = situacaoDisciplina.getDisciplina();
                List<Disciplina> dependentes = disciplinaRepository.findDisciplinasDependentes(
                        disciplinaCursada.getCodigo()
                );

                for (Disciplina dependente : dependentes) {
                    if (verificarRequisitosCumpridos(aluno, dependente)) {
                        SituacaoDisciplina situacaoDependente = situacaoDisciplinaRepository
                                .findByDisciplinaIdAndAlunoId(dependente.getId(), aluno.getId())
                                .orElseThrow(() -> new RuntimeException("Situação não encontrada"));

                        if (situacaoDependente.getSituacao() == Situacao.TRANCADA) {
                            situacaoDependente.setSituacao(Situacao.NAO_CURSADA);
                            situacaoDisciplinaRepository.save(situacaoDependente);
                        }
                    }
                }
            }
        }

        return "Situacoes atualizadas com sucesso";
    }

    private boolean verificarRequisitosCumpridos(Aluno aluno, Disciplina disciplina) {
        List<Requisito> requisitos = disciplina.getRequisitos();

        for (Requisito requisito : requisitos) {
            switch (requisito.getTipo()) {
                case Disciplina:
                    if (!verificarDisciplinaCursada(aluno, requisito.getCodigo())) return false;
                    break;
                case Crédito:
                    if (!verificarCreditos(aluno, requisito.getCreditos())) return false;
                    break;
//                case CargaHorária:
//                    if (!verificarCargaHoraria(aluno, requisito.getCargaHorariaVencida())) return false;
//                    break;
//                case Período:
//                    if (!verificarPeriodo(aluno, requisito.getPeriodoVencido())) return false;
//                    break;
//                case CoRequisito:
//                    if (!verificarCoRequisito(aluno, requisito.getCodigo())) return false;
//                    break;
            }
        }
        return true;
    }

    private boolean verificarDisciplinaCursada(Aluno aluno, String codigoDisciplina) {
        return situacaoDisciplinaRepository.existsByAlunoAndDisciplinaCodigoAndSituacao(
                aluno, codigoDisciplina, Situacao.CURSADA
        );
    }

    private boolean verificarCreditos(Aluno aluno, Integer creditosRequisito) {
        Integer creditosAluno = situacaoDisciplinaRepository.sumCreditosByAlunoAndSituacao(
                aluno.getId(), Situacao.CURSADA
        );
        return creditosAluno != null && creditosAluno >= creditosRequisito;
    }


}

