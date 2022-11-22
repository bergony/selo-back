package ufrn.br.web.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ufrn.br.web.dto.LivroDTO;
import ufrn.br.web.model.Livro;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.model.Usuario;
import ufrn.br.web.repositoreis.LivroRepository;
import ufrn.br.web.services.EmprestimoService;
import ufrn.br.web.services.LivroService;
import ufrn.br.web.services.LoginService;
import ufrn.br.web.services.PessoaService;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/api/livros")
@RequiredArgsConstructor
public class LivroController {

   
    private final PessoaService pessoaService;
    @Autowired
    private final LivroService livroService;
    private final EmprestimoService emprestimoService;
   

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Livro> salvar(@RequestBody @Valid LivroDTO dto )  {
    	Livro livro = livroService.salvar(dto.transformarParaObjeto());
    	
    	return new ResponseEntity<>(livro, HttpStatus.CREATED);
    }

   

}
