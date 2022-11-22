package ufrn.br.web.repositoreis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ufrn.br.web.model.Emprestimo;
import ufrn.br.web.model.Livro;
import ufrn.br.web.model.Pessoa;

import java.util.List;


@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {

    @Query("select l from Livro l where l.pessoa.username = :username")
    List<Livro> findAllByUserName(@Param("username") String username);


    @Query("select l from Livro l where l.emprestimo.id = :id")
    Livro findByEmprestimoAtivo(@Param("id") Integer id);

    List<Livro> findAllByEmprestimoNull();
}
