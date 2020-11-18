package io.github.wendergalan.springrestrepositories.controllers;

import io.github.wendergalan.springrestrepositories.components.MessageByLocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * The type Home controller.
 */
@RestController
public class HomeController {

    @Autowired
    private MessageByLocaleService messageByLocaleService;

    /**
     * Redirect de port application to swagger ui
     *
     * @return ModalAndView model and view
     */
    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("redirect:" + "swagger-ui.html");
    }

    /**
     * Teste da internacionalização do projeto.
     *
     * @return String
     */
    @GetMapping("/internacionalizacao")
    public String internacionalizacaoTest() {
        return messageByLocaleService.getMessage("internacionalizacao.teste");
    }
}
