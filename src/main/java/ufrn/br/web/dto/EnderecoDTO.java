package ufrn.br.web.dto;

import javax.persistence.Column;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ufrn.br.web.model.Pessoa;

@Getter
@Setter
@Data
public class EnderecoDTO {
	
	private String cidade;

    private String bairro;

    private Long numero;

    private String cep;

    private String estado;

    private String logradouro;

    private Pessoa Enderecopessoa;

}
