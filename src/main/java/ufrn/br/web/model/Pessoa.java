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
import java.util.Stack;

@Getter
@Setter
@Entity
@Table(name = "Pessoa")
public class Pessoa extends Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar dataNascimento;

    @Column(nullable = false)
    private String cpf;
    private String nomeCompleto;

    @Column(unique=true)
    private String username;
    private String email;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "telefone_id", referencedColumnName = "id")
    private Telefone telefone;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToMany(mappedBy="pessoa")
    private List<Livro> livros;

    @OneToMany(mappedBy="pessoa")
    private List<Emprestimo> emprestimos;

	public Pessoa() {
		
	}

    

}
