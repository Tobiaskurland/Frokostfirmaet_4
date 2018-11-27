package com.example.demo.Controllers;

import com.example.demo.Models.Event;
import com.example.demo.Models.User;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    //Logger
    Logger log = Logger.getLogger(UserController.class.getName());

    //Current User logged in
    User currentUser = new User();

    //RETURN STRINGS
    private final String REDIRECT = "redirect:/";
    private final String INDEX = "index";
    private final String LOGIN = "login";

    private final String EVENT = "event";

    //INDEX
    @GetMapping("/")
    public String index(){
        log.info("Index action called...");

        return INDEX;
    }

    //LOGIN
    @GetMapping("/login")
    public String login(Model model) {
        log.info("login called...");

        model.addAttribute("users", new User());
        model.addAttribute("pageTitle", "Login");
        model.addAttribute("isLogin", true);

        loginStatus(model);

        return LOGIN;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, RedirectAttributes redirAttr) {
        boolean loginMatch = false;
        loginMatch = userService.loginMatch(user);

        if(loginMatch == true) {
            redirAttr.addFlashAttribute("loginsuccess", true);
            redirAttr.addFlashAttribute("username", user.getUsername());

            currentUser = userService.loggedIn(user);

            return INDEX;
        }
        else {

            redirAttr.addFlashAttribute("loginError", true);

            return REDIRECT + LOGIN;
        }
    }

    @GetMapping("/event")
    public String event(Model model){
        log.info("See event action called..");

        List<Event> e = userService.getEvents();
        model.addAttribute("events", e);

        return EVENT;

    }



    //LOGIN STATUS
    public void loginStatus(Model model) {

        if (currentUser.getRole() == 1) {
            model.addAttribute("isLoggedin", true);
            model.addAttribute("isAdmin", true);
            model.addAttribute("username", currentUser.getUsername());
        } else if (currentUser.getRole() == 2) {
            model.addAttribute("isLoggedin", true);
            model.addAttribute("isKitchen", true);
            model.addAttribute("username", currentUser.getUsername());
        } else if (currentUser.getRole() == 3) {
            model.addAttribute("isLoggedin", true);
            model.addAttribute("isJudge", true);
            model.addAttribute("username", currentUser.getUsername());
        }
    }

}
