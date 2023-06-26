package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/modelo")
public class ModeloController {

    @Autowired
    private ModeloRepository modeloRep;

    @Autowired
    private ModeloService modeloServ;

    @Autowired
    private MovimentacaoRepository movimentacaoRep;


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") final Long id){
        final Modelo modelo = this.modeloRep.findById(id).orElse(null);
        return ResponseEntity.ok(modelo);
    }

    // http://localhost:8080/api/modelo/1

    // http://localhost:8080/api/modelo?id=1


    @GetMapping("/lista")
    public ResponseEntity <?> ListaCompleta(){
        return ResponseEntity.ok(this.modeloRep.findAll());

    }

    @GetMapping("/ativos/{ativo}")
    public ResponseEntity <?> ativo(@PathVariable("ativo") boolean ativo){
        if(!ativo){
            return ResponseEntity.ok(modeloRep.findByAtivo(false));
        }
        return ResponseEntity.ok(modeloRep.findByAtivo(true));
    }


    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Modelo modelo){
        try {
            this.modeloServ.VerificarModelo(modelo);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(
            @PathVariable("id") final Long id,
            @RequestBody final Modelo modelo
    ) {
        try {
            this.modeloServ.AtualizaModelo(modelo);
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
            this.modeloServ.deletar(id);
            return ResponseEntity.ok("Registro excluido com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
