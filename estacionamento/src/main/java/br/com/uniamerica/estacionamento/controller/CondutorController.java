package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/condutor")
public class CondutorController {

    @Autowired
    private CondutorRepository condutorRep;


    @GetMapping("/{id}")
    public ResponseEntity<Condutor> findById(@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Condutor());
    }




    @GetMapping("/lista")
    public ResponseEntity <?> ListaCompleta(){
        return ResponseEntity.ok(this.condutorRep.findAll());

    }

    @GetMapping("/{ativo}")
    public ResponseEntity <?> ativado(@PathVariable("ativo") boolean ativo){
        return ResponseEntity.ok(ativo = true);
    }





    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Condutor condutor){
        try {
            this.condutorRep.save(condutor);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Condutor condutor){
        try {
            final Condutor condutor1 = this.condutorRep.findById(id).orElse(null);

            if (condutor1 == null || condutor1.getId().equals(condutor.getId())){
                throw new RuntimeException("Nao foi possivel identificar o registro informado");
            }
            this.condutorRep.save(condutor);
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