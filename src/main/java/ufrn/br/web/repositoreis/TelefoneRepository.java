package ufrn.br.web.repositoreis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufrn.br.web.model.Telefone;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Integer> {

}