package ufrn.br.web.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataNascimento;

    private String cpf;

    private String nomeCompleto;
}
