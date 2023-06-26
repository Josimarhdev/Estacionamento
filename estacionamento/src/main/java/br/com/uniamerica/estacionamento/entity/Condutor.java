package br.com.uniamerica.estacionamento.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;

@Entity
@Audited

    @Table(name = "condutores", schema = "public")
    @AuditTable(value = "condutores_audit", schema = "audit");
public class Condutor extends AbstractEntity {

    @Getter @Setter
    @Column(name = "nome",nullable = false , length = 100)
    private String nome;
    @Getter @Setter
    @Column(name = "cpf", nullable = false , length = 100)
    private String cpf;
    @Getter @Setter
    @Column(name = "telefone", nullable = false, unique = true, length = 17 )
    private String telefone;
    @Getter @Setter
    @Column(name = "Tempo_gasto")
    private LocalTime tempo_gasto;
    @Getter @Setter
    @Column(name = "Tempo_desconto")
    private LocalTime tempoDesconto;
}



