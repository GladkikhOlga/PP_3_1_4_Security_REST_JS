package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
//работает
    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }
    //работает
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }
    //работает
    @GetMapping("/new")
    public String createUserForm(@ModelAttribute("newUser") User user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "/new";
    }
    // НЕ РАБОТАЕТ
    @PostMapping("/new")
    public String createUser(@ModelAttribute("newUser") User user,
                             @RequestParam("roles") Set<Role> roles) {
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

//работает
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
//работает
    @GetMapping("/edit/{id}")
    public String updateUserForm(@PathVariable("id")Long id, Model model) {
        model.addAttribute("user",userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "/edit";
    }
// НЕ РАБОТАЕТ
    @PatchMapping ("/edit/{id}")
    public String updateUser(User user,@RequestParam("roles")Set<Role> roles) {
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }
}
