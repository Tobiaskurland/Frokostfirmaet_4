package com.example.demo.Repositories;

import com.example.demo.Models.Event;
import com.example.demo.Models.Judge;
import com.example.demo.Models.Kitchen;
import com.example.demo.Models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo {

    //EVENTS
    java.util.List<Event> getEvents();
    Event addEvent(Event event);
    Event readEvent(int id);
    Event editEvent(int id);
    boolean deleteEvent(int id);

    //KITCHENS
    List<Kitchen> getKitchens();
    Kitchen addKitchen(Kitchen kitchen);
    Kitchen readKitchen(int id);
    Kitchen editKitchen(int id);
    boolean deleteKitchen(int id);

    //JUDGE
    List<Judge> getJudges();
    Judge addJudge(Judge judge);
    Judge readJudge(int id);
    Judge editJudge(int id);
    boolean deleteJudge(int id);

    //LOGGEDIN USER
    User findLogin(String username, String password);

    //CONFIRM
    boolean confirmKitchen(Kitchen kitchen);
    boolean confirmJudge(Judge judge);
}
