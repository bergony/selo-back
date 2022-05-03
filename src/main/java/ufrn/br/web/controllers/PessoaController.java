package ufrn.br.web.controllers;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.services.PessoaService;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping(value = "/pessoa/{pessoaId}")
    public  Pessoa findPessoaById(@PathVariable Long pessaoId){
        return pessoaService.findPessoalByID(pessaoId);
    }


}
