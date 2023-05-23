package br.com.uniamerica.estacionamento.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Entity
@Audited

@Table(name = "modelo", schema = "public")
@AuditTable(value = "modelo", schema= "public")
public class Modelo extends AbstractEntity {

    @Getter @Setter
    @Column(name = "nome",nullable = false, unique = true)
    private String nome;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "marca", nullable = false, unique = true)
    private Marca marca;


}
