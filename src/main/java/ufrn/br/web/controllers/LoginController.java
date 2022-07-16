package ufrn.br.web.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {


    @RequestMapping(value={"", "/", "login","/login/" })
    public String index() {
        return "login";
    }
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping("/appLogout")
    public String logout(Model model) {
        List<String> erros = new ArrayList<>();
        erros.add("Você foi desconectado.");
        model.addAttribute("erros", erros);
        return "login";
    }

}
