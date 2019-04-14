package ru.oliferov.platform.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.oliferov.platform.user.models.User;
import ru.oliferov.platform.user.service.UserService;

/**
 * @autor aoliferov
 * @since 24.03.2019
 */
@Controller
//@RestController аннотация заменяет @Controller + @ResponseBody для создания чистого REST
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String showMain(ModelMap model) {
        User user = userService.getById(1);
        User u2 = userService.getByName("guest");
        return "main";
    }

    //сериализация происходит автоматически если подключен Jackson (в spring boot jackson встроен)
    @RequestMapping(value = "/user/id", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@RequestParam(value="id") int id) {
        return userService.getById(id);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public User putUser(@RequestParam(value="name") String name) {
        User user = new User();
        user.setName(name);
        return userService.addUser(user);
    }
}
