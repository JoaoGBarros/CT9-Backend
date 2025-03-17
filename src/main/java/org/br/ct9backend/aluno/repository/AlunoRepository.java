package org.br.ct9backend.aluno.repository;

import jakarta.validation.constraints.NotNull;
import org.br.ct9backend.aluno.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Optional<Aluno> findByFirebaseUid(@NotNull String firebaseUid);
}
