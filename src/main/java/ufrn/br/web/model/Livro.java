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
	private String edicao;
	@Column(name = "isbn")
	private String isbn;
	@Column(name = "titulo")
	private String titulo;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_lancamento")
	private Date data_lancamento;
	
}
