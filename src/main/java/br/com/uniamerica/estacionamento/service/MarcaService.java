package br.com.uniamerica.estacionamento.service;


import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional(rollbackFor = Exception.class)

    public void VerificarMarca (Marca marca){
        Assert.isTrue(marca.getNome()!=null,"O nome não pode ser nulo!");
        Assert.isTrue(!marca.getNome().equals(""),"O nome não pode ser nulo!");

        Marca nomeExistente = marcaRepository.findByNome(marca.getNome());
        Assert.isTrue(nomeExistente == null || nomeExistente.equals(marca),"Marca já existente!");
        Assert.isTrue(marca.getNome().length() <= 30 ,"A marca deve ter no máximo 30 digitos") ;

        marca.setAtivo(true);

        this.marcaRepository.save(marca);
    }


    @Transactional(rollbackFor = Exception.class)

    public ResponseEntity<?> deletar(Long id) {
        Marca marca = this.marcaRepository.findById(id).orElse(null);

        if (marca == null || marca.getId() != (marca.getId())) {
            throw new RuntimeException("Não foi possivel identificar o registro informado");
        }


        List<Modelo> modeloLista = this.marcaRepository.findModelo(marca);

        if (modeloLista.isEmpty()) {
            if(marca.isAtivo()) {
                marca.setAtivo(false);
                this.marcaRepository.save(marca);
                return ResponseEntity.ok("Desativado com sucesso");
            }
            this.marcaRepository.deleteById(id);
            return ResponseEntity.ok("Deletado com sucesso");
        }

        if(!marca.isAtivo()) {
            Assert.isTrue(modeloLista.isEmpty(), "Marca vinculado a um modelo");
            this.marcaRepository.deleteById(id);
        }


        marca.setAtivo(false);
        this.marcaRepository.save(marca);
        return ResponseEntity.ok("Desativado com sucesso");

    }


    }