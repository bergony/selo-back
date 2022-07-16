package ufrn.br.web.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String cidade;

    private String bairro;

    private Long numero;

    @Column(name = "cep")
    private String cep;

    private String estado;

    private String logradouro;

    @OneToOne(mappedBy = "endereco")
    private Pessoa pessoa;

}
