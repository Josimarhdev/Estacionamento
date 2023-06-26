package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {
    Configuracao findByValorHora(float valorHora);
}
