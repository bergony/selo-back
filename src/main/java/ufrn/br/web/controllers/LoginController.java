package ufrn.br.web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.services.PessoaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Controller
public class LoginController {

    @Autowired
    private PessoaService pessoaService;

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
        model.addAttribute("usuarioLogando", usuarioLogando);
        return voltar.pop();
    }

    @RequestMapping(value = "/pessoas", method = RequestMethod.GET)
    public String getPessoas(Model model) {
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("pessoa", new Pessoa());
        if(usuarioLogando == null)
            return "login";
        return "pessoas";
    }

    @RequestMapping(value = "/pessoas/", method = RequestMethod.POST)
    public String savePessoa(Model model, @ModelAttribute Pessoa pessoa) {
        if(pessoa.getId() == 0)
            pessoa.setId(null);
        Pessoa user = pessoaService.savePessoa(pessoa);
        if(user == null){
            model.addAttribute("erros", "Usario ja cadastrado");
        }else{
            Pessoa pessoaLoad = new Pessoa();
            model.addAttribute("pessoa", new Pessoa());
        }
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        return "pessoas";
    }

    @RequestMapping(value = "/pessoas/index", method = RequestMethod.POST, params = "action=logar")
    public String logar(Model model, @ModelAttribute Pessoa pessoa) {
        List<Pessoa> pessoas = pessoaService.findAll();
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
        Pessoa usuario = new Pessoa();
        usuario.setAdmin(false);
        model.addAttribute("pessoa", load);
        model.addAttribute("usuarioLogando", usuario);
        voltar.push("login");
        return "pessoas";
    }

    @RequestMapping(value = "/configuracao")
    public String configuracao(Model model) {
        if(usuarioLogando == null)
            return "login";
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("pessoa", new Pessoa());
        voltar.push("home");
        return "configuracao";
    }

    @RequestMapping(value = "/deletar")
    public String deletar(Model model, @RequestParam(value = "id", required = false) Long id) {
        if(usuarioLogando == null)
            return "login";
        Pessoa pessoa = pessoaService.findPessoalByID(id);
        pessoaService.remover(pessoa);
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("pessoa", new Pessoa());
        return "configuracao";
    }

    @RequestMapping(value = "/editar")
    public String editar(Model model, @RequestParam(value = "id", required = false) Long id) {
        if(usuarioLogando == null)
            return "login";
        Pessoa pessoa = pessoaService.findPessoalByID(id);
        model.addAttribute("pessoa", pessoa);
        model.addAttribute("usuarioLogando", usuarioLogando);
        voltar.push("configuracao");
        return "pessoas";
    }

}
