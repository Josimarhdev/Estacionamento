package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.config.ValidaCPF;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class  CondutorService {



    @Autowired
    private ValidaCPF validaCPF;
    @Autowired
    private CondutorRepository condutorRepository;
    @Transactional(rollbackFor = Exception.class) //propagation


    public void VerificarCondutor (final Condutor condutor){
        Assert.isTrue(!condutor.getNome().equals(""),"O nome do condutor não pode nulo!");
        Assert.isTrue(condutor.getNome().length() <= 100 ,"O nome deve ter no máximo 100 digitos") ;



        if (this.validaCPF.isCPF(condutor.getCpf()) == true) {
            System.out.printf("%s\n", this.validaCPF.imprimeCPF(condutor.getCpf()));
        }
        else {
            int x = 2;
            System.out.printf("Erro, CPF invalido !!!\n");
            Assert.isTrue(x == 1, "Cpf inválido");

        }
        Condutor cpfExistente = condutorRepository.findByCpf(condutor.getCpf());
        Assert.isTrue(cpfExistente == null || cpfExistente.equals(condutor),"Condutor já cadastrado!");

        Assert.isTrue(!condutor.getTelefone().equals(""),"O telefone não pode ser nulo!");
        Assert.isTrue(condutor.getTelefone().length() == 11 ,"O numero deve ter 11 digitos, contando o DDD") ;
        Condutor telefoneExistente = condutorRepository.findByTelefone(condutor.getTelefone());
        Assert.isTrue(condutor.getTelefone().substring(0,11).matches("[0-9]*"),"Telefone deve conter apenas números!");
        Assert.isTrue(telefoneExistente == null || telefoneExistente.equals(condutor),"Telefone já cadastrado!");

        condutor.setAtivo(true);
        this.condutorRepository.save(condutor);
    }

    public void atualizaCondutor (Condutor condutor){
        Assert.isTrue(!condutor.getNome().equals(""),"O nome do condutor não pode nulo!");
        Assert.isTrue(condutor.getNome().length() <= 100 ,"O nome deve ter no máximo 100 digitos") ;



        if (this.validaCPF.isCPF(condutor.getCpf()) == true) {
            System.out.printf("%s\n", this.validaCPF.imprimeCPF(condutor.getCpf()));
        }
        else {
            int x = 2;
            System.out.printf("Erro, CPF invalido !!!\n");
            Assert.isTrue(x == 1, "Cpf inválido");

        }


        Assert.isTrue(!condutor.getTelefone().equals(""),"O telefone não pode ser nulo!");
        Assert.isTrue(condutor.getTelefone().length() == 11 ,"O numero deve ter 11 digitos, contando o DDD") ;

        Assert.isTrue(condutor.getTelefone().substring(0,11).matches("[0-9]*"),"Telefone deve conter apenas números!");

        condutor.setAtivo(true);
        this.condutorRepository.save(condutor);
    }




    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Optional<Condutor> condutorOptional = condutorRepository.findById(id);
        if (condutorOptional.isPresent()) {
            Condutor condutor = condutorOptional.get();
            if (!condutor.isAtivo()) {
                condutorRepository.delete(condutor);
                return ResponseEntity.ok("Deletado com sucesso");
            }
            else {
                condutor.setAtivo(false);
                condutorRepository.save(condutor);
                return ResponseEntity.ok("Desativado com sucesso");
            }
        }
        return ResponseEntity.ok("Não foi encontrado nenhum registro");
    }





    }

