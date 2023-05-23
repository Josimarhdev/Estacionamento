package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    @Transactional(rollbackFor = Exception.class)

    public void VerificarModelo (Modelo modelo){
        Assert.isTrue(!modelo.getNome().equals(""),"Modelo não pode ser nulo");
        Assert.isTrue(modelo.getNome().length() <= 50, "Deve conter até 50 digitos");
        Modelo modeloExistente = modeloRepository.findByNome(modelo.getNome());
        Assert.isTrue(modeloExistente == null || modeloExistente.equals(modelo), "Modelo já existente");



        Assert.isTrue(modelo.getMarca() != null,"Marca não pode ser nulo");



        modelo.setAtivo(true);

        this.modeloRepository.save(modelo);

    }




    public void deletar(@PathVariable Long id) {
        Optional<Modelo> modeloOptional = modeloRepository.findById(id);
        if (modeloOptional.isPresent()) {
            Modelo modelo = modeloOptional.get();
            if (!modelo.isAtivo()) {
                modeloRepository.delete(modelo);
            } else {
                modelo.setAtivo(false);
                modeloRepository.save(modelo);
            }
        }
    }



}