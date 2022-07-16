package ufrn.br.web.repositoreis;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ufrn.br.web.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    @Query("select p from Pessoa p where p.username = :username")
    Pessoa findByUserNameAndPassword(@Param("username") String username);
}
