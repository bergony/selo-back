package ufrn.br.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ufrn.br.web.model.Endereco;
import ufrn.br.web.model.Telefone;


@Getter
@Setter
@Data
public class PessoaDTO {
	
    private String cpf;
    private String nomeCompleto; 
    private String username;
    private String email;
    private String password;
    private Telefone telefonePessoa;
    private Endereco enderecoPessoa;

	public PessoaDTO() {
		
	}
    
	
}
