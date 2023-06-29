package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    @Transactional(rollbackFor = Exception.class)

    public void VerificarModelo (Modelo modelo){

        Assert.isTrue(modelo.getNome()!=null,"Modelo não pode ser nulo");
        Assert.isTrue(!modelo.getNome().equals(""),"Modelo não pode ser nulo");
        Assert.isTrue(modelo.getNome().length() <= 40, "Deve conter até 40 digitos");
        Modelo modeloExistente = modeloRepository.findByNome(modelo.getNome());
        Assert.isTrue(modeloExistente == null || modeloExistente.equals(modelo), "Modelo já existente");



        Assert.isTrue(modelo.getMarca() != null,"Marca não pode ser nulo");



        modelo.setAtivo(true);

        this.modeloRepository.save(modelo);

    }

    @Transactional(rollbackFor = Exception.class)
    public void AtualizaModelo (Modelo modelo){
        Assert.isTrue(modelo.getNome()!=null,"Modelo não pode ser nulo");
        Assert.isTrue(!modelo.getNome().equals(""),"Modelo não pode ser nulo");
        Assert.isTrue(modelo.getNome().length() <= 40, "Deve conter até 40 digitos");




        Assert.isTrue(modelo.getMarca() != null,"Marca não pode ser nulo");



        modelo.setAtivo(true);

        this.modeloRepository.save(modelo);

    }



    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deletar(Long id) {
        Modelo modelo = this.modeloRepository.findById(id).orElse(null);

        if (modelo == null || modelo.getId() != (modelo.getId())) {
            throw new RuntimeException("Não foi possivel identificar o registro informado");
        }


        List<Veiculo> veiculoLista = this.modeloRepository.findVeiculo(modelo);

        if (veiculoLista.isEmpty()) {
            if(modelo.isAtivo()) {
                modelo.setAtivo(false);
                this.modeloRepository.save(modelo);
                return ResponseEntity.ok("Desativado com sucesso");
            }
            this.modeloRepository.deleteById(id);
            return ResponseEntity.ok("Deletado com sucesso");
        }

        if(!modelo.isAtivo()) {
            Assert.isTrue(veiculoLista.isEmpty(), "Modelo vinculado a um veiculo");
            this.modeloRepository.deleteById(id);
        }


        modelo.setAtivo(false);
        this.modeloRepository.save(modelo);
        return ResponseEntity.ok("Desativado com sucesso");

    }



}