package ufrn.br.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.repositoreis.PessoaRepository;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa findPessoalByID(int id) {
        return pessoaRepository.getById(id);
    }
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Pessoa savePessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa); }


}
