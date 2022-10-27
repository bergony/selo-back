package ufrn.br.web.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "livro")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "edicao")
	private String descricao;
	@Column(name = "titulo")
	private String titulo;

	@Column
	private Date data_lancamento;

	@ManyToOne
	@JoinColumn(name="pessoa_id", nullable=false)
	private Pessoa pessoa;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "emprestimo_id")
	private Emprestimo emprestimo;

}
