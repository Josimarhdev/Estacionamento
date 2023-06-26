package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Duration;
import java.util.Optional;

@Service

public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ConfiguracaoService configuracaoServc;

    @Transactional(rollbackFor = Exception.class)
    public void VerificarMovimentacao (final Movimentacao movimentacao){
        Assert.isTrue(movimentacao.getVeiculo()!=null,"O veiculo nao pode ser nulo!");
        Movimentacao veiculoExistente = movimentacaoRepository.findByVeiculo(movimentacao.getVeiculo());
        Assert.isTrue(veiculoExistente == null || veiculoExistente.equals(movimentacao),"Veiculo já existente");

        Assert.isTrue(movimentacao.getCondutor()!=null,"O condutor nao pode ser nulo!");

        Assert.isTrue(!movimentacao.getEntrada().equals(""),"A entrada não pode ser nula!");

        movimentacao.setAtivo(true);
        this.movimentacaoRepository.save(movimentacao);

    }


    public ResponseEntity<?> AtualizaMovimentacao(Movimentacao movimentacao, Long id){


        final Movimentacao movimentacao1 = this.movimentacaoRepository.findById(id).orElse(null);


        Duration TempoEntreD = Duration.between(movimentacao.getEntrada(), movimentacao.getSaida());

        String HorasS = String.format("%02d", TempoEntreD.toHoursPart());
        String MinutosS = String.format("%02d", TempoEntreD.toMinutesPart());
        String SegundosS = String.format("%02d", TempoEntreD.toSecondsPart());

        float minPraH = Float.parseFloat(MinutosS);
        float minutosPraHoras = (minPraH/60);

        float secPraH = Float.parseFloat(SegundosS);
        float segundosPraHoras = (secPraH/3600);

        float horasPraHoras = Float.parseFloat(HorasS);



        float valorHr = configuracaoServc.valorHorass;
        System.out.println(configuracaoServc.valorHorass);

        Assert.isTrue(valorHr != 0.0, "Por favor, adicione um valor por hora");

        float Valor = (minutosPraHoras + segundosPraHoras + horasPraHoras ) * valorHr;

        System.out.println(Valor);

        this.movimentacaoRepository.save(movimentacao);




        return ResponseEntity.ok("Horas utilizadas: "+ (secPraH + minPraH + horasPraHoras) + "\n Valor total:"
        + Valor + "\n Horario de entrada: " + movimentacao.getEntrada() + "\n Horario de saida: " + movimentacao.getSaida() + "\n Placa do veiculo: " + movimentacao1.getVeiculo().getPlaca() +
                "\nMarca: " + movimentacao1.getVeiculo().getModelo().getMarca().getNome() + "\nModelo: " + movimentacao1.getVeiculo().getModelo().getNome() + "\nCondutor: " + movimentacao1.getCondutor().getNome());


    }



    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Optional<Movimentacao> movimentacaoOptional = movimentacaoRepository.findById(id);
        if (movimentacaoOptional.isPresent()) {
            Movimentacao movimentacao = movimentacaoOptional.get();

                movimentacao.setAtivo(false);
                movimentacaoRepository.save(movimentacao);
            }
        return ResponseEntity.ok("Desativado com sucesso");
    }




    }