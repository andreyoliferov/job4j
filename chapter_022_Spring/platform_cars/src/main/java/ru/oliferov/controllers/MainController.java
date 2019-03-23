package ru.oliferov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @autor aoliferov
 * @since 19.03.2019
 */
@Controller
public class MainController {

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String showMain(ModelMap model) {
        return "main";
    }
}
