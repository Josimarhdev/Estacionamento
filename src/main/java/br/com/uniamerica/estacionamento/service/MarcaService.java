package br.com.uniamerica.estacionamento.service;


import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional(rollbackFor = Exception.class)

    public void VerificarMarca (Marca marca){
        Assert.isTrue(!marca.getNome().equals(""),"O nome não pode ser nulo!");
        Marca nomeExistente = marcaRepository.findByNome(marca.getNome());
        Assert.isTrue(nomeExistente == null || nomeExistente.equals(marca),"Nome já existente!");


        marca.setAtivo(true);

        this.marcaRepository.save(marca);
    }




    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Optional<Marca> marcaOptional = marcaRepository.findById(id);
        if (marcaOptional.isPresent()) {
            Marca marca = marcaOptional.get();
            if (!marca.isAtivo()) {
                marcaRepository.delete(marca);
                return ResponseEntity.ok("Deletado com sucesso");
            }
            else {
                marca.setAtivo(false);
                marcaRepository.save(marca);
                return ResponseEntity.ok("Desativado com sucesso");
            }
        }
        return ResponseEntity.ok("Não foi encontrado nenhum registro");
    }


    }