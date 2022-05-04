package ufrn.br.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data@Entity
@IdClass(TelefoneID.class)
public class Telefone implements Serializable {

    @Id
    @GeneratedValue
    private String ddd;

    @Id
    private String numero;



}
