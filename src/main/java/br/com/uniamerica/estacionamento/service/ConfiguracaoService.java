package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ConfiguracaoService {


    @Autowired
    ConfiguracaoRepository configuracaoRep;


    public static float valorHorass;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrarConfig(Configuracao configuracao){




        Configuracao configuracao1 = configuracaoRep.findByValorHora(configuracao.getValorHora());

        valorHorass = configuracao1.getValorHora();

        System.out.println(configuracao1.getValorHora());

    }


}