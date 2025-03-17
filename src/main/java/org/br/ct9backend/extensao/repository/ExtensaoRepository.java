package org.br.ct9backend.extensao.repository;


import org.br.ct9backend.extensao.model.Extensao;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ExtensaoRepository extends JpaRepository<Extensao, Long> {
}
