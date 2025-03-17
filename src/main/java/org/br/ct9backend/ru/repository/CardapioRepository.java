package org.br.ct9backend.ru.repository;

import org.br.ct9backend.ru.model.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CardapioRepository extends JpaRepository<Cardapio, Long> {
    Optional<Cardapio> findByData(LocalDate data);
}
