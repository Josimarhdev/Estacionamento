package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/api/configuracao")
public class ConfiguracaoController {

    @Autowired
    private ConfiguracaoRepository configuracaoRep;


    @GetMapping("/{id}")
    public ResponseEntity<Configuracao> findById(@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Configuracao());
    }




    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Configuracao configuracao){
        try {
            this.configuracaoRep.save(configuracao);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Configuracao configuracao){
        try {
            final Configuracao configuracao1 = this.configuracaoRep.findById(id).orElse(null);

            if (configuracao1 == null || !configuracao1.getId().equals(configuracao.getId())){
                throw new RuntimeException("Nao foi possivel identificar o registro informado");
            }
            this.configuracaoRep.save(configuracao);
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
