package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
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


    @GetMapping("/{id}")
    public ResponseEntity<Marca> findById(@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Marca());
    }



    @GetMapping("/lista")
    public ResponseEntity <?> ListaCompleta(){
        return ResponseEntity.ok(this.marcaRep.findAll());

    }

    @GetMapping("/{ativo}")
    public ResponseEntity <?> ativado(@PathVariable("ativo") boolean ativo){
        return ResponseEntity.ok(ativo = true);
    }
    @PostMapping
    public ResponseEntity <?> cadastrar(@RequestBody final Marca marca){
        try {
            this.marcaRep.save(marca);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id, @RequestBody final Marca marca){
        try {
            final Marca marca1 = this.marcaRep.findById(id).orElse(null);

            if (marca1 == null || marca1.getId().equals(marca.getId())){
                throw new RuntimeException("Nao foi possivel identificar o registro informado");
            }
            this.marcaRep.save(marca);
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
