/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sg.BestGroupProject.daos.DaoException;
import sg.BestGroupProject.daos.TripDao;
import sg.BestGroupProject.daos.UserDao;
import sg.BestGroupProject.models.DayOfActivity;
import sg.BestGroupProject.models.Event;
import sg.BestGroupProject.models.SiteUser;
import sg.BestGroupProject.models.Trip;

/**
 *
 * @author Tamara
 */
@Component
public class TripService {

    @Autowired
    TripDao tDao;

    @Autowired
    UserDao uDao;

    public Response<Integer> createTrip(Trip trip) {
        Response response = new Response();

        if (trip == null || trip.getName().isEmpty()) {
            response.setMessage("Trip must have a name to be created.");
        } else {
            try {
                int id = tDao.createTrip(trip);

                response.setData(id);
                response.setSuccess(true);
            } catch (DaoException ex) {
                response.setMessage(ex.getMessage());
            }
        }
        return response;
    }

    public Response<Trip> getTripById(int id) {
        Response response = new Response();

        if (id <= 0) {
            response.setMessage("Invalid id");
        } else {
            try {
                Trip trip = tDao.getTripById(id);
                List<Event> events = tDao.getEventsByTrip(id);
                List<SiteUser> travellers = uDao.getAllUsers();
                List<SiteUser> teachers = travellers.stream().filter(i -> i.getRoles().size() == 2).collect(Collectors.toList());
                List<SiteUser> students = travellers.stream().filter(i -> i.getRoles().size() == 2).collect(Collectors.toList());
                trip.setTeachers(teachers);
                trip.setStudents(students);
                trip.setEvents(events);
                response.setData(trip);
                response.setSuccess(true);
            } catch (DaoException ex) {
                response.setMessage(ex.getMessage());
            }

        }

        return response;
    }

    public Response updateTrip(Trip trip) {
        Response response = new Response();

        if (trip == null) {
            response.setMessage("Invalid trip");
        } else if (trip.getStartDate() == null) {
            response.setMessage("Start date needed");
        } else if (trip.getEndDate() == null) {
            response.setMessage("End date needed");
        } else if (trip.getName() == null) {
            response.setMessage("Trip name needed");
        } else if (trip.getTeachers().isEmpty()) {
            response.setMessage("Please add a teacher to the trip");
        } else {
            tDao.updateTrip(trip);
            response.setSuccess(true);
        }

        return response;

    }

    public Response<Event> addEvent(Event event) {
        Response response = new Response();

        if (event == null || event.getStartTime() == null || event.getEndTime() == null
                || event.getName().isEmpty() || event.getName() == null
                || event.getCategory() == null || event.getTripId() <= 0) {
            response.setMessage("Invalid event. Please try again");
        } else {
            event = tDao.addEvent(event);
            response.setData(event);
            response.setSuccess(true);
        }

        return response;
    }

    public Response<Event> getEventById(int id) {
        Response response = new Response();

        if (id <= 0) {
            response.setMessage("Invalid event id. Please try again");
        } else {
            try {
                Event event = tDao.getEventById(id);
                response.setData(event);
                response.setSuccess(true);
            } catch (DaoException ex) {
                response.setMessage(ex.getMessage());
            }
        }

        return response;
    }

    public Response<List<Event>> getEventsByDate(LocalDate date, int tripId) {
        Response response = new Response();

        try {
            Trip trip = tDao.getTripById(tripId);

            if (date.isAfter(trip.getEndDate()) || date.isBefore(trip.getStartDate())) {
                response.setMessage("Date given falls outside of the trip dates");
            } else {
                List<Event> events = tDao.getEventsByDate(date, tripId);
                response.setData(events);
                response.setSuccess(true);
            }

        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    public Response<List<Event>> getEventsByWeek(List<LocalDate> dates, int tripId) {
        Response response = new Response();

        try {
            Trip trip = tDao.getTripById(tripId);

            for (LocalDate date : dates) {
                if (date.isAfter(trip.getEndDate()) || date.isBefore(trip.getStartDate())) {
                    response.setMessage("Dates given falls outside of the trip dates");
                    return response;
                }
            }

            List<Event> events = tDao.getEventsByWeek(dates, tripId);
            response.setData(events);
            response.setSuccess(true);

        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }
        return response;

    }

    public Response<List<Event>> getEventsByTrip(int tripId) {
        Response response = new Response();

        List<Event> events = tDao.getEventsByTrip(tripId);
        response.setData(events);
        response.setSuccess(true);

        return response;
    }

    public Response updateEvent(Event event) {
        Response response = new Response();

        if (event == null || event.getStartTime() == null || event.getEndTime() == null
                || event.getName().isEmpty() || event.getName() == null
                || event.getCategory() == null || event.getTripId() <= 0) {
            response.setMessage("Invalid event. Please try again");
        } else {
            tDao.updateEvent(event);
            response.setSuccess(true);
        }

        return response;
    }

    public Response deleteEvent(int id) {

        Response response = new Response();
        try {
            tDao.deleteEvent(id);
            response.setSuccess(true);
        } catch (DaoException ex) {
            response.setMessage(ex.getMessage());
        }

        return response;
    }

    public Response<List<Trip>> getTripsByUser(int userId) {
        Response response = new Response();

        return response;
    }

    public Response<List<DayOfActivity>> getDaysOfActivities(LocalDate date, int tripId) {
        Response response = new Response();
        List<DayOfActivity> days = new ArrayList();

        for (int i = 0; i < 7; i++) {
            DayOfActivity day = new DayOfActivity();
            LocalDate newDay = date.plusDays(i);
            List<Event> events = tDao.getEventsByDate(newDay, tripId);
            events.sort((Event e1, Event e2) -> e1.getStartTime().compareTo(e2.getStartTime()));
            day.setDay(newDay);
            day.setEvents(events);
            days.add(day);
        }

        days.sort((DayOfActivity d1, DayOfActivity d2) -> d1.getDay().compareTo(d2.getDay()));
        response.setSuccess(true);
        response.setData(days);
        
        return response;
    }

}
