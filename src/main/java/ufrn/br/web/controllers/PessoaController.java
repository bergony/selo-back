package ufrn.br.web.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.services.PessoaService;

import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getPessoas(Model model) {
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        model.addAttribute("pessoa", new Pessoa());
        return "welcome";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String savePessoa(Model model, @ModelAttribute Pessoa pessoa) {
        Pessoa user = pessoaService.savePessoa(pessoa);
        return "redirect:/welcome/";
    }
}
