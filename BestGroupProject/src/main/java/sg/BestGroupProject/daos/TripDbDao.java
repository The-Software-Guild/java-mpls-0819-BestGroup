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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import sg.BestGroupProject.models.Event;
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
    public int createTrip(Trip tripToAdd) {
        String insert = "INSERT INTO Trip (name, startdate, enddate) VALUES (?, ?, ?)";
        
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

        return tripToAdd.getId();

    }

    @Override
    public Trip getTripById(int id) throws DaoException {
        String Select = "SELECT id, name, startdate, enddate FROM trip where id = ?";
        try{
        return jdbc.queryForObject(Select, new TripMapper(), id);
        
        }catch(DataAccessException ex){
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
    public Event getEventById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
