package org.br.ct9backend.noticias.repository;

import org.br.ct9backend.noticias.model.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
}
