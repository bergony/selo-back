package ufrn.br.web.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class TelefonePessoa extends Telefone {

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;


}
