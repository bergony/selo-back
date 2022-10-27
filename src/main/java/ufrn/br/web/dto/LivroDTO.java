package ufrn.br.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ufrn.br.web.model.Pessoa;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class LivroDTO {


    private String descricao;
    private String titulo;
    private String data_lancamento;
    private Integer pessoaId;
    private Integer emprestimoID;
}
