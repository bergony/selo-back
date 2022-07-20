package ufrn.br.web.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Entity
@Table(name = "emprestimo")
@Getter
@Setter
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "emprestimo")
    @JoinColumn(name="livro_id")
    Livro livro;
    @ManyToOne()
    private Pessoa pessoa;

    private boolean reposta;

    @Transient
    private boolean dono;
}
