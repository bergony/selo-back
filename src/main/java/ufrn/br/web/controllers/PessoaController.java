package ufrn.br.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.repositoreis.EmprestimoRepository;
import ufrn.br.web.repositoreis.LivroRepository;
import ufrn.br.web.repositoreis.PessoaRepository;
import ufrn.br.web.services.PessoaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@RestController
@RequestMapping("/api/pessoas")
@RequiredArgsConstructor
public class PessoaController {



	@Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping
    public List<Pessoa> find(Pessoa filtro ){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of(filtro, matcher);
        return pessoaRepository.findAll(example);
    }

    @GetMapping ("{id}")
    public Pessoa findById (@PathVariable Integer id) {
    	return pessoaService.findPessoalByID(id);
    }
    
    @PostMapping()
    public Pessoa cadastrarPessoa (@RequestBody Pessoa pessoa) {
    	return pessoaService.savePessoa(pessoa);
    }

    @PutMapping ("{id}")
    public Pessoa editarPessoa (@PathVariable Integer id, @RequestBody Pessoa pessoa) {
    	return pessoaService.editarPessoa(id, pessoa);
    }
    
   /* @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public String salvarPessoa(Model model, @ModelAttribute Pessoa pessoa) {
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
    @RequestMapping(value = "/cadastrar", method = RequestMethod.POST, params = "action=cadastrar")
    public String cadastrar(Model model, @ModelAttribute Pessoa pessoa) {
        List<Pessoa> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        Pessoa load = new Pessoa();
        load.setId(0);
        model.addAttribute("pessoa", load);
        return "pessoas";
    }



    @RequestMapping(value = "/editar")
    public String editar(Model model, @RequestParam(value = "id", required = false) Long id, @RequestParam(value = "admin", required = false) Long admin) {

        Pessoa pessoa = pessoaService.findPessoalByID(id);
        model.addAttribute("pessoa", pessoa);
        model.addAttribute("voltar", "configuracao");
        return "pessoas";
    }

    @RequestMapping(value = "/deletar")
    public String deletar(Model model, @RequestParam(value = "id", required = false) Long id, @RequestParam(value = "admin", required = false) Long admin) {

        Pessoa pessoa = pessoaService.findPessoalByID(id);

       pessoaService.remover(model, pessoa);

        List<Pessoa> pessoas = pessoaService.findAll();

        model.addAttribute("pessoas", pessoas);
        model.addAttribute("pessoa", new Pessoa());

        return "configuracao";
    }*/

}
