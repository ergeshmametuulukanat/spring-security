package com.company.controller;

import com.company.dao.RoleDao;
import com.company.model.Role;
import com.company.model.User;
import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleDao roleDao;

    @GetMapping(value = "/")
    public String getHomePage() {
        return "home-page";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/user")
    public String getUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user-page";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @GetMapping("/newUser")
    public String newUser(User user, Model model) {
        model.addAttribute("user", user);
        List<Role> roles = roleDao.getAllRoles();
        model.addAttribute("allRoles", roles);
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(User user, Model model) {
        System.out.println(user.getRoles());
        userService.add(user);
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        List<Role> roles = roleDao.getAllRoles();
        model.addAttribute("allRoles", roles);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, User user, Model model) {
        userService.update(user);
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userService.delete(userService.getById(id));
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }
}
