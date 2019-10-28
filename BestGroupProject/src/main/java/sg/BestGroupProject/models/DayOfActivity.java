/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.models;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author cas
 */
public class DayOfActivity {
    private List<Event> events;
    private LocalDate day;
    private int tripId;

    /**
     * @return the events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * @return the day
     */
    public LocalDate getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(LocalDate day) {
        this.day = day;
    }

    /**
     * @return the tripId
     */
    public int getTripId() {
        return tripId;
    }

    /**
     * @param tripId the tripId to set
     */
    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
    
  
}
