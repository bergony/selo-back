package ufrn.br.web.dto;

import lombok.Data;
import ufrn.br.web.model.Endereco;
import ufrn.br.web.model.Telefone;

@Data
public class UsuarioDTO {

    private String senha;
    private String nomeCompleto;
    private String login;
    private Integer role;
    private String cpf;
}
