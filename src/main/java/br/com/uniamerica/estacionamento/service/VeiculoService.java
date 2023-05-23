package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class VeiculoService {


    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional(rollbackFor = Exception.class)

    public void VerificarVeiculo (final Veiculo veiculo){
        Assert.isTrue(!veiculo.getPlaca().equals(""),"A placa não pode ser nula!");
        Assert.isTrue(veiculo.getPlaca().length() <= 10 ,"A placa deve ter no máximo 10 digitos") ;
        Veiculo veiculoExistente = veiculoRepository.findByPlaca(veiculo.getPlaca());
        Assert.isTrue(veiculoExistente == null || veiculoExistente.equals(veiculo),"Veiculo já existente");

        Assert.isTrue(!veiculo.getCor().equals(""),"A cor do veiculo não pode ser nula!");
        //Assert.isTrue(veiculo.getCor().length() <= 20 ,"A cor do veiculo deve conter no maximo 20 digitos!") ;

        //Assert.isTrue(!veiculo.getAno().equals(""),"O ano não pode ser nulo!");

        Assert.isTrue(!veiculo.getModelo().equals(""),"O modelo não pode ser nulo!");

        Assert.isTrue(!veiculo.getTipo().equals(""),"O tipo não pode ser nulo!");
        //Assert.isTrue(veiculo.getTipo().length() <= 20,"O tipo deve conter até 20 digitos") ;




        veiculo.setAtivo(true);
        this.veiculoRepository.save(veiculo);
    }



    public void deletar(@PathVariable Long id) {
        Optional<Veiculo> veiculoOptional = veiculoRepository.findById(id);
        if (veiculoOptional.isPresent()) {
            Veiculo veiculo = veiculoOptional.get();
            if (!veiculo.isAtivo()) {
                veiculoRepository.delete(veiculo);
            } else {
                veiculo.setAtivo(false);
                veiculoRepository.save(veiculo);
            }
        }
    }






}