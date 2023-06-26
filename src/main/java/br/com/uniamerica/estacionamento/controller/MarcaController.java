package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/marca")
public class MarcaController {

    @Autowired
    private MarcaRepository marcaRep;

    @Autowired
    private MarcaService marcaServ;

    @Autowired
    private MovimentacaoRepository movimentacaoRep;


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") final Long id){
        final Marca marca = this.marcaRep.findById(id).orElse(null);
        return ResponseEntity.ok(marca);
    }



    @GetMapping("/lista")
    public ResponseEntity <?> ListaCompleta(){
        return ResponseEntity.ok(this.marcaRep.findAll());

    }

    @GetMapping("/ativos/{ativo}")
    public ResponseEntity <?> ativo(@PathVariable("ativo") boolean ativo){
        if(!ativo){
            return ResponseEntity.ok(marcaRep.findByAtivo(false));
        }
        return ResponseEntity.ok(marcaRep.findByAtivo(true));
    }

    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Marca marca){
        try {
            marcaServ.VerificarMarca(marca);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(
            @PathVariable("id") final Long id,
            @RequestBody final Marca marca
    ){
        try {
            this.marcaServ.VerificarMarca(marca);
            return ResponseEntity.ok("Registro atualizado com sucesso. ");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") final Long id
    ){
        try {
            this.marcaServ.deletar(id);
            return ResponseEntity.ok("Registro excluido com sucesso.");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }



}
