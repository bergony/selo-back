package ufrn.br.web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ufrn.br.web.model.Emprestimo;
import ufrn.br.web.model.Livro;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.repositoreis.EmprestimoRepository;
import ufrn.br.web.repositoreis.LivroRepository;
import ufrn.br.web.services.PessoaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Controller
public class LoginController {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    private Pessoa usuarioLogando;
    private Stack<String> voltar = new Stack<>();

    @RequestMapping(value={"", "/", "login","/login/" })
    public String index() {
        return "login";
    }
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return voltar.peek();
    }

    @RequestMapping("/appLogout")
    public String logout(Model model) {
        List<String> erros = new ArrayList<>();
        usuarioLogando = null;
        erros.add("Você foi desconectado.");
        model.addAttribute("erros", erros);
        return "login";
    }

    @RequestMapping("/appVoltar")
    public String appVoltar(Model model) {
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);

        List<Livro> livros = livroRepository.findAll();
        model.addAttribute("livros", livros);
        model.addAttribute("usuarioLogando", usuarioLogando);

        if(voltar.isEmpty())
            return "login";
        return voltar.pop();
    }

    @RequestMapping("/home")
    public String home(Model model) {
        String login = usuarioLogado(model);
        if (login != null) return login;

        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("usuarioLogando", usuarioLogando);
        return "home";
    }

    private String usuarioLogado(Model model) {
        if(usuarioLogando == null){
            List<String> erros = new ArrayList<>();
            usuarioLogando = null;
            erros.add("Nenhum Usuário conectado");
            model.addAttribute("erros", erros);
            return "login";
        }
        return null;
    }

    @RequestMapping(value = "/pessoas", method = RequestMethod.GET)
    public String getPessoas(Model model) {
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("pessoa", new Pessoa());
        String login = usuarioLogado(model);
        if (login != null) return login;
        return "pessoas";
    }

    @RequestMapping(value = "/pessoas/", method = RequestMethod.POST)
    public String savePessoa(Model model, @ModelAttribute Pessoa pessoa) {
        if(pessoa.getId() == 0)
            pessoa.setId(null);
        Pessoa user = pessoaService.savePessoa(pessoa);
        if(user == null){
            model.addAttribute("erros", "Usuário ja cadastrado!");
        }else{
            Pessoa pessoaLoad = new Pessoa();
            model.addAttribute("pessoa", new Pessoa());
            List<String> sucessos = new ArrayList<>();
            sucessos.add("Usuário salvo com sucesso!");
            model.addAttribute("sucessos", sucessos);

        }
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);

        return cadastrar(model, pessoa);
    }


    @RequestMapping(value = "/livrosCadastrar/", method = RequestMethod.POST)
    public String saveLivro(Model model, @ModelAttribute Livro livro) {
        if(livro.getId() != null && livro.getId() == 0)
            livro.setId(null);
        livro.setPessoa(usuarioLogando);
        Livro user = livroRepository.save(livro);
        if(user == null){
            model.addAttribute("erros", "Livro nao cadastrado!");
        }else{
            Livro pessoaLoad = new Livro();
            model.addAttribute("livro", new Livro());
            List<String> sucessos = new ArrayList<>();
            sucessos.add("livro salvo com sucesso!");
            model.addAttribute("sucessos", sucessos);

        }
        List<Livro> livros = livroRepository.findAllByUserName(usuarioLogando.getUsername());
        model.addAttribute("livros", livros);


        Livro load = new Livro();
        load.setId(0l);
        model.addAttribute("livro", load);
        return "livros";
    }


    @RequestMapping(value = "/pessoas/index", method = RequestMethod.POST, params = "action=logar")
    public String logar(Model model, @ModelAttribute Pessoa pessoa) {
        List<Pessoa> pessoas = pessoaService.findAll();
        List<Livro> livros = livroRepository.findAll();
        model.addAttribute("livros", livros);
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("pessoa", new Pessoa());
        List<String> erros = pessoaService.autenticarPessoa(pessoa);
        if(!erros.isEmpty()){
            model.addAttribute("erros", erros);
            return "login";
        }
        usuarioLogando = pessoaService.fetchPessoa(pessoa);

        model.addAttribute("usuarioLogando", usuarioLogando);
        voltar.push("login");
        return "home";
    }


    @RequestMapping(value = "/pessoas/index", method = RequestMethod.POST, params = "action=cadastrar")
    public String cadastrar(Model model, @ModelAttribute Pessoa pessoa) {
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        Pessoa load = new Pessoa();
        load.setId(0l);
        load.setAdmin(false);
        model.addAttribute("pessoa", load);
        return "pessoas";
    }

    @RequestMapping(value = "/configuracao")
    public String configuracao(Model model) {
        String login = usuarioLogado(model);
        if (login != null) return login;
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("pessoa", new Pessoa());
        voltar.push("home");
        return "configuracao";
    }

    @RequestMapping(value = "/livros")
    public String livros(Model model) {
        String login = usuarioLogado(model);
        if (login != null) return login;
        List<Livro> livros = livroRepository.findAllByUserName(usuarioLogando.getUsername());
        model.addAttribute("livros", livros);
        model.addAttribute("livro", new Livro());
        voltar.push("home");
        return "livros";
    }

    @RequestMapping(value = "/deletar")
    public String deletar(Model model, @RequestParam(value = "id", required = false) Long id) {
        String login = usuarioLogado(model);
        if (login != null) return login;
        Pessoa pessoa = pessoaService.findPessoalByID(id);
        pessoaService.remover(pessoa);
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("pessoa", new Pessoa());
        return "configuracao";
    }

    @RequestMapping(value = "/editar")
    public String editar(Model model, @RequestParam(value = "id", required = false) Long id) {
        String login = usuarioLogado(model);
        if (login != null) return login;
        Pessoa pessoa = pessoaService.findPessoalByID(id);

        model.addAttribute("usuarioLogando", usuarioLogando);
        model.addAttribute("pessoa", pessoa);
        voltar.push("configuracao");
        return "pessoas";
    }

    @RequestMapping(value = "/editarLivro")
    public String editarLivro(Model model, @RequestParam(value = "id", required = false) Long id) {
        String login = usuarioLogado(model);
        if (login != null) return login;
        Livro livro = livroRepository.findById(id).orElse(null);
        List<Livro> livros = livroRepository.findAllByUserName(usuarioLogando.getUsername());
        model.addAttribute("livros", livros);

        model.addAttribute("usuarioLogando", usuarioLogando);
        model.addAttribute("livro", livro);
        return "livros";
    }


    @RequestMapping(value = "/deletarLivro")
    public String deletarLivro(Model model, @RequestParam(value = "id", required = false) Long id) {
        String login = usuarioLogado(model);
        if (login != null) return login;
        Livro livro = livroRepository.findById(id).orElse(null);
        livroRepository.delete(livro);
        List<Livro> livros = livroRepository.findAll();
        model.addAttribute("livros", livros);
        model.addAttribute("livro", new Livro());
        return "livros";
    }

    @RequestMapping(value = "/emprestimo")
    public String emprestimo(Model model, @RequestParam(value = "id", required = false) Long id) {
        String login = usuarioLogado(model);
        if (login != null) return login;

        Livro livro = livroRepository.findById(id).orElse(null);

        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setLivro(livro);
        emprestimo.setPessoa(usuarioLogando);
        emprestimo = emprestimoRepository.save(emprestimo);
        model.addAttribute("usuarioLogando", usuarioLogando);
        List<Livro> livros = livroRepository.findAll();
        model.addAttribute("livros", livros);

        return "home";
    }

}
