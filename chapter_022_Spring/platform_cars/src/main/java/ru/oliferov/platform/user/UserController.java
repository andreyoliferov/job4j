package ru.oliferov.platform.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.oliferov.platform.user.models.User;
import ru.oliferov.platform.user.service.UserService;

/**
 * @autor aoliferov
 * @since 24.03.2019
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String showMain(ModelMap model) {
        User user = userService.getById(1);
        User u2 = userService.getByName("guest");
        return "main";
    }
}
