package ufrn.br.web.repositoreis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufrn.br.web.model.Endereco;

@Repository
public interface EnderocoRepository extends JpaRepository<Endereco, Integer> {

}
