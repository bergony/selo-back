package ufrn.br.web.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.services.PessoaService;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Controller
@Slf4j
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getPessoas(Model model) {
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("pessoa", new Pessoa());
        return "pessoas";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String savePessoa(Model model, @ModelAttribute Pessoa pessoa) {
        Pessoa user = pessoaService.savePessoa(pessoa);

        if(user == null){
            model.addAttribute("erros", "Usario ja cadastrado");
        }else{
            model.addAttribute("pessoa", new Pessoa());
        }
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        return "pessoas";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST, params = "action=logar")
    public String logar(Model model, @ModelAttribute Pessoa pessoa) {
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("pessoa", new Pessoa());
        List<String> erros = pessoaService.autenticarPessoa(pessoa);
        if(!erros.isEmpty()){
            model.addAttribute("erros", erros);
            return "login";
        }

        return "home";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST, params = "action=cadastrar")
    public String cadastrar(Model model, @ModelAttribute Pessoa pessoa) {
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("pessoa", new Pessoa());
        return "pessoas";
    }


}
