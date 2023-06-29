package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {


    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional(rollbackFor = Exception.class)

    public void VerificarVeiculo (final Veiculo veiculo){
        Assert.isTrue(veiculo.getPlaca()!= null,"A placa não pode ser nula!");
        Assert.isTrue(!veiculo.getPlaca().equals(""),"A placa não pode ser nula!");
        Assert.isTrue(veiculo.getPlaca().length() <= 8 ,"A placa deve ter 7 digitos") ;
        Assert.isTrue(veiculo.getPlaca().length() >= 8 ,"A placa deve ter 7 digitos") ;
        Veiculo veiculoExistente = veiculoRepository.findByPlaca(veiculo.getPlaca());
        Assert.isTrue(veiculoExistente == null || veiculoExistente.equals(veiculo),"Veiculo já existente");

        Assert.isTrue(veiculo.getCor()!=null,"A cor do veiculo não pode ser nula!");


        Assert.isTrue(veiculo.getAno() != 0, "Ano não pode ser nulo");

        Assert.isTrue(veiculo.getModelo()!=null,"O modelo não pode ser nulo!");

        Assert.isTrue(veiculo.getTipo()!=null,"O tipo não pode ser nulo!");


        veiculo.setAtivo(true);
        this.veiculoRepository.save(veiculo);
    }


    @Transactional(rollbackFor = Exception.class)
    public void AtualizaVeiculo (final Veiculo veiculo){
        Assert.isTrue(veiculo.getPlaca()!=null,"A placa não pode ser nula!");
        Assert.isTrue(veiculo.getPlaca().length() <= 8 ,"A placa deve ter 7 digitos") ;
        Assert.isTrue(veiculo.getPlaca().length() >= 8 ,"A placa deve ter 7 digitos") ;


        Assert.isTrue(veiculo.getCor()!=null,"A cor do veiculo não pode ser nula!");


        Assert.isTrue(veiculo.getAno() != 0, "Ano não pode ser nulo");

        Assert.isTrue(veiculo.getModelo()!= null,"O modelo não pode ser nulo!");

        Assert.isTrue(veiculo.getTipo()!=null,"O tipo não pode ser nulo!");


        veiculo.setAtivo(true);
        this.veiculoRepository.save(veiculo);
    }





    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deletar(Long id) {
        Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);

        if (veiculo == null || veiculo.getId() != (veiculo.getId())) {
            throw new RuntimeException("Não foi possivel identificar o registro informado");
        }


        List<Movimentacao> movimentacaoLista = this.veiculoRepository.findMovimentacao(veiculo);

        if (movimentacaoLista.isEmpty()) {
            if(veiculo.isAtivo()) {
                veiculo.setAtivo(false);
                this.veiculoRepository.save(veiculo);
                return ResponseEntity.ok("Desativado com sucesso");
            }
            this.veiculoRepository.deleteById(id);
            return ResponseEntity.ok("Deletado com sucesso");
        }

        if(!veiculo.isAtivo()) {
            Assert.isTrue(movimentacaoLista.isEmpty(), "Veiculo vinculado a uma movimentacao");
            this.veiculoRepository.deleteById(id);
        }


        veiculo.setAtivo(false);
        this.veiculoRepository.save(veiculo);
        return ResponseEntity.ok("Desativado com sucesso");

    }






}