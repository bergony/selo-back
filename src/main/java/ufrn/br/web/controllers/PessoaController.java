package ufrn.br.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.repositoreis.EmprestimoRepository;
import ufrn.br.web.repositoreis.LivroRepository;
import ufrn.br.web.services.PessoaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Controller
public class PessoaController {
    @Autowired
    private PessoaService pessoaService;

    private Pessoa usuarioLogando;

    @RequestMapping(value = "/pessoas", method = RequestMethod.GET)
    public String getPessoas(Model model) {
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("pessoa", new Pessoa());
        String login = usuarioLogado(model);
        if (login != null) return login;
        return "pessoas";
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



    @RequestMapping(value = "/editar")
    public String editar(Model model, @RequestParam(value = "id", required = false) Long id, @RequestParam(value = "admin", required = false) Long admin) {

        Pessoa pessoa = pessoaService.findPessoalByID(id);
        usuarioLogando =  pessoaService.findPessoalByID(admin);
        usuarioLogando.getVoltar().push("configuracao");
        model.addAttribute("usuarioLogando", usuarioLogando);
        model.addAttribute("pessoa", pessoa);
        model.addAttribute("voltar", "configuracao");
        return "pessoas";
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

}
