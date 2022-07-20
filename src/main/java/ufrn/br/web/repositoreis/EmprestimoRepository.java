package ufrn.br.web.repositoreis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ufrn.br.web.model.Emprestimo;
import ufrn.br.web.model.Pessoa;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findAllByPessoa(Pessoa username);

}