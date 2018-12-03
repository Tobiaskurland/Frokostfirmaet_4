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
    private Logger log = Logger.getLogger(UserController.class.getName());

    //Current User logged in
    private User currentUser = new User();

    private Kitchen currentKitchen = new Kitchen();

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
    private final String ACCEPT_KITCHEN = "kitchen/accept_kitchen";

    //JUDGE
    private final String INDEX_JUDGE = "judge/index_judge";
    private final String EVENT_JUDGE = "judge/event_judge";
    private final String JUDGE_JUDGE = "judge/judge_judge";
    private final String KITCHEN_JUDGE = "judge/kitchen_judge";

    //USER
    private final String INDEX_USER = "user/index_user";
    private final String EVENT_USER = "user/event_user";
    private final String JUDGE_USER = "user/judge_user";
    private final String KITCHEN_USER = "user/kitchen_user";


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
            }else if (currentUser.getRole() == 4){

                 return REDIRECT + INDEX_USER;
            }else {

                redirAttr.addFlashAttribute("loginError", true);

                return REDIRECT + LOGIN;
            }
        } else {

            redirAttr.addFlashAttribute("loginError", true);

            return REDIRECT + LOGIN;
        }
    }

//LOGOUT

    @GetMapping("/logout")
    public String logout(Model model){
        currentUser = new User();

        return REDIRECT + LOGIN;
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

//VERIFY

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

            return VERIFY;
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

        if(currentUser.getRole() == 2 || currentUser.getRole() == 4) { //checks if an kitchen is logged in
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

//KITCHEN FORM

    @GetMapping("/kitchen/kitchen_form")
    public String kitchenForm(Model model){
        log.info("AddKitchen action called...");
        if(currentUser.getRole() == 4) {
            model.addAttribute("kitchen", new Kitchen());

            return KITCHEN_FORM;
        } else if (currentUser.getRole() == 2){

            return INDEX_KITCHEN;
        } else if (currentUser.getRole() == 3){

            return INDEX_JUDGE;
        }
        return LOGIN;
    }

    @PostMapping("/kitchen/kitchen_form")
    public String kitchenForm(@ModelAttribute Kitchen kitchen, Model model){

        if(currentUser.getRole() == 4) {
            kitchen.setIduser(currentUser.getId());

            userService.addKitchen(kitchen);
            model.addAttribute("kitchens", userService.getKitchens());

            currentKitchen = kitchen;

            return ACCEPT_KITCHEN;
        }
        return LOGIN;
    }

    @PostMapping("/kitchen/accept_kitchen")
    public String kitchenAccept(Model model){
        if(currentUser.getRole() == 4){

            userService.addKitchenToEvent(currentKitchen.getId());

            return ACCEPT_KITCHEN;
        }
        return LOGIN;
    }

//JUDGE

    @GetMapping("/judge/index_judge")
    public String indexJudge(Model model){
        if(currentUser.getRole() == 3) {
            model.addAttribute("events", userService.getEvents());

            model.addAttribute("username", currentUser.getUsername());

            return INDEX_JUDGE;
        }
        return LOGIN;
    }

    @GetMapping("/judge/event_judge")
    public String eventJudge(Model model) {
        log.info("See eventJudge action called..");

        if(currentUser.getRole() == 3) { //checks if an judge is logged in
            List<Kitchen> k = userService.getKitchens();
            model.addAttribute("kitchens", k);

            List<Judge> j = userService.getJudges();
            model.addAttribute("judges", j);

            List<Event> e = userService.getEvents();
            model.addAttribute("events", e);

            model.addAttribute("username", currentUser.getUsername());

            return EVENT_JUDGE;
        }
        return LOGIN;
    }

    @GetMapping("/judge/kitchen_judge/{id}")
    public String readKitchenJudge(@PathVariable("id") int id, Model model) {
        log.info("Read kitchen with id: " + id);

        if(currentUser.getRole() == 3) {
            model.addAttribute("kitchen", userService.readKitchen(id));

            return KITCHEN_JUDGE;
        }
        return LOGIN;
    }

    @GetMapping("/judge/judge_judge/{id}")
    public String readJudgeJudge(@PathVariable("id") int id, Model model) {
        log.info("Read judge with id: " + id);

        if(currentUser.getRole() == 3) {
            model.addAttribute("judge", userService.readJudge(id));

            return JUDGE_JUDGE;
        }
        return LOGIN;
    }

//JUDGE FORM
    @GetMapping("/judge/judge_form")
    public String judgeForm(Model model){

        if(currentUser.getRole() == 4) {
            model.addAttribute("judge", new Judge());

            return JUDGE_FORM;
        } else if (currentUser.getRole() == 2){

            return INDEX_KITCHEN;
        } else if (currentUser.getRole() == 3){

            return INDEX_JUDGE;
        }
        return LOGIN;
    }

    @PutMapping("/judge/judge_form")
    public String judgeForm(@ModelAttribute Judge judge, Model model){


        judge.setIduser(currentUser.getId());

        userService.addJudge(judge);
        model.addAttribute("kitchens", userService.getKitchens());

        userService.addKitchenToEvent(judge.getId());

        return INDEX_USER;

    }

//USER

    @GetMapping("/user/index_user")
    public String indexUser(Model model){
        if(currentUser.getRole() == 4) {
            model.addAttribute("events", userService.getEvents());

            model.addAttribute("username", currentUser.getUsername());

            return INDEX_USER;
        }
        return LOGIN;
    }

    @GetMapping("/user/event_user")
    public String eventUser(Model model) {
        log.info("See eventJudge action called..");

        if(currentUser.getRole() == 4) { //checks if an judge is logged in
            List<Kitchen> k = userService.getKitchens();
            model.addAttribute("kitchens", k);

            List<Judge> j = userService.getJudges();
            model.addAttribute("judges", j);

            List<Event> e = userService.getEvents();
            model.addAttribute("events", e);

            model.addAttribute("username", currentUser.getUsername());

            return EVENT_USER;
        }
        return LOGIN;
    }

    @GetMapping("/user/kitchen_user/{id}")
    public String readKitchenUser(@PathVariable("id") int id, Model model) {
        log.info("Read kitchen with id: " + id);

        if(currentUser.getRole() == 4) {
            model.addAttribute("kitchen", userService.readKitchen(id));

            return KITCHEN_USER;
        }
        return LOGIN;
    }

    @GetMapping("/user/judge_user/{id}")
    public String readJudgeUser(@PathVariable("id") int id, Model model) {
        log.info("Read judge with id: " + id);

        if(currentUser.getRole() == 4) {
            model.addAttribute("judge", userService.readJudge(id));

            return JUDGE_USER;
        }
        return LOGIN;
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
        } else if (currentUser.getRole() == 4){
            model.addAttribute("isLoggedin", true);
            model.addAttribute("username", currentUser.getUsername());
        }
    }

}
