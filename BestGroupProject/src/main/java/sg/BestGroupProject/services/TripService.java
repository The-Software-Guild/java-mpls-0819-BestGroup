/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sg.BestGroupProject.daos.TripDao;
import sg.BestGroupProject.models.Event;
import sg.BestGroupProject.models.Trip;

/**
 *
 * @author Tamara
 */
@Component
public class TripService {

    @Autowired
    TripDao dao;

    public Response<Integer> createTrip(Trip trip) {
        Response response = new Response();

        if (trip == null || trip.getName().isEmpty()) {
            response.setMessage("Trip must have a name to be created.");
        } else {
            int id = dao.createTrip(trip);

            response.setData(id);
            response.setSuccess(true);
        }
        return response;
    }
    
    public Response<Trip> getTripById(int id){
        Response response = new Response();
        
        if (id <= 0){
            response.setMessage("Invalid id");
        } else {
            Trip trip = dao.getTripById(id);
            response.setData(trip);
            response.setSuccess(true);
            
        }
        
        return response;
    } 
    
    public Response updateTrip(Trip trip){
        Response response = new Response();
        
        if (trip == null){
            response.setMessage("Invalid trip");
        } else if (trip.getStartDate() == null){
            response.setMessage("Start date needed");
        } else if (trip.getEndDate() == null){
            response.setMessage("End date needed");
        } else if (trip.getName() == null){
            response.setMessage("Trip name needed");
        } else if (trip.getTeachers().isEmpty()){
            response.setMessage("Please add a teacher to the trip");
        } else {
            dao.updatedTrip(trip);
            response.setSuccess(true);
        }
        
        return response;
 
    }
    
    public Response<Event> addEvent(Event event){
        Response response = new Response();
        
        if (event ==  null || event.getStartTime() == null || event.getEndTime() == null
                || event.getName().isEmpty() || event.getName() == null){
            response.setMessage("Invalid event. Please try again");
        } else {
            event = dao.addEvent(event);
            response.setData(event);
            response.setSuccess(true);
        }
        
        return response;
    }
    
    

}
