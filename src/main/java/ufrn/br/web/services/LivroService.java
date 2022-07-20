package ufrn.br.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ufrn.br.web.model.Emprestimo;
import ufrn.br.web.model.Livro;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.repositoreis.EmprestimoRepository;
import ufrn.br.web.repositoreis.LivroRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class LivroService {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    EmprestimoRepository emprestimoRepository;

    @Autowired
    PessoaService pessoaService;

    public Livro findLivroByID(Long id) {
        return livroRepository.findById(id).orElse(null);
    }
    public boolean validarLivro( Livro livro){
        if(livro.getId() != null && livro.getId() == 0){
            return  true;
        }
        return false;
    }
    public Livro salvar(Livro livro){
        return  livroRepository.save(livro);
    }
    public Livro salvar(Model model, Livro livro){

        Livro livroSaved =  livroRepository.save(livro);

        if(livroSaved == null){
            model.addAttribute("erros", "Livro nao cadastrado!");
        }else{
            List<String> sucessos = new ArrayList<>();
            sucessos.add("livro salvo com sucesso!");
            model.addAttribute("sucessos", sucessos);
        }
        carregar(model, livro.getPessoa());

        return livroSaved;

    }

    public Livro editarLivro(Model model, Long id, Long admin){

        Livro livro = livroRepository.findById(id).orElse(null);
        carregarMeusLivros(model, livro.getPessoa());
        carregarMeusEmprestimo(model, livro.getPessoa());
        model.addAttribute("livro", livro);

        Pessoa usuarioLogando = pessoaService.findPessoalByID(admin);
        model.addAttribute("usuarioLogando", usuarioLogando);
        return livro;
    }

    public Livro deletarLivro(Model model, Long id, Long admin){

        Livro livro = livroRepository.findById(id).orElse(null);
        livroRepository.delete(livro);
        List<Livro> livros = livroRepository.findAll();
        carregar(model, livro.getPessoa());

        Pessoa usuarioLogando = pessoaService.findPessoalByID(admin);
        model.addAttribute("usuarioLogando", usuarioLogando);

        return livro;
    }




    public void carregarMeusLivros(Model model, Pessoa usuarioLogando){
        List<Livro> livros = livroRepository.findAllByUserName(usuarioLogando.getUsername());
        model.addAttribute("livros", livros);

    }

    public void carregarMeusEmprestimo(Model model, Pessoa usuarioLogado){
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();

        emprestimos.forEach( e -> {
            e.setLivro(livroRepository.findByEmprestimoAtivo(e.getId()));
            if(e.getLivro() != null){
                e.setDono(e.getLivro().getPessoa().getId() == usuarioLogado.getId());
            }
        });

        emprestimos.removeIf(e -> {
            if(e.getLivro() == null || (e.getPessoa().getId() != usuarioLogado.getId() && e.getLivro().getPessoa().getId() != usuarioLogado.getId())){
                return true;
            }

            return false;
        });
        model.addAttribute("emprestimos",emprestimos );

    }

    public void reset(Model model, Pessoa usuarioLogado){
        Livro livro = new Livro();
        livro.setId(0l);
        livro.setPessoa(usuarioLogado);
        model.addAttribute("livro", livro);
    }

    public void carregar(Model model, Pessoa usuarioLogado){
        carregarMeusLivros(model, usuarioLogado);
        carregarMeusEmprestimo(model, usuarioLogado);
        reset(model, usuarioLogado);
    }

    public  List<Livro> carregalivrosDisponiveis(){
        return livroRepository.findAllByEmprestimoNull();
    }

    public Livro findLivroEmprestimo(Emprestimo emprestimo) {
        return livroRepository.findByEmprestimoAtivo(emprestimo.getId());
    }
}
