package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.service.VeiculoService;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRep;


    @Autowired
    private VeiculoService veiculoServ;

    @Autowired
    private MovimentacaoRepository movimentacaoRep;


    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> findById(@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Veiculo());
    }



    @GetMapping("/lista")
    public ResponseEntity <?> ListaCompleta(){
        return ResponseEntity.ok(this.veiculoRep.findAll());

    }


    @GetMapping("/ativo")
    public ResponseEntity <?> ativo(@PathVariable("ativo") boolean ativo){
        if(!ativo){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new Movimentacao());
    }
    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Veiculo veiculo){
        try {
            veiculoServ.VerificarVeiculo(veiculo);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Veiculo veiculo){
        try {
            final Veiculo veiculo1 = this.veiculoRep.findById(id).orElse(null);

            if (veiculo1 == null || !veiculo1.getId().equals(veiculo.getId())){
                throw new RuntimeException("Registro não identificado");
            }
            this.veiculoRep.save(veiculo);
            return ResponseEntity.ok("O registro foi cadastrado com sucesso");
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
    public void deletaVeiculo(@PathVariable Long id) {
        this.veiculoServ.deletar(id);
    }
}
