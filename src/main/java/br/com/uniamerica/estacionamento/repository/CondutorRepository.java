package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CondutorRepository extends JpaRepository<Condutor, Long> {


    Condutor findByTelefone(String telefone);

    Condutor findByCpf(String cpf);
}


