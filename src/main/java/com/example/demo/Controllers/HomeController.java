package com.example.demo.Controllers;

import com.example.demo.Models.Event;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    //LOGGER
    Logger log = Logger.getLogger(HomeController.class.getName());

    //RETURN STRINGS
    private final String INDEX = "index";

    /*@GetMapping("/")
    public String index(){
        log.info("Index action called...");

        return INDEX;
    }*/
}
