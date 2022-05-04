package ufrn.br.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class TelefoneID implements Serializable {

    private String ddd;
    private String numero;

    public TelefoneID(String ddd, String numero) {
        this.ddd = ddd;
        this.numero = numero;
    }
}
