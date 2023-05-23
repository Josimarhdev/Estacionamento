package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.config.ValidaCPF;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        Assert.isTrue(condutor.getTelefone().substring(0,11).matches("[0-9]*"),"Telefone deve conter apenas números!");

        Assert.isTrue(!condutor.getCpf().equals(""),"O cpf não pode ser nulo!");
        Assert.isTrue(condutor.getCpf().length() <= 20 ,"O cpf deve ter no máximo 20 dígitos") ;
        Condutor cpfExistente = condutorRepository.findByCpf(condutor.getCpf());
        Assert.isTrue(cpfExistente == null || cpfExistente.equals(condutor),"Condutor já cadastrado!");

        Assert.isTrue(!condutor.getTelefone().equals(""),"O telefone não pode ser nulo!");
        Assert.isTrue(condutor.getTelefone().length() == 11 ,"O numero deve ter 11 digitos, contando o DDD") ;
        Condutor telefoneExistente = condutorRepository.findByTelefone(condutor.getTelefone());
        Assert.isTrue(telefoneExistente == null || telefoneExistente.equals(condutor),"Telefone já cadastrado!");

        this.condutorRepository.save(condutor);
    }

    public void atualizaCondutor (Condutor condutor){
        final Condutor condutorBancoDeDados = this.condutorRepository.findById(condutor.getId()).orElse(null);
        condutor.setCadastro(condutorBancoDeDados.getCadastro());


        condutor.setAtivo(true);

        this.condutorRepository.save(condutor);
    }

    public void deletar(@PathVariable Long id) {
        Optional<Condutor> condutorOptional = condutorRepository.findById(id);
        if (condutorOptional.isPresent()) {
            Condutor condutor = condutorOptional.get();
            if (!condutor.isAtivo()) { // Se ativo for false, deleta o condutor
                condutorRepository.delete(condutor);
            } else { // Se ativo for true, atualiza para false e depois deleta o condutor
                condutor.setAtivo(false);
                condutorRepository.save(condutor);
            }
        }
    }





    }

