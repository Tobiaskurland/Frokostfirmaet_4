package com.example.demo.Services;

import com.example.demo.Models.Event;
import com.example.demo.Models.Judge;
import com.example.demo.Models.Kitchen;
import com.example.demo.Models.User;
import com.example.demo.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;

//EVENTS

    @Override
    public List<Event> getEvents() {

        return userRepo.getEvents();
    }

    @Override
    public Event addEvent(Event event) {

        return userRepo.addEvent(event);
    }

    @Override
    public Event readEvent(int id) {

        return userRepo.readEvent(id);
    }

    @Override
    public Event editEvent(int id, Event event) {

        return userRepo.editEvent(id, event);
    }

    @Override
    public boolean deleteEvent(int id) {

        return userRepo.deleteEvent(id);
    }

//KITCHENS

    @Override
    public List<Kitchen> getKitchens() {

        return userRepo.getKitchens();
    }

    @Override
    public Kitchen addKitchen(Kitchen kitchen) {

        return userRepo.addKitchen(kitchen);
    }

    @Override
    public Kitchen readKitchen(int id) {

        return userRepo.readKitchen(id);
    }

    @Override
    public Kitchen editKitchen(int id, Kitchen kitchen) {

        return userRepo.editKitchen(id, kitchen);
    }

    @Override
    public boolean deleteKitchen(int id) {

        return userRepo.deleteKitchen(id);
    }

//JUDGES

    @Override
    public List<Judge> getJudges() {

        return userRepo.getJudges();
    }

    @Override
    public Judge addJudge(Judge judge) {

        return userRepo.addJudge(judge);
    }

    @Override
    public Judge readJudge(int id) {

        return userRepo.readJudge(id);
    }

    @Override
    public Judge editJudge(int id, Judge judge) {

        return userRepo.editJudge(id, judge);
    }

    @Override
    public boolean deleteJudge(int id) {

        return userRepo.deleteJudge(id);
    }

//LOGGEDIN USER

    public boolean loginMatch(User user) {
        boolean loginMatch;

        User loginUser = userRepo.findLogin(user.getUsername(),user.getPassword());

        if(loginUser.getUsername() != null && loginUser.getPassword() != null) {
            loginMatch = true;
        }
        else{
            loginMatch = false;
        }

        return loginMatch;
    }

    @Override
    public User loggedIn(User user) {

        user = userRepo.findLogin(user.getUsername(),user.getPassword());
        return user;
    }


//CONFIRMS

    @Override
    public boolean confirmKitchen(Kitchen kitchen) {

        return userRepo.confirmKitchen(kitchen);
    }

    @Override
    public boolean confirmJudge(Judge judge) {

        return userRepo.confirmJudge(judge);
    }
}
