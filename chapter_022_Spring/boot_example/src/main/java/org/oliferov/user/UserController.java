package org.oliferov.user;

import org.oliferov.user.models.User;
import org.oliferov.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @autor aoliferov
 * @since 24.03.2019
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //сериализация происходит автоматически если подключен Jackson (в spring boot jackson встроен)
    @RequestMapping(value = "/user/id", method = RequestMethod.GET)
    public User getUser(@RequestParam(value = "id") int id) {
        return userService.getById(id);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User putUser(@RequestParam(value = "name") String name) {
        User user = new User();
        user.setName(name);
        return userService.addUser(user);
    }
}
