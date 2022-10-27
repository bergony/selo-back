package ufrn.br.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ufrn.br.web.exception.SenhaInvalidaException;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.model.Usuario;
import ufrn.br.web.repositoreis.UsuarioRepository;

import javax.transaction.Transactional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        Pessoa pessoa = (Pessoa) usuario;
        pessoa.setUsername(usuario.getLogin());
        return repository.save(pessoa);
    }

    public UserDetails autenticar( Usuario usuario ){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = passwordEncoder.matches( usuario.getSenha(), user.getPassword() );

        if(senhasBatem){
            return user;
        }

        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles =  null;

        switch (usuario.getRole().getId()) {

            case 1:
               roles =  new String[] { "ADMIN", "USER" , "MODERADOR" };
                break;
            case 2:
               roles =  new String[] { "MODERADOR", "USER" };
               break;
            default:
                roles =  new String[] { "USER" };

        }

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();

        /*
         * Usuário em memória
         * if(!username.equals("cicrano")){
         * throw new UsernameNotFoundException("Usuário não encontrado na base.");
         * }
         *
         * return User
         * .builder()
         * .username("cicrano")
         * .password(passwordEncoder.encode("123"))
         * .roles("USER")
         * .build();
         */
    }

}
