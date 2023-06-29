package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    @Query("from Movimentacao where veiculo = :veiculo")
    public List<Movimentacao> findMovimentacao (@Param("veiculo") final Veiculo veiculo);

    Veiculo findByPlaca(String placa);

    List<Veiculo> findByAtivo(boolean ativo);
}
