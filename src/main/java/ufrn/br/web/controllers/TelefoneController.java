package ufrn.br.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import ufrn.br.web.model.Telefone;
import ufrn.br.web.services.TelefoneService;

@RestController
@RequestMapping("/api/telefones")
@RequiredArgsConstructor
public class TelefoneController {

	@Autowired
	private TelefoneService telefoneService;
	
	
	@PostMapping
	public Telefone cadastrar (@RequestBody Telefone telefone) throws Exception {
		return telefoneService.cadastrarTelefone(telefone);
	}
	@GetMapping ("{id}")
	public ResponseEntity buscarPorId (@PathVariable Integer id) {
		Telefone telefone = telefoneService.buscarPorId(id).get();
		if (telefone != null) {
			return ResponseEntity.ok().body(telefone);
		}
		return (ResponseEntity) ResponseEntity.notFound();
	}
	@PutMapping ("{id}")
	public Telefone editarTelefone (@PathVariable Integer id, @RequestBody Telefone telefone) {
		return telefoneService.editTelefone(id, telefone);
	}
	
	@DeleteMapping 
	public Telefone delete (@PathVariable Integer id) {
		return telefoneService.delete(id);
	}
}
