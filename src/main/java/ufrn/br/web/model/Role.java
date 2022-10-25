package ufrn.br.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class Role {
    
    public  static final int ADMIN = 1;
    public  static final int MODERADOR = 2;
    public  static final int USER = 3;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String denominacao;

    public Role(int id){
        this.id = id;
    }
    public static Role roleAdmin() {
        return new Role(ADMIN);
    }

    public static Role roleModerador() {
        return new Role(MODERADOR);
    }

    public static Role roleUser() {
        return new Role(USER);
    }
    
}
