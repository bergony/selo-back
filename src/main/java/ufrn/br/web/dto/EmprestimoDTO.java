package ufrn.br.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmprestimoDTO {
    private String descricaoLivro;
    private String nomeDono;
    private Boolean reposta;
}
