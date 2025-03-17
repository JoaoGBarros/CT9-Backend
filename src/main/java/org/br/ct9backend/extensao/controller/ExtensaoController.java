package org.br.ct9backend.extensao.controller;

import org.br.ct9backend.extensao.model.Extensao;
import org.br.ct9backend.extensao.service.ExtensaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/extensao")
public class ExtensaoController {

    @Autowired
    private ExtensaoService extensaoService;

    @GetMapping("/getTodasExtensao")
    public ResponseEntity<List<Extensao>> getExtensao() {
        try {
            List<Extensao> extensao = extensaoService.getTodasExtensao();
            return ResponseEntity.status(HttpStatus.OK).body(extensao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/salvarExtensao")
    public ResponseEntity<Extensao> saveExtensao(@RequestBody Extensao extensao) {
        try {
            Extensao savedExtensao = extensaoService.saveExtensao(extensao);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedExtensao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @GetMapping("/getExtensao/{id}")
    public ResponseEntity<Extensao> getExtensao(@PathVariable Long id) {
        try {
            Extensao extensao = extensaoService.getExtensao(id);
            return ResponseEntity.status(HttpStatus.OK).body(extensao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteExtensao/{id}")
    public ResponseEntity<Boolean> deleteExtensao(@PathVariable Long id) {
        try {
            extensaoService.deleteExtensao(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateExtensao/{id}")
    public ResponseEntity<Extensao> updateExtensao(@PathVariable Long id, @RequestBody Extensao extensao) {
        try {
            Extensao updatedExtensao = extensaoService.updateExtensao(id, extensao);
            return ResponseEntity.status(HttpStatus.OK).body(updatedExtensao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
