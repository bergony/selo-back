package ufrn.br.web.model;

import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "livro")
@Getter
@Setter
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "edicao")
	private String descricao;
	@Column(name = "titulo")
	private String titulo;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date data_lancamento;

	@ManyToOne
	@JoinColumn(name="pessoa_id", nullable=false)
	private Pessoa pessoa;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "emprestimo_id")
	private Emprestimo emprestimo;

}
