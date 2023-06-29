package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.config.ValidaCPF;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class  CondutorService {



    @Autowired
    private ValidaCPF validaCPF;
    @Autowired
    private CondutorRepository condutorRepository;
    @Transactional(rollbackFor = Exception.class) //propagation


    public void VerificarCondutor (final Condutor condutor){
        Assert.isTrue(condutor.getNome()!=null,"O nome do condutor não pode nulo!");
        Assert.isTrue(!condutor.getNome().equals(""),"O nome do condutor não pode nulo!");
        Assert.isTrue(condutor.getNome().length() <= 100 ,"O nome deve ter no máximo 100 digitos") ;
        Assert.isTrue(condutor.getCpf()!=null,"O cpf do condutor não pode nulo!");
        Assert.isTrue(!condutor.getCpf().equals(""),"O cpf do condutor não pode nulo!");




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

        Assert.isTrue(condutor.getTelefone()!=null,"O telefone não pode ser nulo!");
        Assert.isTrue(condutor.getTelefone().length() == 11 ,"O numero deve ter 11 digitos, contando o DDD") ;
        Condutor telefoneExistente = condutorRepository.findByTelefone(condutor.getTelefone());
        Assert.isTrue(condutor.getTelefone().substring(0,11).matches("[0-9]*"),"Telefone deve conter apenas números!");
        Assert.isTrue(telefoneExistente == null || telefoneExistente.equals(condutor),"Telefone já cadastrado!");

        condutor.setAtivo(true);
        this.condutorRepository.save(condutor);
    }


    @Transactional(rollbackFor = Exception.class)

    public void atualizaCondutor (Condutor condutor){
        Assert.isTrue(condutor.getNome()!=null,"O nome do condutor não pode nulo!");
        Assert.isTrue(!condutor.getNome().equals(""),"O nome do condutor não pode nulo!");
        Assert.isTrue(condutor.getNome().length() <= 100 ,"O nome deve ter no máximo 100 digitos") ;
        Assert.isTrue(condutor.getCpf()!=null,"O cpf do condutor não pode nulo!");
        Assert.isTrue(!condutor.getCpf().equals(""),"O cpf do condutor não pode nulo!");


        if (this.validaCPF.isCPF(condutor.getCpf()) == true) {
            System.out.printf("%s\n", this.validaCPF.imprimeCPF(condutor.getCpf()));
        }
        else {
            int x = 2;
            System.out.printf("Erro, CPF invalido !!!\n");
            Assert.isTrue(x == 1, "Cpf inválido");

        }


        Assert.isTrue(condutor.getTelefone()!=null,"O telefone não pode ser nulo!");
        Assert.isTrue(condutor.getTelefone().length() == 11 ,"O numero deve ter 11 digitos, contando o DDD") ;

        Assert.isTrue(condutor.getTelefone().substring(0,11).matches("[0-9]*"),"Telefone deve conter apenas números!");

        condutor.setAtivo(true);
        this.condutorRepository.save(condutor);
    }



    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deletar(Long id) {
        Condutor condutor = this.condutorRepository.findById(id).orElse(null);

        if (condutor == null || condutor.getId() != (condutor.getId())) {
            throw new RuntimeException("Não foi possivel identificar o registro informado");
        }


        List<Movimentacao> movimentacaoLista = this.condutorRepository.findMovimentacao(condutor);

        if (movimentacaoLista.isEmpty()) {
            if(condutor.isAtivo()) {
                condutor.setAtivo(false);
                this.condutorRepository.save(condutor);
                return ResponseEntity.ok("Desativado com sucesso");
            }
            this.condutorRepository.deleteById(id);
            return ResponseEntity.ok("Deletado com sucesso");
        }

        if(!condutor.isAtivo()) {
            Assert.isTrue(movimentacaoLista.isEmpty(), "Condutor vinculado a uma movimentacao");
            this.condutorRepository.deleteById(id);
        }


        condutor.setAtivo(false);
        this.condutorRepository.save(condutor);
        return ResponseEntity.ok("Desativado com sucesso");

    }






    }

