package br.com.uniamerica.estacionamento.entity;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@MappedSuperclass

public class AbstractEntity {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id",nullable = false, unique = true)
    private Long id;
    @Getter @Setter
    @Column(name="dtCadastro",nullable = false)
private LocalDateTime cadastro;

    @Column(name="dtEdicao")
    @Getter @Setter
private LocalDateTime edicao;
    @Getter @Setter
    @Column(name="ativo",nullable = false)
private Boolean ativo;

    @PrePersist
    private void proPersist(){
        this.cadastro = LocalDateTime.now();
        this.ativo = true;
    }

    @PreUpdate
    private void preUpdate(){

        this.edicao = LocalDateTime.now();
    }}


