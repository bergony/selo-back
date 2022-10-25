package ufrn.br.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ufrn.br.web.dto.LivroDTO;
import ufrn.br.web.model.Livro;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.model.Usuario;
import ufrn.br.web.repositoreis.LivroRepository;
import ufrn.br.web.services.LivroService;
import ufrn.br.web.services.LoginService;
import ufrn.br.web.services.PessoaService;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/api/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LoginService loginService;
    private final PessoaService pessoaService;
    private final LivroService livroService;
    private final LivroRepository livroRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro salvar(@RequestBody @Valid LivroDTO dto )  {

        Livro livro = convert(dto);
        return livroRepository.save(livro);
    }

    private Livro convert(LivroDTO dto){


        return Livro
                .builder()
                .id(0l)
                .descricao(dto.getDescricao())
                .titulo(dto.getTitulo())
                .data_lancamento(new Date())
                .pessoa(pessoaService.findPessoalByID(dto.getPessoaId()))
                .build();
    }
/*

    @RequestMapping(value = "/emprestimo")
    public String emprestimo(Model model, @RequestParam(value = "id", required = false ) Long id,Pessoa usuarioLogando) {

        loginService.emprestimo(model, usuarioLogando, id);
        loginService.carregarLivrosDisponiveis(model, usuarioLogando);
        model.addAttribute("usuarioLogando", usuarioLogando);
        loginService.carregarLivrosDisponiveis(model, usuarioLogando);

        return "home";
    }

    @RequestMapping(value = "/livros")
    public String livros(Model model, Pessoa usuarioLogando) {

        livroService.carregar(model, usuarioLogando);
        usuarioLogando.getVoltar().push("home");
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
*/

}
