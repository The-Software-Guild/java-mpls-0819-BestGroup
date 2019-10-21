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
import java.time.format.DateTimeFormatter;
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
        String insert = "INSERT INTO Trip (`name`, startdate, enddate) VALUES (?, ?, ?)";

        if (tripToAdd.getName() == null || tripToAdd.getStartDate() == null || tripToAdd.getEndDate() == null) {
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
    public void updateTrip(Trip trip) {
        String update = "UPDATE Trip SET `name` = ?, startdate = ?, enddate = ? WHERE id = ?";

        jdbc.update(update, trip.getName(), trip.getStartDate(), trip.getEndDate(), trip.getId());

    }

    @Override
    public Event addEvent(Event event) {
        String insert = "INSERT INTO Event (`name`, starttime, endtime, "
                + "location, description, categoryId, transportId, tripId)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement toReturn = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

                int categoryId = -1;

                if (event.getCategory() == Category.transportation) {
                    categoryId = 1;
                }
                if (event.getCategory() == Category.hotelReservation) {
                    categoryId = 2;
                }
                if (event.getCategory() == Category.attraction) {
                    categoryId = 3;
                }
                if (event.getCategory() == Category.meal) {
                    categoryId = 4;
                }
                if (event.getCategory() == Category.freeTime) {
                    categoryId = 5;
                }

                toReturn.setString(1, event.getName());
                toReturn.setString(2, event.getStartTime().toString());
                toReturn.setString(3, event.getEndTime().toString());
                toReturn.setString(4, event.getLocation());
                toReturn.setString(5, event.getDescription());
                toReturn.setInt(6, categoryId);
                toReturn.setString(7, event.getTransportationId());
                toReturn.setInt(8, event.getTripId());

                return toReturn;
            }
        };

        jdbc.update(psc, holder);

        int generatedId = holder.getKey().intValue();

        event.setId(generatedId);

        return event;

    }

    @Override
    public Event getEventById(int id) throws DaoException {
        String select = "SELECT id, name, starttime, endtime, "
                + "location, description, categoryid, transportid, tripid "
                + "FROM event WHERE id = ?";

        try {
            return jdbc.queryForObject(select, new EventMapper(), id);
        } catch (DataAccessException ex) {
            throw new DaoException("Event with ID: " + id + " does not exist.");
        }

    }

    @Override
    public List<Event> getEventsByTrip(int tripId) {
        String select = "SELECT * FROM Event WHERE TripId = ?";

        return jdbc.query(select, new EventMapper(), tripId);
    }

    @Override
    public void updateEvent(Event event) {
        String update = "UPDATE Event SET `name` = ?, starttime = ?, endtime = ?, "
                + "location = ?, description = ?, categoryid = ?, transportid = ?, tripid = ? WHERE id = ?";

        int categoryId = -1;

        if (event.getCategory() == Category.transportation) {
            categoryId = 1;
        }
        if (event.getCategory() == Category.hotelReservation) {
            categoryId = 2;
        }
        if (event.getCategory() == Category.attraction) {
            categoryId = 3;
        }
        if (event.getCategory() == Category.meal) {
            categoryId = 4;
        }
        if (event.getCategory() == Category.freeTime) {
            categoryId = 5;
        }

        jdbc.update(update, event.getName(), event.getStartTime(), event.getEndTime(),
                event.getLocation(), event.getDescription(), categoryId,
                event.getTransportationId(), event.getTripId(), event.getId());

    }

    @Override
    public void deleteEvent(int id) throws DaoException {
        String delete = "DELETE FROM Event WHERE id = ?";

        int rowsAffected = jdbc.update(delete, id);

        if (rowsAffected != 1) {
            throw new DaoException("Delete process failed.");
        }

    }

    @Override
    public List<Event> getEventsByDate(LocalDate date, int tripId) {
        String select = "Select *\n"
                + "from Event\n"
                + "where TripId = ? AND cast(StartTime as date) = ?;";

        return jdbc.query(select, new EventMapper(), tripId, date);

    }

    @Override
    public List<Event> getEventsByWeek(List<LocalDate> week, int tripId) {
        String select = "Select *\n"
                + "from Event\n"
                + "where TripId = ? AND cast(StartTime as date) = ?;";

        List<Event> eventsByWeek = new ArrayList();

        for (LocalDate date : week) {

            List<Event> eventsPerDay = new ArrayList();

            eventsPerDay = jdbc.query(select, new EventMapper(), tripId, date);

            for (Event event : eventsPerDay) {
                eventsByWeek.add(event);
            }
        }

        return eventsByWeek;

    }

    @Override
    public List<Trip> getTripsByUser(int userId) {
        String select = "SELECT FROM usertrip where userid = ?";
        
        return jdbc.query(select, new TripMapper(), userId);

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public Event mapRow(ResultSet results, int rowNum) throws SQLException {
            Event toReturn = new Event();

            toReturn.setId(results.getInt("id"));
            toReturn.setName(results.getString("name"));
            toReturn.setStartTime(LocalDateTime.parse(results.getString("starttime"), formatter));
            toReturn.setEndTime(LocalDateTime.parse(results.getString("endtime"), formatter));
            toReturn.setLocation(results.getString("location"));
            toReturn.setDescription(results.getString("description"));
            switch (results.getInt("categoryid")) {
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
