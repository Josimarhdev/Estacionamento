package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import br.com.uniamerica.estacionamento.service.CondutorService;

import java.util.Optional;

@Controller
@RequestMapping(value = "/api/condutor")
public class CondutorController {

    @Autowired
    private CondutorRepository condutorRep;

    @Autowired
    private CondutorService condutorserv;

    @Autowired
    private MovimentacaoRepository movimentacaoRep;


    @GetMapping("/{id}")
    public ResponseEntity<Condutor> findById(@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Condutor());
    }




    @GetMapping("/lista")
    public ResponseEntity <?> ListaCompleta(){
        return ResponseEntity.ok(this.condutorRep.findAll());

    }

    @GetMapping("/ativo")
    public ResponseEntity <?> ativo(@PathVariable("ativo") boolean ativo){
        if(!ativo){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new Movimentacao());
    }




    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Condutor condutor){
        try {
            condutorserv.VerificarCondutor(condutor);

            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Condutor condutor){
        try {
            final Condutor condutor1 = this.condutorRep.findById(id).orElse(null);

            if (condutor1 == null || !condutor1.getId().equals(condutor.getId())){
                throw new RuntimeException("Registro não identificado");
            }
            this.condutorRep.save(condutor);
            return ResponseEntity.ok("O registro foi Cadastrado com Sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }

    }

    @DeleteMapping("delete/{id}")
    public void deletaCondutor(@PathVariable Long id) {
       this.condutorserv.deletar(id);
    }
}