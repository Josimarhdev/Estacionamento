package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoRepository movimentacaoRep;


    @GetMapping("/{id}")
    public ResponseEntity<Movimentacao> findById(@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Movimentacao());
    }



    @GetMapping("/lista")
    public ResponseEntity <?> ListaCompleta(){
        return ResponseEntity.ok(this.movimentacaoRep.findAll());

    }
    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Movimentacao movimentacao){
        try {
            this.movimentacaoRep.save(movimentacao);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Movimentacao movimentacao){
        try {
            final Movimentacao movimentacao1 = this.movimentacaoRep.findById(id).orElse(null);

            if (movimentacao1 == null || movimentacao1.getId().equals(movimentacao.getId())){
                throw new RuntimeException("Nao foi possivel identificar o registro informado");
            }
            this.movimentacaoRep.save(movimentacao);
            return ResponseEntity.ok("Registro Cadastrado com Sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }

    }





}
