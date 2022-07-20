package ufrn.br.web.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ufrn.br.web.model.Emprestimo;
import ufrn.br.web.model.Livro;
import ufrn.br.web.model.Pessoa;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {


    private final LivroService livroService;
    private final EmprestimoService emprestimoService;

    public LoginService(LivroService livroService, EmprestimoService emprestimoService) {
        this.livroService = livroService;
        this.emprestimoService = emprestimoService;
    }


    public String usuarioLogado(Model model, Pessoa usuarioLogando) {
        if(usuarioLogando == null){
            List<String> erros = new ArrayList<>();
            usuarioLogando = null;
            erros.add("Nenhum Usuário conectado");
            model.addAttribute("erros", erros);
            return "login";
        }
        return null;
    }

    public void carregarLivrosDisponiveis(Model model, Pessoa usuarioLogado) {

        List<Livro> livros = livroService.carregalivrosDisponiveis();
        livros.removeIf(l -> l.getPessoa().getId() == usuarioLogado.getId());
        model.addAttribute("livros", livros);

    }

    public void emprestimo(Model model, Pessoa usuarioLogado, Long id) {
        Livro livro = livroService.findLivroByID(id);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(null);
        emprestimo.setLivro(livro);
        emprestimo.setPessoa(usuarioLogado);

        emprestimoService.save(emprestimo, livro);



        carregarLivrosDisponiveis(model, usuarioLogado);

    }




}
