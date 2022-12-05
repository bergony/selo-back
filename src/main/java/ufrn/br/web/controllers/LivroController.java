package ufrn.br.web.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ufrn.br.web.model.Livro;
import ufrn.br.web.services.LivroService;

@RestController
@RequestMapping("/api/livros")

public class LivroController {   
    
    @Autowired
    private LivroService livroService;
 
    @PutMapping
	public ResponseEntity<Livro> create(@RequestParam(value = "pessoa", defaultValue = "0") Integer id_pessoa,
			@Valid @RequestBody Livro obj) {
		Livro newObj = livroService.create(id_pessoa, obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/livros/{id}")
				.buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}")
	public ResponseEntity<Livro> findByid(@PathVariable Integer id) {
		Livro obj = livroService.findById(id);
		return ResponseEntity.ok().body(obj);
	}

//	@PutMapping(value = "/{id}")
//	public ResponseEntity<Livro> update(@PathVariable Integer id, @Valid @RequestBody Livro obj) {
//		Livro newObj = livroService.update(id, obj);
//		return ResponseEntity.ok().body(newObj);
//	}

//	@GetMapping
//	public ResponseEntity<List<LivroDTO>> findAllBookByCategory(// localhost:8080/livros?categoria=1
//			@RequestParam(value = "categoria", defaultValue = "0") Integer id_cat) {
//		List<Livro> list = livroService.findAllByCategoriy(id_cat);
//		List<LivroDTO> listDTO = list.stream().map(obj -> new LivroDTO(obj)).collect(Collectors.toList());
//		return ResponseEntity.ok().body(listDTO);
//	}

//	@GetMapping(value = "/allbooks")
//	public ResponseEntity<List<LivroDTO>> findAll() {
//		List<Livro> list = livroService.findAll();
//		List<LivroDTO> listDTO = list.stream().map(obj -> new LivroDTO(obj)).collect(Collectors.toList());
//		return ResponseEntity.ok().body(listDTO);
//	}

//	@DeleteMapping(value = "/{id}")
//	public ResponseEntity<Void> delete(@PathVariable Integer id) {
//		livroService.delete(id);
//		return ResponseEntity.noContent().build();
//
//	}


   

}
