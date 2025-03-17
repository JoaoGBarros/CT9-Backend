package org.br.ct9backend.noticias.service;


import org.br.ct9backend.noticias.model.Noticia;
import org.br.ct9backend.noticias.repository.NoticiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class NoticiaServiceImpl implements NoticiaService {


    @Autowired
    private NoticiaRepository noticiaRepository;

    @Override
    public List<Noticia> getNoticias() {
        return noticiaRepository.findAll();
    }

    @Override
    public Noticia saveNoticia(Noticia noticia) {
        return noticiaRepository.save(noticia);
    }

    @Override
    public Noticia getNoticia(Long id) {

        Optional<Noticia> noticia = noticiaRepository.findById(id);

        if(noticia.isEmpty()) {
            throw new RuntimeException("Notícia não encontrada");
        }

        return noticia.get();
    }

    @Override
    public void deleteNoticia(Long id) {
        Optional<Noticia> noticia = noticiaRepository.findById(id);

        if(noticia.isEmpty()) {
            throw new RuntimeException("Notícia não encontrada");
        }

        noticiaRepository.delete(noticia.get());
    }

}
