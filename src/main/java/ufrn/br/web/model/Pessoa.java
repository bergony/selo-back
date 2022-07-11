package ufrn.br.web.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar dataNascimento;

    @Column(nullable = false)
    private String cpf;

    private String nomeCompleto;

    private String username;

    private String email;

    private String senha;

    @ManyToOne
    @JoinColumn(name = "telefone_id")
    private Telefone telefone;

    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

}
