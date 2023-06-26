package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
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


    @GetMapping("/{id}")
    public ResponseEntity<Modelo> findById(@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Modelo());
    }

    // http://localhost:8080/api/modelo/1

    // http://localhost:8080/api/modelo?id=1


    @GetMapping("/lista")
    public ResponseEntity <?> ListaCompleta(){
        return ResponseEntity.ok(this.modeloRep.findAll());

    }

    @GetMapping("/{ativo}")
    public ResponseEntity <?> ativado(@PathVariable("ativo") boolean ativo){
        return ResponseEntity.ok(ativo = true);
    }

    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Modelo modelo){
        try {
            this.modeloRep.save(modelo);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Modelo modelo){
        try {
            final Modelo modelo1 = this.modeloRep.findById(id).orElse(null);

            if (modelo1 == null || modelo1.getId().equals(modelo.getId())){
                throw new RuntimeException("Nao foi possivel identificar o registro informado");
            }
            this.modeloRep.save(modelo);
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
