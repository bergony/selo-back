package ufrn.br.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ufrn.br.web.dto.CredenciaisDTO;
import ufrn.br.web.dto.TokenDTO;
import ufrn.br.web.exception.SenhaInvalidaException;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.model.Usuario;
import ufrn.br.web.repositoreis.UsuarioRepository;
import ufrn.br.web.security.JwtService;
import ufrn.br.web.services.UsuarioService;

import javax.validation.Valid;
import java.util.List;
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    //private final UsuarioRepository usuarioRepository;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "Requestor-Type")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Pessoa pessoa ){

        pessoa.setSenha(passwordEncoder.encode(pessoa.getSenha()));

        return usuarioService.salvar(pessoa);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
