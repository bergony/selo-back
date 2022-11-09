package ufrn.br.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ufrn.br.web.model.Genero;
import ufrn.br.web.services.GeneroService;

@RestController
@RequestMapping("/api/genero")
@RequiredArgsConstructor

public class GeneroController {

	private GeneroService generoService;
	
	@PostMapping
	public Genero cadastar(@RequestBody Genero genero) throws Exception {
		return generoService.cadastrarGenero(genero);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Genero> buscarID (@PathVariable Long id){
		Genero  genero = generoService.buscarGenero(id);
		if(genero != null) {
			return ResponseEntity.ok(genero);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping
	public Genero delete(@PathVariable Long id) {
		return generoService.deletarGenero(id);
	}
}
