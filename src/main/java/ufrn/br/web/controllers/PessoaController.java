package ufrn.br.web.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ufrn.br.web.model.Pessoa;
import ufrn.br.web.services.PessoaService;

import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    // inject via application.properties
    @Value("${welcome.message}")
    private String message;

    private List<String> tasks = Arrays.asList("O homem mais rico da Babilônia teste teste",
            "Watchmen – Edição Definitiva",
            "É Assim que Acaba",
            "Mulheres que correm com os lobos",
            "A garota do lago",
            "12 regras para a vida: Um antídoto para o caos",
            "Mais esperto que o Diabo: O mistério revelado da liberdade e do sucesso");

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("message", message);
        model.addAttribute("tasks", tasks);

        return "welcome"; //view
    }

    // /hello?name=kotlin
    @GetMapping("/hello")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "")
            String name, Model model) {

        model.addAttribute("message", name);

        return "welcome"; //view
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> handleForm(@RequestParam("firstName") String firstName,
                                        @RequestParam("firstName") String lastName, @RequestParam("pessoa")
                                                Pessoa pessoa) {
        log.info("first Name : {}", firstName);
        log.info("Last Name : {}", lastName);
        log.info("pessoa: {}", pessoa);
        return ResponseEntity.ok().body(firstName);
    }
}
