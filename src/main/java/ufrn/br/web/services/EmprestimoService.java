package ufrn.br.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ufrn.br.web.model.Emprestimo;
import ufrn.br.web.model.Livro;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.repositoreis.EmprestimoRepository;

import javax.transaction.Transactional;

@Service
public class EmprestimoService {


    @Autowired
    EmprestimoRepository emprestimoRepository;

@Autowired private PessoaService pessoaService;

    @Autowired
    LivroService livroService;
  public void responder(Long id, boolean reposta, Integer admin){
        Emprestimo emprestimo = emprestimoRepository.findById(id).orElse(null);
        emprestimo.setReposta(reposta);
        emprestimoRepository.save(emprestimo);

        Pessoa usuarioLogando = pessoaService.findPessoalByID(admin);
        livroService.carregar( usuarioLogando);
    }


   public void save(Emprestimo emprestimo, Livro livro) {
        emprestimo = emprestimoRepository.save(emprestimo);

        livro.setEmprestimo(emprestimo);
        livroService.salvar(livro);

    }

    @Transactional
    public void deletar(Long id, boolean reposta, Integer admin){
        Emprestimo emprestimo = emprestimoRepository.findById(id).orElse(null);
        Livro livro = livroService.findLivroEmprestimo(emprestimo);
        livro.setEmprestimo(null);
        livroService.salvar(livro);
        emprestimoRepository.delete(emprestimo);

        Pessoa usuarioLogando = pessoaService.findPessoalByID(admin);
        livroService.carregar(usuarioLogando);
    }

}
