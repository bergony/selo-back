package ufrn.br.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import ufrn.br.web.dto.PessoaDTO;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.repositoreis.PessoaRepository;
import ufrn.br.web.services.PessoaService;

@RestController
@RequestMapping("/api/pessoas")
@RequiredArgsConstructor
public class PessoaController {



	@Autowired
    private PessoaService pessoaService;

    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping ("{id}")
    public ResponseEntity<Pessoa> findById (@PathVariable Integer id) {
    	Pessoa p = pessoaService.findPessoalByID(id);

    	if (p != null) {
    		return ResponseEntity.ok(p);
    	}
    	return ResponseEntity.notFound().build();
    }
    
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaDTO cadastrarPessoa (@RequestBody Pessoa pessoa) {
    	return toPessoaDto( pessoaService.savePessoa(pessoa));
    }

    @PutMapping ("{id}")
    public Pessoa editarPessoa (@PathVariable Integer id, @RequestBody Pessoa pessoa) {
    	return pessoaService.editarPessoa(id, pessoa);
    }
    
    @DeleteMapping("{id}")
    public Pessoa deletarPessoa (@PathVariable Integer id) {
    	return pessoaService.deletarPessoa(id);
    }
    @GetMapping
    public List<PessoaDTO> listarPessoas () {
    	return pessoaService.findAll()
    			.stream()
    			.map(this::toPessoaDto)
    			.collect(Collectors.toList());
    }
    
    public PessoaDTO toPessoaDto (Pessoa pessoa) {
        PessoaDTO pessoaDTO =  modelMapper.map(pessoa, PessoaDTO.class);
    	return pessoaDTO;
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
/*
 {
    "id": null,
    "login": null,
    "senha": null,
    "role": null,
    "dataNascimento": null,
    "cpf": null,
    "nomeCompleto": null,
    "username": null,
    "email": "teste",
    "password": null,
    "telefone": null,
    "endereco": null,
    "livros": null,
    "emprestimos": null
}
 * */
}
