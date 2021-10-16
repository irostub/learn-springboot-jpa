package com.irostub.learnspringbootjpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String helloPage(Model model) {
        model.addAttribute("data", "String Boot & JPA");
        return "hello";
    }
}
