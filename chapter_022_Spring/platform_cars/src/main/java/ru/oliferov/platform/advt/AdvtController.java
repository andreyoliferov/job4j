package ru.oliferov.platform.advt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.oliferov.platform.advt.service.AdvtService;

/**
 * @autor aoliferov
 * @since 25.03.2019
 */
@Controller
public class AdvtController {

    @Autowired
    private AdvtService advtService;

    @RequestMapping(value = "/advt", method = RequestMethod.GET)
    public String showMain(ModelMap model) {
        return "main";
    }
}
