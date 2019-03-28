package ru.oliferov.platform.advt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.oliferov.platform.advt.models.Advt;
import ru.oliferov.platform.advt.service.AdvtService;
import ru.oliferov.platform.car.models.TypeCar;

import java.util.List;
import java.util.Set;

/**
 * @autor aoliferov
 * @since 25.03.2019
 */
@Controller
@RequestMapping(value = "/advt")
public class AdvtController {

    @Autowired
    private AdvtService advtService;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView showNewForm(ModelMap model) {

        ModelAndView view = new ModelAndView("new");
        view.addObject("types", List.of(new TypeCar("track"), new TypeCar("big"), new TypeCar("taxi")));


        return view;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView createAdvt(Advt newAdvt, ModelMap model) {


        Set<String> t = model.keySet();
        ModelAndView view = new ModelAndView("advt");



        return view;
    }
}
