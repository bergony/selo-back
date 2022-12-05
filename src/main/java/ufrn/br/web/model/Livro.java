package ufrn.br.web.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Data
@Entity
@NoArgsConstructor

public class Livro implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "edicao")
	private String descricao;
	
	@Column(name = "titulo")
	private String titulo;

//	@Column
//	private String data_lancamento;

	@ManyToOne()
	@JoinColumn(name="pessoa_id", nullable=false)
	@JsonIgnore
	private Pessoa pessoa;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "emprestimo_id")
	private Emprestimo emprestimo;

	public Livro(Integer id, String descricao, String titulo, Pessoa pessoa) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.titulo = titulo;
		this.pessoa = pessoa;
	}	
	

}
