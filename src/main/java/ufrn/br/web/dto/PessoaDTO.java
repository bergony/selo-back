package ufrn.br.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ufrn.br.web.model.Endereco;
import ufrn.br.web.model.Role;
import ufrn.br.web.model.Telefone;


@Getter
@Setter
@Data
public class PessoaDTO {


    private Integer id;
    private String cpf;
    private String nomeCompleto; 
    private String login;
    private Role role;
    private Telefone telefone;
    private Endereco endereco;

	public PessoaDTO() {
		
	}
    
	
}
