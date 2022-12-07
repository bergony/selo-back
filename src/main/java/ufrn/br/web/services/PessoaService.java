package ufrn.br.web.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import ufrn.br.web.model.Pessoa;
import ufrn.br.web.repositoreis.EmprestimoRepository;
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

    @Autowired
    private EmprestimoRepository emprestimoRepository;
    
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

        Pessoa pessoaJaCadstrada = pessoaRepository.findByUserNameAndPassword(pessoa.getNomeCompleto());

        if(pessoaJaCadstrada != null && pessoa.getId() == null)
            return null;


        return pessoaRepository.save(pessoa); }

    public boolean autenticarPessoa(Model model, Pessoa pessoa) {
        List<String> erros = new ArrayList<>();
        Pessoa pessoaValidar = pessoaRepository.findByUserNameAndPassword(pessoa.getNomeCompleto());

        if(pessoaValidar == null){
            erros.add("Usuario Invalido");
            model.addAttribute("erros", erros);
            return true;
        }
        if(!pessoa.getSenha().equals(pessoaValidar.getSenha())){
            erros.add("Senha Invalido");
        }

        if(!erros.isEmpty()) {
            model.addAttribute("erros", erros);
            return true;
        }

        return false;
    }

    public Pessoa fetchPessoa( Pessoa pessoa) {
       return pessoaRepository.findByUserNameAndPassword(pessoa.getNomeCompleto());

    }
//    public boolean remover(Model model, Pessoa pessoa) {
//        List<String> erros = new ArrayList<>();
//
//        if(!pessoa.getEmprestimos().isEmpty()){
//            erros.add(" não é possivel deletar "+ pessoa.getUsername()+" com emprestimos associados .");
//            model.addAttribute("erros", erros);
//            return false;
//        }
//        erros.add("removido com sucesso");
//        model.addAttribute("sucessos", erros);
//        pessoaRepository.delete(pessoa);
//        return false;
//    }
    public Pessoa editarPessoa  (Integer id, Pessoa pessoa) {
    	Pessoa atual = findPessoalByID(id);
    	if (atual != null) {
            if(pessoa.getCpf() != null)
                atual.setCpf(pessoa.getCpf());

            if(pessoa.getNomeCompleto() != null)
                atual.setNomeCompleto(pessoa.getNomeCompleto());

            if(pessoa.getLogin() != null)
                atual.setLogin(pessoa.getLogin());
    		pessoaRepository.save(atual);
    	}
    	return null;
    }
    
    public Pessoa deletarPessoa (Integer id) {
    	Pessoa pessoa = findPessoalByID(id);
    	if (pessoa != null) {
    		pessoaRepository.delete(pessoa);
    		return pessoa;
    	}
    	return null; 
    }
    
    
    
//    public Pessoa buscarPorEmprestimos (Integer id) {
//    	Pessoa p = findPessoalByID(id);
//    	if (p != null) {
//    		return emprestimoRepository.findAllByPessoa(p.getNomeCompleto());
//    	}
//    }
//    public Pessoa buscarPorId (Integer id) {
//    	Optional <Pessoa> atualPessoa = pessoaRepository.findById(id);
//    	if (atualPessoa.isPresent()) {
//    		return atualPessoa.get();
//    	}
//    	return null;
//    }
}
