package ufrn.br.web.services;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufrn.br.web.dto.LivroDTO;
import ufrn.br.web.model.Livro;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.repositoreis.EmprestimoRepository;
import ufrn.br.web.repositoreis.LivroRepository;

@Service
public class LivroService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Autowired
	LivroRepository livroRepository;

	@Autowired
	EmprestimoRepository emprestimoRepository;

	@Autowired
	PessoaService pessoaService;

	public Livro findById(Integer id) {
		Optional<Livro> obj = livroRepository.findById(id);
//		return obj.orElseThrow(() -> new ObjectNotfoundExecption(
//				"Obejeto Nao Encontrado! ID: " + id + ", tipo " + Livro.class.getName()));
		return obj.orElse(null);
	}

	public Livro create(Integer id_pessoa, Livro obj) {
		obj.setId(null);
		Pessoa pessoa = pessoaService.findPessoalByID(id_pessoa);
		obj.setPessoa(pessoa);
		return livroRepository.save(obj);
	}

	public void delete(Integer id) {
		Livro obj = findById(id);
		livroRepository.delete(obj);
	}

}
