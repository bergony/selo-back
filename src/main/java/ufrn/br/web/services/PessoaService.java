package ufrn.br.web.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.repositoreis.PessoaRepository;

@Service
@AllArgsConstructor
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa findPessoalByID(Long id) {
        return pessoaRepository.getById(id);
    }

    public Pessoa savePessoa(Pessoa pessoa) {return pessoaRepository.save(pessoa); }


}
