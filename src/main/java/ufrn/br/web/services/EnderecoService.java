package ufrn.br.web.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufrn.br.web.model.Endereco;
import ufrn.br.web.repositoreis.EnderocoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderocoRepository enderecoRepository;
	
	public Endereco cadastrarEndereco(Endereco endereco) throws Exception{
		return enderecoRepository.save(endereco);
	}
	
	public Endereco buscarPorCep(String cep) {
		
		return enderecoRepository.findByCep(cep);
	}
}
