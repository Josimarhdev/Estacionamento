package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CondutorRepository extends JpaRepository<Condutor, Long> {


    @Query("from Movimentacao where condutor = :condutor")
    public List<Movimentacao> findMovimentacao (@Param("condutor") final Condutor condutor);

    Condutor findByTelefone(String telefone);

    Condutor findByCpf(String cpf);

    List<Condutor> findByAtivo(boolean ativo);
}


