package org.br.ct9backend.curso.service;

import org.br.ct9backend.curso.model.*;
import org.br.ct9backend.curso.repository.*;
import org.br.ct9backend.curso.repository.auxTables.CursoCoordenacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class CursosServiceImpl implements CursosService {

    private static final Logger logger = LoggerFactory.getLogger(CursosServiceImpl.class);

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private RequisitoRepository requisitoRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;
    @Autowired
    private CursoCoordenacaoRepository cursoCoordenacaoRepository;

    @Override
    @Transactional
    public Curso criarCurso(Curso curso) {
       Curso savedCurso = cursoRepository.save(curso);
       logger.info("Curso criado com sucesso: {}", curso);
       return savedCurso;
    }

    @Override
    public Iterable<Curso> getTodosCursos() {
        return cursoRepository.findAll();
    }

    @Override
    public Optional<Curso> getCurso(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    public Curso editarCurso(Long id, Curso curso) {
        Optional<Curso> cursoSalvo = cursoRepository.findById(id);
        if(cursoSalvo.isEmpty()){
            throw new RuntimeException("Curso não encontrado");
        }

        BeanUtils.copyProperties(curso, cursoSalvo.get(), "id");

        return cursoRepository.save(cursoSalvo.get());
    }


    @Override
    public List<Disciplina> getDisciplinas(Long cursoID) {
        Optional<Curso> curso = cursoRepository.findById(cursoID);

        if(curso.isEmpty()) {
            throw new RuntimeException("Curso não encontrado");
        }

        if(curso.get().getPpc().isEmpty()) {
            throw new RuntimeException("Curso não encontrado");
        }

        PPC ppcAtivo = curso.get().getPpc().stream().filter(ppc -> ppc.getAtivo().equals(true)).findFirst().orElse(null);

        if(ppcAtivo == null) {
            throw new RuntimeException("PPC não encontrado");
        }

        return ppcAtivo.getDisciplinas();

    }

    @Override
    public List<Coordenacao> getCoordenacao(Long id) {
        Optional<Curso> curso = cursoRepository.findById(id);

        if(curso.isEmpty()) {
            throw new RuntimeException("Curso não encontrado");
        }

        return curso.get().getCoordenacao();
    }

    @Override
    public Coordenacao getCoordenador(Long id) {
        Optional<Coordenacao> coordenador = coordenacaoRepository.findById(id);

        if(coordenador.isEmpty()) {
            throw new RuntimeException("Coordenador não encontrado");
        }

        return coordenador.get();
    }

    @Override
    public Coordenacao saveCoordenacao(Long cursoId, Coordenacao coordenacao) {
        Optional<Curso> curso = cursoRepository.findById(cursoId);
        if (curso.isEmpty()) {
            throw new RuntimeException("Curso não encontrado");
        }
        curso.get().getCoordenacao().add(coordenacao);
        return coordenacaoRepository.save(coordenacao);
    }

    @Override
    public Coordenacao editCoordenacao(Long id, Coordenacao coordenacao) {
        Optional<Coordenacao> coordenacaoSalva = coordenacaoRepository.findById(id);
        if(coordenacaoSalva.isEmpty()){
            throw new RuntimeException("Coordenacao não encontrada");
        }

        BeanUtils.copyProperties(coordenacao, coordenacaoSalva.get(), "id");

        return coordenacaoRepository.save(coordenacaoSalva.get());
    }

    @Transactional
    @Override
    public void deleteCoordenador(Long id) {
        Optional<Coordenacao> coordenacao = coordenacaoRepository.findById(id);


        if(coordenacao.isEmpty()){
            throw new RuntimeException("Coordenacao não encontrada");
        }

        cursoCoordenacaoRepository.deleteByCoordenacaoId(id);
        coordenacaoRepository.delete(coordenacao.get());
    }


}