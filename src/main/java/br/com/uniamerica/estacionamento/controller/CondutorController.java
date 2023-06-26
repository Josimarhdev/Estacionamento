package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Marca;
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
    public ResponseEntity<?> findById(@PathVariable("id") final Long id) {
        final Condutor condutor = this.condutorRep.findById(id).orElse(null);
        return ResponseEntity.ok(condutor);
    }


    @GetMapping("/lista")
    public ResponseEntity<?> ListaCompleta() {
        return ResponseEntity.ok(this.condutorRep.findAll());

    }

    @GetMapping("/ativos/{ativo}")
    public ResponseEntity<?> ativo(@PathVariable("ativo") boolean ativo) {
        if (!ativo) {
            return ResponseEntity.ok(condutorRep.findByAtivo(false));
        }
        return ResponseEntity.ok(condutorRep.findByAtivo(true));
    }


    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Condutor condutor) {
        try {
            condutorserv.VerificarCondutor(condutor);

            return ResponseEntity.ok("Registro cadastrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(
            @PathVariable("id") final Long id,
            @RequestBody final Condutor condutor
    ) {
        try {
            this.condutorserv.atualizaCondutor(condutor);
            return ResponseEntity.ok("Registro atualizado com sucesso. ");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") final Long id
    ) {
        try {
            this.condutorserv.deletar(id);
            return ResponseEntity.ok("Registro excluido com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}

