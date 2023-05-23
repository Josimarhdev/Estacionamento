package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service

public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Transactional(rollbackFor = Exception.class)
    public void VerificarMovimentacao (final Movimentacao movimentacao){
        Assert.isTrue(!movimentacao.getVeiculo().equals(""),"O veiculo nao pode ser nulo!");
        Movimentacao veiculoExistente = movimentacaoRepository.findByVeiculo(movimentacao.getVeiculo());
        Assert.isTrue(veiculoExistente == null || veiculoExistente.equals(movimentacao),"Veiculo já existente");

        Assert.isTrue(!movimentacao.getCondutor().equals(""),"O condutor nao pode ser nulo!");

        Assert.isTrue(!movimentacao.getEntrada().equals(""),"A entrada não pode ser nula!");


        movimentacao.setAtivo(true);
        this.movimentacaoRepository.save(movimentacao);

    }






    public void deletar(@PathVariable Long id) {
        Optional<Movimentacao> movimentacaoOptional = movimentacaoRepository.findById(id);
        if (movimentacaoOptional.isPresent()) {
            Movimentacao movimentacao = movimentacaoOptional.get();

                movimentacao.setAtivo(false);
                movimentacaoRepository.save(movimentacao);
            }
    }




    }