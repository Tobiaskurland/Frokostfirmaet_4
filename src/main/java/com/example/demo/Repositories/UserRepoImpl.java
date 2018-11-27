package com.example.demo.Repositories;

import com.example.demo.Models.Event;
import com.example.demo.Models.Judge;
import com.example.demo.Models.Kitchen;
import com.example.demo.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo{

    @Autowired
    JdbcTemplate jdbc;

//EVENTS

    @Override
    public List<Event> getEvents() {

        ArrayList<Event> e = new ArrayList<>();

        String sql = "SELECT name, description, date FROM event";


        // Fra sql til list.
        // Manuelt i stedet.
        return this.jdbc.query(sql, new ResultSetExtractor<java.util.List<Event>>() {

            @Override
            public java.util.List<Event> extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()) {
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String date = rs.getString("date");


                    Event event = new Event(name, description, date);

                    e.add(event);
                }
                return e;
            }
        });
    }

    @Override
    public Event addEvent(Event event) {
        return null;
    }

    @Override
    public Event readEvent(int id) {
        return null;
    }

    @Override
    public Event editEvent(int id) {
        return null;
    }

    @Override
    public boolean deleteEvent(int id) {
        return false;
    }

//KITCHENS

    @Override
    public List<Kitchen> getKitchens() {
        return null;
    }

    @Override
    public Kitchen addKitchen(Kitchen kitchen) {
        return null;
    }

    @Override
    public Kitchen readKitchen(int id) {
        return null;
    }

    @Override
    public Kitchen editKitchen(int id) {
        return null;
    }

    @Override
    public boolean deleteKitchen(int id) {
        return false;
    }

//JUDGES

    @Override
    public List<Judge> getJudges() {
        return null;
    }

    @Override
    public Judge addJudge(Judge judge) {
        return null;
    }

    @Override
    public Judge readJudge(int id) {
        return null;
    }

    @Override
    public Judge editJudge(int id) {
        return null;
    }

    @Override
    public boolean deleteJudge(int id) {
        return false;
    }

//LOGGEDIN USER

    @Override
    public User findLogin(String un, String pas) {

        String sql = "SELECT iduser_role, user.username, user.password, role.idrole FROM user_role " +
                "INNER JOIN user ON user_role.iduser = user.iduser " +
                "INNER JOIN role ON user_role.idrole = role.idrole " +
                "WHERE username = ? AND password = ?";

        return this.jdbc.query(sql, new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet rs) throws SQLException, DataAccessException {
                String userName, pass;
                int id, role;
                User user = new User();

                while (rs.next()) {
                    id = rs.getInt("iduser_role");
                    userName = rs.getString("username");
                    pass = rs.getString("password");
                    role = rs.getInt("idrole");

                    user.setUsername(userName);
                    user.setPassword(pass);
                    user.setId(id);
                    user.setRole(role);
                }
                return user;
            }
        },un, pas);
    }


//CONFIRMS

    @Override
    public boolean confirmKitchen(Kitchen kitchen) {
        return false;
    }

    @Override
    public boolean confirmJudge(Judge judge) {
        return false;
    }
}
