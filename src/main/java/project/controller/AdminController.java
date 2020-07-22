package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.model.Role;
import project.model.User;
import project.service.UserService;

import java.util.Collections;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    @Autowired
    UserService userService;

    @PostMapping("/add")
    public String add(User user, Model model) {
        user.setRoles(Collections.singleton(new Role("ADMIN")));
        userService.add(user);
        model.addAttribute("listz", userService.listUsers());
        return "list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @GetMapping("/delete")
    public String delete(long id, Model model) {
        userService.remove(id);
        model.addAttribute("listz", userService.listUsers());
        return "list";
    }

    @GetMapping("/list")
    public String allUser(Model model) {
        model.addAttribute("listz", userService.listUsers());
        return "list";
    }

    @GetMapping(value = "/edit")
    public String edit(Model model, long id) {
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(Model model, User user) {
        userService.edit(user.getId(), user);
        model.addAttribute("listz", userService.listUsers());
        return "list";
    }
}
