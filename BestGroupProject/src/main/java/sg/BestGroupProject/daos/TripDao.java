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
    
    int createTrip(Trip trip);
    Trip getTripById (int id);
    void updatedTrip (Trip trip);
    Event addEvent (Event event);
    Event getEventById (int id);
    List<Event> getEventsByDate (LocalDate date, int tripId);
    List<Event> getEventsByWeek (List<LocalDate> week, int tripId);
    List<Event> getEventsByTrip (int tripId);
    void updateEvent (Event event);
    void deleteEvent (int id);
    List<SiteUser> getTeachersForTrip (int id);
    List<SiteUser> getStudentsForTrip (int id);
    
}
