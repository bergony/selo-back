package ufrn.br.web.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufrn.br.web.model.Telefone;
import ufrn.br.web.repositoreis.TelefoneRepository;

@Service
public class TelefoneService {
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	public Telefone cadastrarTelefone (Telefone telefone) throws Exception {
		if (telefone.getNumero() == null) {
			throw new Exception("ERRO! Número está vazio!");
		}
		return telefoneRepository.save(telefone);
	}
	
	public Telefone buscarPorId (Integer id) {
		Optional <Telefone> telefone = telefoneRepository.findById(id);
		return telefone.get();
	}
	
	public Telefone delete (Integer id) {
		Optional <Telefone> telefone = telefoneRepository.findById(id);
		if (telefone.isPresent()) {
			telefoneRepository.delete(telefone.get());
			return telefone.get();
		}
		return null;
	}
	
	public Telefone editTelefone (Integer id, Telefone telefone) {
		
		Telefone oldTelefone = buscarPorId(id);
		if (oldTelefone == null) {
			return null;
		}
		oldTelefone.setNumero(telefone.getNumero());
		telefoneRepository.save(oldTelefone);
		
		
		return telefone;
		
	} 
	
	public List<Telefone> buscarPorPessoa (Long id) throws Exception {
		List <Telefone> telefones = telefoneRepository.findByPessoa(id);
		if (telefones.isEmpty()) {
			throw new Exception ("Não há telefone para o usuário com id: " + id + " !!");
		}
		return telefones; 
	}
	
	
	
	

}
