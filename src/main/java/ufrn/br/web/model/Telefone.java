package ufrn.br.web.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Telefone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String numero;

    @OneToOne(mappedBy = "telefone")
    private Pessoa pessoa;

}
