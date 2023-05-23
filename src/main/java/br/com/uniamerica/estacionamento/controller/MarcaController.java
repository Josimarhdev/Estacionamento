package br.com.uniamerica.estacionamento.controller;

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
    public ResponseEntity<Marca> findById(@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Marca());
    }



    @GetMapping("/lista")
    public ResponseEntity <?> ListaCompleta(){
        return ResponseEntity.ok(this.marcaRep.findAll());

    }

    @GetMapping("/ativo")
    public ResponseEntity <?> ativo(@PathVariable("ativo") boolean ativo){
        if(!ativo){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new Movimentacao());
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

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Marca marca){
        try {
            final Marca marca1 = this.marcaRep.findById(id).orElse(null);

            if (marca1 == null || !marca1.getId().equals(marca.getId())){
                throw new RuntimeException("Registro não identificado");
            }
            this.marcaRep.save(marca);
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
    public void deletaMarca(@PathVariable Long id) {
        this.marcaServ.deletar(id);
    }



}
