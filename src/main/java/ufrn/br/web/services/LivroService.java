package ufrn.br.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufrn.br.web.model.Livro;
import ufrn.br.web.repositoreis.EmprestimoRepository;
import ufrn.br.web.repositoreis.LivroRepository;

@Service
public class LivroService {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    EmprestimoRepository emprestimoRepository;

    @Autowired
    PessoaService pessoaService;

    public Livro findLivroByID(Integer id) {
        return livroRepository.findById(id).orElse(null);
    }    
 
    
    public Livro salvar(Livro livro){
    	
        return  livroRepository.save(livro);
    }      
    

}
