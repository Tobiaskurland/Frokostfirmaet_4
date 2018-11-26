package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final String INDEX = "index";

    @GetMapping("/")
    public String index(Model model){

        return INDEX;
    }
}
