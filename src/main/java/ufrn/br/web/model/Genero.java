package ufrn.br.web.model;

import javax.persistence.*;

import lombok.ToString;
import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ToString
public class Genero {

	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Id
	@GeneratedValue
	private String Genero;
}
