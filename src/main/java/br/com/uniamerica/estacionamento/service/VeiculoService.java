package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        Assert.isTrue(veiculo.getPlaca().length() <= 8 ,"A placa deve ter no máximo 7 digitos") ;
        Assert.isTrue(veiculo.getPlaca().length() >= 8 ,"A placa deve ter no mínimo 7 digitos") ;
        Veiculo veiculoExistente = veiculoRepository.findByPlaca(veiculo.getPlaca());
        Assert.isTrue(veiculoExistente == null || veiculoExistente.equals(veiculo),"Veiculo já existente");

        Assert.isTrue(!veiculo.getCor().equals(""),"A cor do veiculo não pode ser nula!");


        Assert.isTrue(veiculo.getAno() != 0, "Ano não pode ser nulo");

        Assert.isTrue(!veiculo.getModelo().equals(""),"O modelo não pode ser nulo!");

        Assert.isTrue(!veiculo.getTipo().equals(""),"O tipo não pode ser nulo!");


        veiculo.setAtivo(true);
        this.veiculoRepository.save(veiculo);
    }

    public void AtualizaVeiculo (final Veiculo veiculo){
        Assert.isTrue(!veiculo.getPlaca().equals(""),"A placa não pode ser nula!");
        Assert.isTrue(veiculo.getPlaca().length() <= 8 ,"A placa deve ter no máximo 7 digitos") ;
        Assert.isTrue(veiculo.getPlaca().length() >= 8 ,"A placa deve ter no mínimo 7 digitos") ;


        Assert.isTrue(!veiculo.getCor().equals(""),"A cor do veiculo não pode ser nula!");


        Assert.isTrue(veiculo.getAno() != 0, "Ano não pode ser nulo");

        Assert.isTrue(!veiculo.getModelo().equals(""),"O modelo não pode ser nulo!");

        Assert.isTrue(!veiculo.getTipo().equals(""),"O tipo não pode ser nulo!");


        veiculo.setAtivo(true);
        this.veiculoRepository.save(veiculo);
    }




    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Optional<Veiculo>  veiculoOptional = veiculoRepository.findById(id);
        if (veiculoOptional.isPresent()) {
            Veiculo veiculo = veiculoOptional.get();
            if (!veiculo.isAtivo()) {
                veiculoRepository.delete(veiculo);
                return ResponseEntity.ok("Deletado com sucesso");
            }
            else {
                veiculo.setAtivo(false);
                veiculoRepository.save(veiculo);
                return ResponseEntity.ok("Desativado com sucesso");
            }
        }
        return ResponseEntity.ok("Não foi encontrado nenhum registro");
    }






}