package com.example.demo.Repositories;

import com.example.demo.Models.Event;
import com.example.demo.Models.Judge;
import com.example.demo.Models.Kitchen;
import com.example.demo.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
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

        String sql = "INSERT INTO event values (default, ?, ?, ?)";
        jdbc.update(sql, event.getName(), event.getDescription(), event.getDate());

        return event;
    }

    @Override
    public Event readEvent(int id) {
        return null;
    }

    @Override
    public Event editEvent(int id, Event event) {


        String sql = "UPDATE event SET name=?, description=?, date=? WHERE idevent=?";
        jdbc.update(sql, event.getName(), event.getDescription(), event.getDate());
        return event;
    }

    @Override
    public boolean deleteEvent(int id) {
        return false;
    }


//KITCHENS

    @Override
    public List<Kitchen> getKitchens() {
        ArrayList<Kitchen> k = new ArrayList<>();

        String sql = "SELECT idevent_kitchen, kitchen.idkitchen, " +
                "kitchen.name, kitchen.address, kitchen.description, kitchen.picture, kitchen.iduser, user.username, user.password, user.idrole, kitchen.verified " +
                "FROM event_kitchen " +
                "INNER JOIN event ON event_kitchen.idevent = event.idevent " +
                "INNER JOIN kitchen ON event_kitchen.idkitchen = kitchen.idkitchen " +
                "INNER JOIN user ON kitchen.iduser = user.iduser " +
                "INNER JOIN role ON role.idrole = user.idrole";

        return this.jdbc.query(sql, new ResultSetExtractor<java.util.List<Kitchen>>() {

            @Override
            public List<Kitchen> extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()) {
                    int ideventK = rs.getInt(1);
                    int idkit = rs.getInt(2);
                    String kName = rs.getString(3);
                    String adresse = rs.getString(4);
                    String kDescription = rs.getString(5);
                    String picture = rs.getString(6);
                    int iduser = rs.getInt(7);
                    String username = rs.getString(8);
                    String password = rs.getString(9);
                    int role = rs.getInt(10);
                    boolean f = rs.getBoolean(11);

                    Kitchen kitchen = new Kitchen (idkit,username, password, role, kName, adresse, kDescription, picture, f);

                    k.add(kitchen);

                }

                return k;
            }
        });
    }


    @Override
    public Kitchen addKitchen(Kitchen kitchen) {

       return null; //TODO
    }

    @Override
    public Kitchen readKitchen(int id) {

        String sql = "SELECT idkitchen, name, address, description FROM kitchen " +
                "WHERE idkitchen=? ";

        RowMapper<Kitchen> rowMapper = new BeanPropertyRowMapper<>(Kitchen.class);
        Kitchen kitchen = jdbc.queryForObject(sql, rowMapper, id);
        return kitchen;
    }

    @Override
    public Kitchen editKitchen(int id, Kitchen kitchen) {
        return null;
    }//TODO

    @Override
    public boolean deleteKitchen(int id) {
        return false;
    }//TODO

//JUDGES

    @Override
    public List<Judge> getJudges() {
        ArrayList<Judge> j = new ArrayList<>();

        String sql = "SELECT idevent_judge, event.idevent, judge.idjudge, " +
                "judge.firstname, judge.lastname, judge.profession, judge.jobdescription, " +
                "judge.iduser, user.username, user.password, user.idrole, judge.verified " +
                "FROM event_judge " +
                "INNER JOIN event ON event_judge.idevent = event.idevent " +
                "INNER JOIN judge ON event_judge.idjudge = judge.idjudge " +
                "INNER JOIN user ON judge.iduser = user.iduser " +
                "INNER JOIN role ON role.idrole = user.idrole";

        return this.jdbc.query(sql, new ResultSetExtractor<java.util.List<Judge>>() {

            @Override
            public List<Judge> extractData(ResultSet rs) throws SQLException, DataAccessException {

                while (rs.next()) {
                    int c = rs.getInt(3);
                    String firstname = rs.getString(4);
                    String lastname = rs.getString(5);
                    String prof = rs.getString(6);
                    String jd = rs.getString(7);
                    int ur = rs.getInt(8);
                    String username = rs.getString(9);
                    String password = rs.getString(10);
                    int r = rs.getInt(11);
                    boolean f = rs.getBoolean(12);

                    Judge judge = new Judge(c, username, password, r, firstname, lastname, prof, jd, f);
                    j.add(judge);
                }

                return j;
            }
        });
    }

    @Override
    public Judge addJudge(Judge judge) {
        return null;
    }//TODO

    @Override
    public Judge readJudge(int id) {

        String sql = "SELECT idjudge, firstname, lastname, profession, jobdescription FROM judge " +
                "WHERE idjudge=? ";

        RowMapper<Judge> rowMapper = new BeanPropertyRowMapper<>(Judge.class);
        Judge judge = jdbc.queryForObject(sql, rowMapper, id);
        return judge;
    }

    @Override
    public Judge editJudge(int id, Judge judge) {
        return null;
    }//TODO

    @Override
    public boolean deleteJudge(int id) {
        return false;
    }//TODO

//LOGGEDIN USER

    @Override
    public User findLogin(String un, String pas) {

        String sql = "SELECT iduser, username, password, idrole FROM user " +
                    "WHERE username = ? AND password = ?";

        return this.jdbc.query(sql, new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet rs) throws SQLException, DataAccessException {
                String userName, pass;
                int id, role;
                User user = new User();

                while (rs.next()) {
                    id = rs.getInt("iduser");
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
    public void confirmKitchen(int id) {

        String sql = "UPDATE kitchen SET verified=1 WHERE idkitchen=?";
        jdbc.update(sql, id);

    }

    @Override
    public boolean confirmJudge(Judge judge) {
        return false;
    }
}
