package com.example.tester.controllers;

import com.example.tester.models.Role;
import com.example.tester.models.User;
import com.example.tester.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@Controller
public class SignController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registratiob(){
        return "registration";
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST, params = "regUser")
    public String postRegistration(User user, Model model,@RequestParam String username) {

        if(userRepository.findByUsername(username) != null){
            model.addAttribute("message","Пользователь с данным логином уже существует!");
            return "registration";
        }

            user.setRoles(Collections.singleton(Role.ROLE_USER));
            user.setActive(true);
            userRepository.save(user);
        return "redirect:/login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST, params = "regAdmin")
    public String postRegistrationAdmin(User user, Model model,@RequestParam String username) {

        if(userRepository.findByUsername(username) != null){
            model.addAttribute("message","Пользователь с данным логином уже существует!");
            return "registration";
        }

        user.setRoles(Collections.singleton(Role.ROLE_ADMIN));
        user.setActive(true);
        userRepository.save(user);
        return "redirect:/login";
    }

}
