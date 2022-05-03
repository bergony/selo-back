package ufrn.br.web.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Pessoa {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "data_nascimento")
    private Date dataNascimento;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @OneToMany(mappedBy = "pessoa")
    private List<TelefonePessoa> telephoneList;

    @ManyToOne
    @JoinColumn(name = "endereco_pessoa_id")
    private EnderecoPessoa enderecoPessoa;
}
