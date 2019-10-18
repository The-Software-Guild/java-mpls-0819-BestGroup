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
public class Trip {
    private int id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<SiteUser> teachers;
    private List<SiteUser> students;
    private List<Event> events;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the teachers
     */
    public List<SiteUser> getTeachers() {
        return teachers;
    }

    /**
     * @param teachers the teachers to set
     */
    public void setTeachers(List<SiteUser> teachers) {
        this.teachers = teachers;
    }

    /**
     * @return the students
     */
    public List<SiteUser> getStudents() {
        return students;
    }

    /**
     * @param students the students to set
     */
    public void setStudents(List<SiteUser> students) {
        this.students = students;
    }

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
    
}
