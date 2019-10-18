/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import sg.BestGroupProject.models.Category;
import sg.BestGroupProject.models.Event;
import sg.BestGroupProject.models.SiteUser;
import sg.BestGroupProject.models.Trip;

/**
 *
 * @author ddubs
 */
@Component
public class TripDbDao implements TripDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public int createTrip(Trip tripToAdd) throws DaoException {
        String insert = "INSERT INTO Trip (name, startdate, enddate) VALUES (?, ?, ?)";

        if(tripToAdd.getName() == null || tripToAdd.getStartDate() == null || tripToAdd.getEndDate() == null){
            throw new DaoException("Please enter data for all fields. Name, startdate and enddate cannot be left blank.");
        }
        
        
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

                toReturn.setString(1, tripToAdd.getName());
                toReturn.setString(2, tripToAdd.getStartDate().toString());
                toReturn.setString(3, tripToAdd.getEndDate().toString());

                return toReturn;
            }
        };
        
        jdbc.update(psc, holder);

        int generatedId = holder.getKey().intValue();

        tripToAdd.setId(generatedId);

        List<SiteUser> teachers = new ArrayList();
        
        // Add Teacher who created the trip to the teacher list

        tripToAdd.setTeachers(teachers);
        
        return tripToAdd.getId();

    }

    @Override
    public Trip getTripById(int id) throws DaoException {
        String select = "SELECT id, name, startdate, enddate FROM trip where id = ?";

        try {
            return jdbc.queryForObject(select, new TripMapper(), id);

        } catch (DataAccessException ex) {
            throw new DaoException("Trip with Id " + id + " does not exist.");
        }
    }

    @Override
    public void updatedTrip(Trip trip) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Event addEvent(Event event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Event getEventById(int id) throws DaoException {
       String select = "SELECT id, name, starttime, endtime, "
               + "location, description, categoryid, transportid, tripid "
               + "FROM event WHERE id = ?";
       
       try{
           return jdbc.queryForObject(select, new EventMapper(), id);
       }catch(DataAccessException ex){
           throw new DaoException("Event with ID: " + id + " does not exist.");
       }
        
    }

    @Override
    public List<Event> getEventsByDate(LocalDate date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Event> getEventsByWeek(List<LocalDate> week) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Event> getEventsByTrip(int tripId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateEvent(Event event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteEvent(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



    private static class TripMapper implements RowMapper<Trip> {

        @Override
        public Trip mapRow(ResultSet results, int rowNum) throws SQLException {
            Trip toReturn = new Trip();

            toReturn.setId(results.getInt("id"));
            toReturn.setName(results.getString("name"));
            toReturn.setStartDate(LocalDate.parse(results.getString("startdate")));
            toReturn.setEndDate(LocalDate.parse(results.getString("enddate")));

            return toReturn;
        }
    }

    private static class EventMapper implements RowMapper<Event> {

        @Override
        public Event mapRow(ResultSet results, int rowNum) throws SQLException {
            Event toReturn = new Event();
            
            toReturn.setId(results.getInt("id"));
            toReturn.setName(results.getString("name"));
            toReturn.setStartTime(LocalDateTime.parse(results.getString("starttime")));
            toReturn.setEndTime(LocalDateTime.parse(results.getString("endtime")));
            toReturn.setLocation(results.getString("location"));
            toReturn.setDescription(results.getString("description"));
            switch(results.getInt("categoryid")){
                case 1:
                 toReturn.setCategory(Category.transportation);
                 break;
                case 2:
                 toReturn.setCategory(Category.hotelReservation);
                 break;
                case 3:
                 toReturn.setCategory(Category.attraction);
                 break;
                case 4:
                 toReturn.setCategory(Category.meal);
                 break;
                case 5:
                 toReturn.setCategory(Category.freeTime);
                 break;   
            }
            toReturn.setTransportationId(results.getString("TransportId"));
            toReturn.setTripId(results.getInt("TripId"));
            
            return toReturn;
            
        }
    }

}
