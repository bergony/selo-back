package ufrn.br.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.services.EmprestimoService;
import ufrn.br.web.services.LivroService;
import ufrn.br.web.services.PessoaService;

@Controller
@RequestMapping("/api/emprestimos")
@RequiredArgsConstructor
public class EmprestimoController {


    @Autowired
    EmprestimoService emprestimoService;

    @Autowired
    LivroService livroService;

    @Autowired
    PessoaService pessoaService;
  /*  @RequestMapping(value = "/aceitar")
    public String aceitar(Model model, @RequestParam(value = "id", required = false) Long id, @RequestParam(value = "admin", required = false) Long admin) {
        emprestimoService.responder(id, true, admin, model);
        return "livros";
    }

    @RequestMapping(value = "/negar")
    public String negar(Model model, @RequestParam(value = "id", required = false) Long id, @RequestParam(value = "admin", required = false) Long admin) {
        emprestimoService.responder(id, false, admin, model);
        return "livros";
    }

    @RequestMapping(value = "/deletarEmprestimo")
    public String deletar(Model model, @RequestParam(value = "id", required = false) Long id, @RequestParam(value = "admin", required = false) Long admin) {
        emprestimoService.deletar(id, false, admin, model);
        return "livros";
    }*/
}
