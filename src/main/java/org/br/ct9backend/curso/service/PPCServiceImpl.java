package org.br.ct9backend.curso.service;

import org.br.ct9backend.curso.model.Curso;
import org.br.ct9backend.curso.model.Disciplina;
import org.br.ct9backend.curso.model.PPC;
import org.br.ct9backend.curso.model.SituacaoDisciplina;
import org.br.ct9backend.curso.model.auxTable.AlunoSituacaoDisciplina;
import org.br.ct9backend.curso.repository.CursoRepository;
import org.br.ct9backend.curso.repository.DisciplinaRepository;
import org.br.ct9backend.curso.repository.PPCRepository;
import org.br.ct9backend.curso.repository.SituacaoDisciplinaRepository;
import org.br.ct9backend.curso.repository.auxTables.AlunoSituacaoDisciplinaRepository;
import org.br.ct9backend.curso.repository.auxTables.CursoPPCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PPCServiceImpl implements PPCService{

    @Autowired
    private PPCRepository ppcRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CursoPPCRepository cursoPPCRepository;
    @Autowired
    private SituacaoDisciplinaRepository situacaoDisciplinaRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private AlunoSituacaoDisciplinaRepository alunoSituacaoDisciplinaRepository;


    @Transactional
    @Override
    public PPC criarPPC(Long cursoID, PPC ppc) {
        Optional<Curso> curso = cursoRepository.findById(cursoID);

        if (curso.isEmpty()) {
            throw new RuntimeException("Curso não encontrado");
        }

        if (ppc.getAtivo()) {
            curso.get().getPpc().forEach(existingPPC -> {
                if (existingPPC.getAtivo()) {
                    existingPPC.setAtivo(false);
                    ppcRepository.save(existingPPC);
                }
            });
        }

        curso.get().getPpc().add(ppc);
        ppcRepository.save(ppc);
        return ppc;
    }

    @Override
    public List<PPC> getPPCs(Long cursoID) {
        Optional<Curso> curso = cursoRepository.findById(cursoID);

        if(curso.isEmpty()) {
            throw new RuntimeException("Curso não encontrado");
        }
        return curso.get().getPpc();
    }

    @Override
    public PPC getPPC(Long cursoID, Long ppcID) {
        Optional<Curso> curso = cursoRepository.findById(cursoID);

        if(curso.isEmpty()) {
            throw new RuntimeException("Curso não encontrado");
        }
        return curso.get().getPpc().stream().filter(ppc -> ppc.getId().equals(ppcID)).findFirst().orElse(null);
    }

    @Transactional
    @Override
    public void deletePPC(Long ppcID) {
        Optional<PPC> ppc = ppcRepository.findById(ppcID);

        if(ppc.isEmpty()) {
            throw new RuntimeException("Curso não encontrado");
        }
        cursoPPCRepository.deleteByPpcId(ppcID);
        for(Disciplina d : ppc.get().getDisciplinas()) {

            for(SituacaoDisciplina situ : situacaoDisciplinaRepository.findByDisciplinaId(d.getId())) {
                alunoSituacaoDisciplinaRepository.deleteBySituacaoDisciplinaId(situ.getId());
            }
            situacaoDisciplinaRepository.deleteByDisciplinaId(d.getId());
            disciplinaRepository.delete(d);
        }
        ppcRepository.delete(ppc.get());
    }

}
