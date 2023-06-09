package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ModeloRepository extends JpaRepository<Modelo, Long>{


    Modelo findByMarca(Marca marca);

    Modelo findByNome(String nome);
}
