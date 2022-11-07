package ufrn.br.web.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import ufrn.br.web.model.Pessoa;
import ufrn.br.web.repositoreis.EnderocoRepository;
import ufrn.br.web.repositoreis.PessoaRepository;
import ufrn.br.web.repositoreis.TelefoneRepository;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private EnderocoRepository enderocoRepository;
    @Autowired
    private TelefoneRepository telefoneRepository;

    public Pessoa findPessoalByID(Integer id) {
        return pessoaRepository.findById(id).orElse(null);
    }
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public List<Pessoa> findAllUsarios(Model model, Pessoa usuarioLogado) {
        List<Pessoa> pessoas = findAll();
        pessoas.removeIf(p -> p.getId() == usuarioLogado.getId());

        model.addAttribute("pessoas", pessoas);
        model.addAttribute("pessoa", new Pessoa());

        return pessoas;
    }

    @Transactional
    public Pessoa savePessoa(Pessoa pessoa) {

        Pessoa pessoaJaCadstrada = pessoaRepository.findByUserNameAndPassword(pessoa.getUsername());

        if(pessoaJaCadstrada != null && pessoa.getId() == null)
            return null;


        return pessoaRepository.save(pessoa); }

    public boolean autenticarPessoa(Model model, Pessoa pessoa) {
        List<String> erros = new ArrayList<>();
        Pessoa pessoaValidar = pessoaRepository.findByUserNameAndPassword(pessoa.getUsername());

        if(pessoaValidar == null){
            erros.add("Usuario Invalido");
            model.addAttribute("erros", erros);
            return true;
        }
        if(!pessoa.getPassword().equals(pessoaValidar.getPassword())){
            erros.add("Senha Invalido");
        }

        if(!erros.isEmpty()) {
            model.addAttribute("erros", erros);
            return true;
        }

        return false;
    }

    public Pessoa fetchPessoa( Pessoa pessoa) {
       return pessoaRepository.findByUserNameAndPassword(pessoa.getUsername());

    }
    public boolean remover(Model model, Pessoa pessoa) {
        List<String> erros = new ArrayList<>();

        if(!pessoa.getEmprestimos().isEmpty()){
            erros.add(" não é possivel deletar "+ pessoa.getUsername()+" com emprestimos associados .");
            model.addAttribute("erros", erros);
            return false;
        }
        erros.add("removido com sucesso");
        model.addAttribute("sucessos", erros);
        pessoaRepository.delete(pessoa);
        return false;
    }
    public Pessoa editarPessoa  (Integer id, Pessoa pessoa) {
    	Pessoa atual = findPessoalByID(id);
    	if (atual != null) {
    		atual = pessoa;
    		pessoaRepository.save(atual);
    	}
    	return null;
    }
//    public Pessoa buscarPorId (Integer id) {
//    	Optional <Pessoa> atualPessoa = pessoaRepository.findById(id);
//    	if (atualPessoa.isPresent()) {
//    		return atualPessoa.get();
//    	}
//    	return null;
//    }
}
