package ufrn.br.web.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufrn.br.web.model.Genero;
import ufrn.br.web.repositoreis.GeneroRepository;

@Service
public class GeneroService {
	@Autowired
	private GeneroRepository generoRepository;
	
	public Genero cadastrarGenero(Genero genero)throws Exception {
		if(genero.getId()==null) {
			throw new Exception("Campo Vazio");
		}		
		return generoRepository.save(genero);
	}
	
	public Genero buscarGenero(Long id) {
		Optional<Genero> genero = generoRepository.findById(id);
		if (genero.isPresent()) {
			return genero.get();
		}
		return null;
	}
	
		
	public Genero deletarGenero(Long id) {
		Optional<Genero> genero = generoRepository.findById(id);
		if (genero.isPresent()) {
			generoRepository.delete(genero.get());
			return genero.get();
		}
		return null;
	}
}
