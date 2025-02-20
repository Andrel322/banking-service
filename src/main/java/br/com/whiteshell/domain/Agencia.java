package br.com.whiteshell.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agencia implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    private String nome;

    @Column(name = "razao_social")
    private String razaoSocial;

    private String cnpj;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
}
