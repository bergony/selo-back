package ufrn.br.web.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Endereco {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String cidade;

    private Long numero;

    private long cep;

    private String estado;

    private String logradouro;


}
