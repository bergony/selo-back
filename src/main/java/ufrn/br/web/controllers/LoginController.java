package ufrn.br.web.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.services.LivroService;
import ufrn.br.web.services.LoginService;
import ufrn.br.web.services.PessoaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final PessoaService pessoaService;
    private final LivroService livroService;
    private Pessoa usuarioLogando;


    public LoginController(LoginService loginService, PessoaService pessoaService, LivroService livroService) {
        this.loginService = loginService;
        this.pessoaService = pessoaService;
        this.livroService = livroService;
    }

    @RequestMapping(value={"", "/", "login","/login/" })
    public String index() {
        return "login";
    }
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return usuarioLogando.getVoltar().peek();
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
    public String appVoltar(Model model, @RequestParam(value = "voltar", required = false) String voltar) {

        model.addAttribute("usuarioLogando", usuarioLogando);
        if(voltar != null && !voltar.equals("")){
            pessoaService.findAllUsarios(model, usuarioLogando);
            return voltar;
        }

        if(usuarioLogando == null || usuarioLogando.getVoltar().isEmpty())
            return "login";

        if(usuarioLogando.getVoltar().peek().equals("home")){
            loginService.carregarLivrosDisponiveis(model, usuarioLogando);
        }

        return usuarioLogando.getVoltar().pop();
    }

    @RequestMapping("/home")
    public String home(Model model) {
        String login = loginService.usuarioLogado(model, usuarioLogando);
        if (login != null) return login;

        loginService.carregarLivrosDisponiveis(model, usuarioLogando);
        model.addAttribute("usuarioLogando", usuarioLogando);
        return "home";
    }



    @RequestMapping(value = "/configuracao")
    public String configuracao(Model model) {
        String login = loginService.usuarioLogado(model, usuarioLogando);
        if (login != null) return login;
        pessoaService.findAllUsarios(model, usuarioLogando);
        model.addAttribute("usuarioLogando", usuarioLogando);

        usuarioLogando.getVoltar().push("home");
        return "configuracao";
    }

    @RequestMapping(value = "/pessoas/index", method = RequestMethod.POST, params = "action=logar")
    public String logar(Model model, @ModelAttribute Pessoa pessoa) {

        usuarioLogando = pessoaService.fetchPessoa(pessoa);
       if(pessoaService.autenticarPessoa(model, pessoa)) {
           return "login";
       }
        model.addAttribute("usuarioLogando", usuarioLogando);
        loginService.carregarLivrosDisponiveis(model, usuarioLogando);
        return "home";
    }

    @RequestMapping(value = "/livros")
    public String livros(Model model) {
        String login = loginService.usuarioLogado(model, usuarioLogando);
        if (login != null) return login;

        model.addAttribute("usuarioLogando", usuarioLogando);
        livroService.carregar(model, usuarioLogando);
        usuarioLogando.getVoltar().push("home");
        return "livros";
    }
    
    @RequestMapping(value = "/livros")
    public String livrosall(Model model) {
        String login = loginService.usuarioLogado(model, usuarioLogando);
        if (login != null) return login;

        model.addAttribute("usuarioLogando", usuarioLogando);
        livroService.carregar(model, usuarioLogando);
        usuarioLogando.getVoltar().push("home");
        return "meuslivros";
    }


    @RequestMapping(value = "/emprestimo")
    public String emprestimo(Model model, @RequestParam(value = "id", required = false) Long id) {

       loginService.emprestimo(model, usuarioLogando, id);
        loginService.carregarLivrosDisponiveis(model, usuarioLogando);
        model.addAttribute("usuarioLogando", usuarioLogando);
        loginService.carregarLivrosDisponiveis(model, usuarioLogando);

        return "home";
    }

}
