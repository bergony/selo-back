package ufrn.br.web.repositoreis;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ufrn.br.web.model.Emprestimo;
import ufrn.br.web.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    @Query("select p from Pessoa p where p.nomeCompleto = :nomeCompleto")
    Pessoa findByUserNameAndPassword(@Param("nomeCompleto") String nomeCompleto);
    
    //Pessoa findByEmprestimos ();
}
