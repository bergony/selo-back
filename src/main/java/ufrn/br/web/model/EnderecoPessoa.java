package ufrn.br.web.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Data
@Entity
public class EnderecoPessoa  extends Endereco {

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

}
