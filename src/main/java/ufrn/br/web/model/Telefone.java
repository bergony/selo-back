package ufrn.br.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Data@Entity
@IdClass(TelefoneID.class)
public class Telefone implements Serializable {


    @Id
    private String ddd;

    @Id
    private String numero;



}
