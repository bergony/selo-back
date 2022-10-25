package ufrn.br.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.model.Usuario;
import ufrn.br.web.repositoreis.UsuarioRepository;
import ufrn.br.web.services.UsuarioService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Pessoa pessoa ){

        pessoa.setSenha(passwordEncoder.encode(pessoa.getSenha()));

        return usuarioService.salvar(pessoa);
    }
}
