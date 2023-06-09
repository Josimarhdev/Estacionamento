package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "veiculo", schema = "public")
public class    Veiculo extends AbstractEntity {
    @Getter @Setter
    @Column(name = "placa",nullable = false, unique = true, length = 10)
    private String placa;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modelo", nullable = false)
    private Modelo modelo;

    @Getter @Setter
    @Column(name = "ano",nullable = false)
    private int ano;


    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name= "cor", length = 20, nullable = false)
    private Cor cor;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name= "tipo", length = 20, nullable = false)
    private Tipo tipo;

}


