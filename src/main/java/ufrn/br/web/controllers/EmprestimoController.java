package ufrn.br.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.services.EmprestimoService;
import ufrn.br.web.services.LivroService;
import ufrn.br.web.services.PessoaService;

@RestController
@RequestMapping("/api/emprestimos")
@RequiredArgsConstructor
public class EmprestimoController {


    @Autowired
    EmprestimoService emprestimoService;

    @Autowired
    LivroService livroService;

    @Autowired
    PessoaService pessoaService;
    @PutMapping("/aceitar/{idEmprestimo}/{idUser}")
    public String aceitar(@PathVariable Long idEmprestimo, @RequestBody Pessoa pessoa,@PathVariable Integer  idUser) {
        emprestimoService.responder(idEmprestimo, true, idUser);
        return "livros";
    }

    @RequestMapping(value = "/negar/{idEmprestimo}/{idUser}\"")
    public String negar(@PathVariable Long idEmprestimo, @RequestBody Pessoa pessoa,@PathVariable Integer  idUser) {
        emprestimoService.responder(idEmprestimo, false, idUser);
        return "livros";
    }

    @RequestMapping(value = "/deletarEmprestimo/{idEmprestimo}/{idUser}")
    public String deletar(@PathVariable Long idEmprestimo, @RequestBody Pessoa pessoa,@PathVariable Integer  idUser) {
        emprestimoService.deletar(idEmprestimo, false, idUser);
        return "livros";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
