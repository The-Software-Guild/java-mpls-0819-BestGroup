/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.daos;

import java.time.LocalDate;
import java.util.List;
import sg.BestGroupProject.models.Event;
import sg.BestGroupProject.models.SiteUser;
import sg.BestGroupProject.models.Trip;

/**
 *
 * @author Tamara
 */
public interface TripDao {
    
    int createTrip(Trip trip) throws DaoException;
    Trip getTripById (int id) throws DaoException;
    void updateTrip (Trip trip);
    Event addEvent (Event event);
    Event getEventById (int id) throws DaoException;
    List<Event> getEventsByDate (LocalDate date, int tripId);
    List<Event> getEventsByWeek (List<LocalDate> week, int tripId);
    List<Event> getEventsByTrip (int tripId);
    void updateEvent (Event event);
    void deleteEvent (int id) throws DaoException;
    List<Trip> getTripsByUser (int userId);
    
}
