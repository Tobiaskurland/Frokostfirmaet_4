package com.example.demo.Controllers;

import com.example.demo.Models.Event;
import com.example.demo.Models.Judge;
import com.example.demo.Models.Kitchen;
import com.example.demo.Models.User;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    //GUEST
    private final String EVENT = "event";
    private final String KITCHEN = "kitchen/kitchen";
    private final String JUDGE = "judge/judge";

    //ADMIN
    private final String INDEX_ADMIN = "admin/index_admin";
    private final String EVENT_ADMIN = "admin/event_admin";
    private final String JUDGE_ADMIN = "admin/judge_admin";
    private final String KITCHEN_ADMIN = "admin/kitchen_admin";
    private final String VERIFY = "admin/verify";

    //KITCHEN
    private final String INDEX_KITCHEN = "kitchen/index_kitchen";
    private final String EVENT_KITCHEN = "kitchen/event_kitchen";
    private final String JUDGE_KITCHEN = "kitchen/judge_kitchen";
    private final String KITCHEN_KITCHEN = "kitchen/kitchen_kitchen";

    //JUDGE
    private final String INDEX_JUDGE = "judge/index_judge";
    private final String EVENT_JUDGE = "judge/event_judge";
    private final String JUDGE_JUDGE = "judge/judge_judge";
    private final String KITCHEN_JUDGE = "judge/kitchen_judge";


    //INDEX
    @GetMapping("/")
    public String index(Model model){
        log.info("Index action called...");

        List<Event> e = userService.getEvents();
        model.addAttribute("events", e);

        loginStatus(model);

        return INDEX;
    }

//LOGIN
    @GetMapping("/login")
    public String login(Model model) {
        log.info("login called...");

        model.addAttribute("users", new User());
        //model.addAttribute("pageTitle", "Login");
        model.addAttribute("isLogin", true);

        loginStatus(model);

        return LOGIN;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, RedirectAttributes redirAttr) {
        boolean loginMatch = false;
        loginMatch = userService.loginMatch(user);

        if (loginMatch == true) {
            redirAttr.addFlashAttribute("loginsuccess", true);
            redirAttr.addFlashAttribute("username", user.getUsername());

            currentUser = userService.loggedIn(user);

            if (currentUser.getRole() == 1) {

                return REDIRECT + INDEX_ADMIN;
            } else if (currentUser.getRole() == 2) {

                return REDIRECT + INDEX_KITCHEN;
            } else if (currentUser.getRole() == 3) {

                return REDIRECT + INDEX_JUDGE;
            }else {

                redirAttr.addFlashAttribute("loginError", true);

                return REDIRECT + LOGIN;
            }
        } else {

            redirAttr.addFlashAttribute("loginError", true);

            return REDIRECT + LOGIN;
        }
    }
//EVENT
    @GetMapping("/event")
    public String event(Model model){
        log.info("See event action called..");

        List<Kitchen> k = userService.getKitchens();
        model.addAttribute("kitchens", k);

        List<Judge> j = userService.getJudges();
        model.addAttribute("judges", j);

        List<Event> e = userService.getEvents();
        model.addAttribute("events", e);

        loginStatus(model);

        return EVENT;

    }

//DETAILS
    @GetMapping("/kitchen/kitchen/{id}")
    public String readKitchen(@PathVariable("id") int id, Model model) {
        log.info("Read kitchen with id: " + id);

        model.addAttribute("kitchen", userService.readKitchen(id));

        loginStatus(model);

        return KITCHEN;
    }

    @GetMapping("/judge/judge/{id}")
    public String readJudge(@PathVariable("id") int id, Model model) {
        log.info("Read judge with id: " + id);

        model.addAttribute("judge", userService.readJudge(id));

        loginStatus(model);

        return JUDGE;
    }

//ADMIN
    @GetMapping("/admin/index_admin")
    public String indexAdmin(Model model){
        log.info("IndexAdmin action called...");

        List<Event> e = userService.getEvents();
        model.addAttribute("events", e);

        loginStatus(model);

        return INDEX_ADMIN;
    }

    @GetMapping("/admin/event_admin")
    public String eventAdmin(Model model) {
        log.info("See eventAdmin action called..");

        List<Kitchen> k = userService.getKitchens();
        model.addAttribute("kitchens", k);

        List<Judge> j = userService.getJudges();
        model.addAttribute("judges", j);

        List<Event> e = userService.getEvents();
        model.addAttribute("events", e);

        loginStatus(model);

        return EVENT_ADMIN;
    }

    @GetMapping("/admin/kitchen_admin/{id}")
    public String readKitchenAdmin(@PathVariable("id") int id, Model model) {
        log.info("Read kitchenAdmin with id: " + id);

        model.addAttribute("kitchen", userService.readKitchen(id));

        loginStatus(model);

        return KITCHEN_ADMIN;
    }

    @GetMapping("/admin/judge_admin/{id}")
    public String readJudgeAdmin(@PathVariable("id") int id, Model model) {
        log.info("Read judgeAdmin with id: " + id);

        model.addAttribute("judge", userService.readJudge(id));

        loginStatus(model);

        return JUDGE_ADMIN;
    }

    @GetMapping("/admin/verify")
    public String verify(Model model){
        log.info("Verify action called...");

        model.addAttribute("kitchens", userService.getKitchens());

        loginStatus(model);

        return VERIFY;
    }

    @PutMapping("/admin/verify/{id}")
    public String verify(@PathVariable("id") int id, Model model) {
        log.info("Verify put action called...");

        userService.confirmKitchen(id);

        model.addAttribute("kitchen", userService.getKitchens());

        loginStatus(model);

        return REDIRECT + VERIFY;
    }

//KITCHEN
    @GetMapping("/kitchen/index_kitchen")
    public String eventKitchen(Model model){

        model.addAttribute("events", userService.getEvents());

        loginStatus(model);

        return INDEX_KITCHEN;
    }

//JUDGE

    @GetMapping("/judge/index_judge")
    public String eventJudge(Model model){

        model.addAttribute("events", userService.getEvents());

        loginStatus(model);

        return INDEX_JUDGE;
    }

//LOGIN STATUS
    public void loginStatus(Model model) {

        if (currentUser.getRole() == 1) {
            model.addAttribute("isLoggedin", true);
            model.addAttribute("isAdmin", true);
            model.addAttribute("username", currentUser.getUsername());
        } else if (currentUser.getRole() == 2) {
            model.addAttribute("isLoggedin", true);
            model.addAttribute("username", currentUser.getUsername());
        } else if (currentUser.getRole() == 3) {
            model.addAttribute("isLoggedin", true);
            model.addAttribute("username", currentUser.getUsername());
        }
    }

}
