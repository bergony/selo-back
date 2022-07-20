package ufrn.br.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ufrn.br.web.model.Livro;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.services.LivroService;
import ufrn.br.web.services.PessoaService;

import java.util.Stack;

@Controller
public class LivroController {

    private final PessoaService pessoaService;
    private final LivroService livroService;

    public LivroController(PessoaService pessoaService, LivroService livroService) {
        this.pessoaService = pessoaService;
        this.livroService = livroService;
    }

    @RequestMapping(value = "/livrosCadastrar/", method = RequestMethod.POST)
    public String saveLivro(Model model, @ModelAttribute Livro livro) {

        if(livroService.validarLivro(livro))
            livro.setId(null);

        livroService.salvar(model, livro);

        livroService.carregar(model, livro.getPessoa());
        model.addAttribute("usuarioLogando", livro.getPessoa());

        return "livros";
    }

    @RequestMapping(value = "/editarLivro")
    public String editarLivro(Model model, @RequestParam(value = "id", required = false) Long id, @RequestParam(value = "admin", required = false)Long admin) {
        livroService.editarLivro(model, id, admin);
        return "livros";
    }


    @RequestMapping(value = "/deletarLivro")
    public String deletarLivro(Model model, @RequestParam(value = "id", required = false) Long id, @RequestParam(value = "admin", required = false)Long admin) {
        livroService.deletarLivro(model, id, admin);
        return "livros";
    }

}
