package ufrn.br.web.dto;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Builder;
import lombok.Data;
import ufrn.br.web.model.Livro;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.services.PessoaService;

@Data
@Builder
public class LivroDTO {	
    private String descricao;
    private String titulo;
    private Integer pessoaId;
    private Date data = new Date();  
    
    @Autowired
    private PessoaService pessoaService;    
       
    public Pessoa buscar(Integer id) {    	
    	return pessoaService.findPessoalByID(id);
    }

    public Livro transformarParaObjeto() {
    	return new Livro(1, descricao, titulo, data, buscar(pessoaId), null);
    }
    
    
}
