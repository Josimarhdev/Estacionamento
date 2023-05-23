package br.com.uniamerica.estacionamento.service;


import br.com.uniamerica.estacionamento.entity.Modelo;
//import jakarta.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ModeloService {






    @Transactional( rollbackFor = Exception.class )
    public void cadastrar(final Modelo modelo){




    }
}
