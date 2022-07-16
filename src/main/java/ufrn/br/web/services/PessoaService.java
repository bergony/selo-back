package ufrn.br.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.repositoreis.EnderocoRepository;
import ufrn.br.web.repositoreis.PessoaRepository;
import ufrn.br.web.repositoreis.TelefoneRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private EnderocoRepository enderocoRepository;
    @Autowired
    private TelefoneRepository telefoneRepository;

    public Pessoa findPessoalByID(int id) {
        return pessoaRepository.getById(id);
    }
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    @Transactional
    public Pessoa savePessoa(Pessoa pessoa) {

        Pessoa pessoaJaCadstrada = pessoaRepository.findByUserNameAndPassword(pessoa.getUsername());

        if(pessoaJaCadstrada != null)
            return null;


        return pessoaRepository.save(pessoa); }

    public List<String> autenticarPessoa( Pessoa pessoa) {
        List<String> erros = new ArrayList<>();
        Pessoa pessoaValidar = pessoaRepository.findByUserNameAndPassword(pessoa.getUsername());

        if(pessoaValidar == null){
            erros.add("Usuario Invalido");
            return erros;
        }
        if(!pessoa.getPassword().equals(pessoaValidar.getPassword())){
            erros.add("Senha Invalido");
        }
        return erros;
    }
}
