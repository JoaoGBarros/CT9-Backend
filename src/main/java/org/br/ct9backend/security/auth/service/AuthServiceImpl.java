package org.br.ct9backend.security.auth.service;

import org.br.ct9backend.aluno.model.Aluno;
import org.br.ct9backend.aluno.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public String verifyRole(String uid) {
        Optional<Aluno> aluno = alunoRepository.findByFirebaseUid(uid);

        if(aluno.isEmpty()){
            throw new RuntimeException("Aluno não encontrado");
        }

        return aluno.get().getCargo().toString();

    }
}
