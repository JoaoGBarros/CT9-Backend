package org.br.ct9backend.noticias.controller;


import org.br.ct9backend.noticias.model.Noticia;
import org.br.ct9backend.noticias.service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/noticias")
public class NoticiasController {

    @Autowired
    private NoticiaService noticiaService;

    @GetMapping("/getTodasNoticias")
    public ResponseEntity<List<Noticia>> getNoticias() {

        try {
            List<Noticia> noticias = noticiaService.getNoticias();
            return ResponseEntity.status(HttpStatus.OK).body(noticias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/salvarNoticia")
    public ResponseEntity<Noticia> saveNoticia(@RequestBody Noticia noticia) {

        try {
            Noticia savedNoticia = noticiaService.saveNoticia(noticia);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNoticia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @GetMapping("/getNoticia/{id}")
    public ResponseEntity<Noticia> getNoticia(@PathVariable Long id) {

        try {
            Noticia noticia = noticiaService.getNoticia(id);
            return ResponseEntity.status(HttpStatus.OK).body(noticia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteNoticia/{id}")
    public ResponseEntity<Boolean> deleteNoticia(@PathVariable Long id) {

        try {
            noticiaService.deleteNoticia(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }

    }
}
