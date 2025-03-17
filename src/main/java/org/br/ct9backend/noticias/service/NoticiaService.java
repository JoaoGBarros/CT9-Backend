package org.br.ct9backend.noticias.service;

import org.br.ct9backend.noticias.model.Noticia;

import java.util.List;

public interface NoticiaService {
    List<Noticia> getNoticias();

    Noticia saveNoticia(Noticia noticia);

    Noticia getNoticia(Long id);

    void deleteNoticia(Long id);
}
