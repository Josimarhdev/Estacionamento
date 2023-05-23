package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/movimentacao")
public class MovimentacaoController {


    @Autowired
    private MovimentacaoService movimentacaoServ;
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

    @GetMapping("/abertas")
    public ResponseEntity <?> aberta(@PathVariable("aberta") boolean aberta){
        if(!aberta){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new Movimentacao());
    }
    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Movimentacao movimentacao){
        try {
            movimentacaoServ.VerificarMovimentacao(movimentacao);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Movimentacao movimentacao){
        try {
            final Movimentacao movimentacao1 = this.movimentacaoRep.findById(id).orElse(null);

            if (movimentacao1 == null || !movimentacao1.getId().equals(movimentacao.getId())){
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

    @DeleteMapping("delete/{id}")
    public void deletaMovimentacao(@PathVariable Long id) {
        this.movimentacaoServ.deletar(id);
    }


}
