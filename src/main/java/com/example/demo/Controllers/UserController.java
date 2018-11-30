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
    private final String JUDGE_FORM = "judge/judge_form";
    private final String KITCHEN_FORM = "kitchen/kitchen_form";

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


        return EVENT;

    }

    @GetMapping("/judge/judge_form")
    public String judgeForm(Model model){

        return JUDGE_FORM;
    }

    @GetMapping("/kitchen/kitchen_form")
    public String kitchenForm(Model model){

        return KITCHEN_FORM;
    }

//DETAILS
    @GetMapping("/kitchen/kitchen/{id}")
    public String readKitchen(@PathVariable("id") int id, Model model) {
        log.info("Read kitchen with id: " + id);

        model.addAttribute("kitchen", userService.readKitchen(id));

        return KITCHEN;
    }

    @GetMapping("/judge/judge/{id}")
    public String readJudge(@PathVariable("id") int id, Model model) {
        log.info("Read judge with id: " + id);

        model.addAttribute("judge", userService.readJudge(id));

        return JUDGE;
    }

//ADMIN
    @GetMapping("/admin/index_admin")
    public String indexAdmin(Model model){
        log.info("IndexAdmin action called...");

        if(currentUser.getRole() == 1) { //checks if an admin is logged in
            List<Event> e = userService.getEvents();
            model.addAttribute("events", e);

            model.addAttribute("username", currentUser.getUsername());

            return INDEX_ADMIN;
        }
        return LOGIN;
    }

    @GetMapping("/admin/event_admin")
    public String eventAdmin(Model model) {
        log.info("See eventAdmin action called..");
        if(currentUser.getRole() == 1) { //checks if an admin is logged in
            List<Kitchen> k = userService.getKitchens();
            model.addAttribute("kitchens", k);

            List<Judge> j = userService.getJudges();
            model.addAttribute("judges", j);

            List<Event> e = userService.getEvents();
            model.addAttribute("events", e);

            model.addAttribute("username", currentUser.getUsername());

            return EVENT_ADMIN;
        }
        return LOGIN;
    }

    @GetMapping("/admin/kitchen_admin/{id}")
    public String readKitchenAdmin(@PathVariable("id") int id, Model model) {
        log.info("Read kitchenAdmin with id: " + id);
        if(currentUser.getRole() == 1) { //checks if an admin is logged in
            model.addAttribute("kitchen", userService.readKitchen(id));

            model.addAttribute("username", currentUser.getUsername());

            return KITCHEN_ADMIN;
        }
        return LOGIN;
    }

    @GetMapping("/admin/judge_admin/{id}")
    public String readJudgeAdmin(@PathVariable("id") int id, Model model) {
        log.info("Read judgeAdmin with id: " + id);
        if(currentUser.getRole() == 1) { //checks if an admin is logged in
            model.addAttribute("judge", userService.readJudge(id));

            model.addAttribute("username", currentUser.getUsername());

            return JUDGE_ADMIN;
        }
        return LOGIN;
    }

    @GetMapping("/admin/verify")
    public String verify(Model model){
        log.info("Verify action called...");
        if(currentUser.getRole() == 1) { //checks if an admin is logged in
            model.addAttribute("kitchens", userService.getKitchens());

            model.addAttribute("username", currentUser.getUsername());

            return VERIFY;
        }
        return LOGIN;
    }

    @PutMapping("/admin/verify/{id}")
    public String verify(@PathVariable("id") int id, Model model) {
        log.info("Verify put action called...");
        if(currentUser.getRole() == 1) { //checks if an admin is logged in

            userService.confirmKitchen(id);

            //model.addAttribute("kitchen", userService.getKitchens());

            model.addAttribute("username", currentUser.getUsername());

            return REDIRECT + VERIFY;
        }
        return LOGIN;
    }

//KITCHEN
    @GetMapping("/kitchen/index_kitchen")
    public String indexKitchen(Model model){
        if(currentUser.getRole() == 2) {
            model.addAttribute("events", userService.getEvents());

            model.addAttribute("username", currentUser.getUsername());

            return INDEX_KITCHEN;
        }
        return LOGIN;
    }

    @GetMapping("/kitchen/event_kitchen")
    public String eventKitchen(Model model) {
        log.info("See eventKitchen action called..");

        if(currentUser.getRole() == 2) { //checks if an kitchen is logged in
            List<Kitchen> k = userService.getKitchens();
            model.addAttribute("kitchens", k);

            List<Judge> j = userService.getJudges();
            model.addAttribute("judges", j);

            List<Event> e = userService.getEvents();
            model.addAttribute("events", e);

            model.addAttribute("username", currentUser.getUsername());

            return EVENT_KITCHEN;
        }
        return LOGIN;
    }

    @GetMapping("/kitchen/kitchen_kitchen/{id}")
    public String readKitchenKitchen(@PathVariable("id") int id, Model model) {
        log.info("Read kitchen with id: " + id);

        if(currentUser.getRole() == 2) {
            model.addAttribute("kitchen", userService.readKitchen(id));

            return KITCHEN_KITCHEN;
        }
        return LOGIN;
    }

    @GetMapping("/kitchen/judge_kitchen/{id}")
    public String readJudgeKitchen(@PathVariable("id") int id, Model model) {
        log.info("Read judge with id: " + id);

        if(currentUser.getRole() == 2) {
            model.addAttribute("judge", userService.readJudge(id));

            return JUDGE_KITCHEN;
        }
        return LOGIN;
    }

//JUDGE

    @GetMapping("/judge/index_judge")
    public String eventJudge(Model model){

        model.addAttribute("events", userService.getEvents());

        model.addAttribute("username", currentUser.getUsername());

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
