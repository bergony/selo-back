package ufrn.br.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ufrn.br.web.dto.EnderecoDTO;
import ufrn.br.web.model.Endereco;
import ufrn.br.web.services.EnderecoService;

@RestController
@RequestMapping("/api/endereco")
@RequiredArgsConstructor

public class EnderecoController {
	@Autowired
	private EnderecoService enderecoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus (HttpStatus.CREATED)
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
	
	@GetMapping
	public List <EnderecoDTO> listarEnderecos () {
		return enderecoService.findAll()
				.stream()
				.map(this::toEnderecoDto).
				collect(Collectors.toList());
				
	}

    public EnderecoDTO toEnderecoDto (Endereco endereco) {
    	return modelMapper.map(endereco, EnderecoDTO.class);
    }

}
