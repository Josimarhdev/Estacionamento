package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ConfiguracaoService configuracaoServc;

    @Autowired
    private ConfiguracaoRepository configuracaoRep;

    @Transactional(rollbackFor = Exception.class)
    public void VerificarMovimentacao(final Movimentacao movimentacao) {
        Assert.isTrue(movimentacao.getVeiculo() != null, "O veiculo nao pode ser nulo!");


        Assert.isTrue(movimentacao.getCondutor() != null, "O condutor nao pode ser nulo!");

        movimentacao.setAtivo(true);
        movimentacao.setEntrada(LocalDateTime.now());
        this.movimentacaoRepository.save(movimentacao);
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> AtualizaMovimentacao(Movimentacao movimentacao, Long id) {


        Assert.isTrue(movimentacao.getSaida() == null,"Saída já registrada");

        movimentacao.setSaida(LocalDateTime.now());

        final Movimentacao movimentacao1 = this.movimentacaoRepository.findById(id).orElse(null);

        Duration tempoEntreD = Duration.between(movimentacao.getEntrada(), movimentacao.getSaida());
        double horasUtilizadas = tempoEntreD.toMinutes() / 60.0;



        Configuracao configuracao = this.configuracaoRep.findById(1L).orElse(null);

        float valorHr = configuracao.getValorHora();

        Assert.isTrue(valorHr != 0.0, "Por favor, adicione um valor por hora");


        double valorTotal = horasUtilizadas * valorHr;



        movimentacao.setAtivo(false);
        this.movimentacaoRepository.save(movimentacao);

        return ResponseEntity.ok("Horas utilizadas: " + horasUtilizadas +
                "\nValor total: " + valorTotal +
                "\nHorario de entrada: " + movimentacao.getEntrada() +
                "\nHorario de saida: " + movimentacao.getSaida() +
                "\nPlaca do veiculo: " + movimentacao1.getVeiculo().getPlaca() +
                "\nMarca: " + movimentacao1.getVeiculo().getModelo().getMarca().getNome() +
                "\nModelo: " + movimentacao1.getVeiculo().getModelo().getNome() +
                "\nCondutor: " + movimentacao1.getCondutor().getNome());
    }

    @Transactional(rollbackFor = Exception.class)



    public ResponseEntity<?> deletar(@PathVariable Long id) {


        Optional<Movimentacao> movimentacaoOptional = movimentacaoRepository.findById(id);
        if (movimentacaoOptional.isPresent()) {
            Movimentacao movimentacao = movimentacaoOptional.get();
            Assert.isTrue(movimentacao.getAtivo()!=(false),"Movimentação já desativada");
            movimentacao.setAtivo(false);
            movimentacaoRepository.save(movimentacao);
        }
        return ResponseEntity.ok("Desativado com sucesso");
    }



}
