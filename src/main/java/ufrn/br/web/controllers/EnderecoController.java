package ufrn.br.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.RequiredArgsConstructor;
import ufrn.br.web.model.Endereco;
import ufrn.br.web.services.EnderecoService;

@RestController
@RequestMapping("/api/endereco")
@RequiredArgsConstructor

public class EnderecoController {
	@Autowired
	private EnderecoService enderecoService;
	
	@PostMapping
	public Endereco cadastrar (@RequestBody Endereco endereco) throws Exception {
		return enderecoService.cadastrarEndereco(endereco);
	}
	
	@GetMapping ("{cep}")
	public ResponseEntity<Endereco> buscarPorCep(@PathVariable String cep){
		Endereco endereco = enderecoService.buscarPorCep(cep);
		if(endereco != null) {
			return ResponseEntity.ok(endereco);
		}
		return ResponseEntity.notFound().build();	
	}



}
