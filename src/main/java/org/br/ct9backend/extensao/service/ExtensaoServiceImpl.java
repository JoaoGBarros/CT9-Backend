package org.br.ct9backend.extensao.service;

import org.br.ct9backend.extensao.model.Extensao;
import org.br.ct9backend.extensao.repository.ExtensaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExtensaoServiceImpl implements ExtensaoService{

    @Autowired
    private ExtensaoRepository extensaoRepository;

    @Override
    public List<Extensao> getTodasExtensao(){
        return extensaoRepository.findAll();
    }

    @Override
    public Extensao saveExtensao(Extensao extensao) {
        return extensaoRepository.save(extensao);
    }

    @Override
    public Extensao getExtensao(Long id) {
        Optional<Extensao> extensao = extensaoRepository.findById(id);
        if(extensao.isEmpty()){
            throw new RuntimeException("Extensão não encontrada");
        }

        return extensao.get();
    }

    @Override
    public void deleteExtensao(Long id) {
        Optional<Extensao> extensao = extensaoRepository.findById(id);
        if(extensao.isEmpty()){
            throw new RuntimeException("Extensão não encontrada");
        }

        extensaoRepository.delete(extensao.get());
    }

    @Override
    public Extensao updateExtensao(Long id, Extensao extensao) {
        Optional<Extensao> extensaoOptional = extensaoRepository.findById(id);

        if(extensaoOptional.isEmpty()){
            throw new RuntimeException("Extensão não encontrada");
        }

        Extensao extensaoAtualizada = extensaoOptional.get();

        if(extensao.getImagemUrl() != null){
            BeanUtils.copyProperties(extensao, extensaoAtualizada, "id");
        }else{
            BeanUtils.copyProperties(extensao, extensaoAtualizada, "id", "imagemUrl");
        }

        return extensaoRepository.save(extensaoAtualizada);

    }
}
