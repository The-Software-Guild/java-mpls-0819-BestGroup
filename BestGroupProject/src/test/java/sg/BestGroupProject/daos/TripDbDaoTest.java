/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.daos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import sg.BestGroupProject.TestApplicationConfiguration;
import sg.BestGroupProject.models.Category;
import sg.BestGroupProject.models.Event;
import sg.BestGroupProject.models.Trip;

/**
 *
 * @author ddubs
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
@Profile("daoTest")
public class TripDbDaoTest {
        @Autowired
        private JdbcTemplate jdbc;
    
        @Autowired
        TripDao toTest;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        String deleteEvents = "Delete From Event";
        jdbc.update(deleteEvents);

        String deleteUserTrip = "Delete From UserTrip";
        jdbc.update(deleteUserTrip);
        
        String deleteTrips = "Delete From Trip";
        jdbc.update(deleteTrips);
        
        String deleteUserRole = "Delete From User_Role";
        jdbc.update(deleteUserRole);
        
        String deleteUsers = "Delete From User";
        jdbc.update(deleteUsers);

    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createTrip method, of class TripDbDao.
     */
    @Test
    public void testCreateTrip() throws Exception {
        Trip newTrip = new Trip();
        newTrip.setName("Trip");
        newTrip.setStartDate(LocalDate.of(2019, 10, 18));
        newTrip.setEndDate(LocalDate.of(2019, 10, 19));
        
       int newId = toTest.createTrip(newTrip);
        
      Trip tripToAssert = toTest.getTripById(newId);
      
      assertEquals(newId, tripToAssert.getId());
    }

    /**
     * Test of getTripById method, of class TripDbDao.
     */
    @Test
    public void testGetTripById() throws Exception {
        Trip newTrip = new Trip();
        newTrip.setName("Trip");
        newTrip.setStartDate(LocalDate.of(2019, 10, 18));
        newTrip.setEndDate(LocalDate.of(2019, 10, 19));
        
        int newId = toTest.createTrip(newTrip);
        
        Trip tripToAssert = toTest.getTripById(newId);
      
        assertEquals(newId, tripToAssert.getId());
        assertEquals("Trip", tripToAssert.getName());
        assertEquals("2019-10-18", tripToAssert.getStartDate().toString());
        assertEquals("2019-10-19", tripToAssert.getEndDate().toString());
        
    }

    /**
     * Test of updatedTrip method, of class TripDbDao.
     */
    @Test
    public void testUpdatedTrip() throws DaoException {
        Trip newTrip = new Trip();
        newTrip.setName("Trip");
        newTrip.setStartDate(LocalDate.of(2019, 10, 18));
        newTrip.setEndDate(LocalDate.of(2019, 10, 19));
        
        int newId = toTest.createTrip(newTrip);
    
        Trip updatedTrip = toTest.getTripById(newId);
        updatedTrip.setName("Trip with New Name");
        updatedTrip.setStartDate(LocalDate.of(2019, 10, 30));
        updatedTrip.setEndDate(LocalDate.of(2019, 10, 31));
        
        toTest.updateTrip(updatedTrip);
        
        Trip tripToAssert = toTest.getTripById(newId);
        
        assertEquals("Trip with New Name", tripToAssert.getName());
        assertEquals(LocalDate.of(2019, 10, 30), tripToAssert.getStartDate());
        assertEquals(LocalDate.of(2019, 10, 31), tripToAssert.getEndDate());
       
    }

    /**
     * Test of addEvent method, of class TripDbDao.
     */
    @Test
    public void testAddEvent() throws DaoException {
        
        Trip newTrip = new Trip();
        newTrip.setName("Trip");
        newTrip.setStartDate(LocalDate.of(2019, 10, 18));
        newTrip.setEndDate(LocalDate.of(2019, 10, 31));
        
        int newId = toTest.createTrip(newTrip);
        
        Event toAdd = new Event();
        
        toAdd.setName("Flight");
        toAdd.setLocation("MSP Airport");
        toAdd.setDescription("Flight from MSP to LAX");
        toAdd.setStartTime(LocalDateTime.of(2019, 10, 21, 12, 56, 00));
        toAdd.setEndTime(LocalDateTime.of(2019, 10, 21, 15, 56, 00));
        toAdd.setCategory(Category.transportation);
        toAdd.setTransportationId("abcde12345");
        toAdd.setTripId(newId);
        
        toTest.addEvent(toAdd);
        
    }

    /**
     * Test of getEventById method, of class TripDbDao.
     */
    @Test
    public void testGetEventById() throws Exception {
                Trip newTrip = new Trip();
        newTrip.setName("Trip");
        newTrip.setStartDate(LocalDate.of(2019, 10, 18));
        newTrip.setEndDate(LocalDate.of(2019, 10, 31));
        
        int newId = toTest.createTrip(newTrip);
        
        Event toAdd = new Event();
        
        toAdd.setName("Flight");
        toAdd.setLocation("MSP Airport");
        toAdd.setDescription("Flight from MSP to LAX");
        toAdd.setStartTime(LocalDateTime.of(2019, 10, 21, 12, 56, 00));
        toAdd.setEndTime(LocalDateTime.of(2019, 10, 21, 15, 56, 00));
        toAdd.setCategory(Category.transportation);
        toAdd.setTransportationId("abcde12345");
        toAdd.setTripId(newId);
        
        Event eventToAdd = toTest.addEvent(toAdd);
        
        Event eventToAssert = toTest.getEventById(eventToAdd.getId());
        
        assertEquals("Flight", eventToAssert.getName());
        assertEquals("MSP Airport", eventToAssert.getLocation());
        assertEquals("Flight from MSP to LAX", eventToAssert.getDescription());
        assertEquals(LocalDateTime.of(2019, 10, 21, 12, 56, 00), eventToAssert.getStartTime());
        assertEquals(LocalDateTime.of(2019, 10, 21, 15, 56, 00), eventToAssert.getEndTime());
        assertEquals(Category.transportation, eventToAssert.getCategory());
        assertEquals("abcde12345", eventToAssert.getTransportationId());
 
    }

    /**
     * Test of getEventsByTrip method, of class TripDbDao.
     */
    @Test
    public void testGetEventsByTrip() throws DaoException {
        Trip newTrip = new Trip();
        newTrip.setName("Trip");
        newTrip.setStartDate(LocalDate.of(2019, 10, 18));
        newTrip.setEndDate(LocalDate.of(2019, 10, 31));
        
        int newId = toTest.createTrip(newTrip);
        
        Event toAdd = new Event();
        
        toAdd.setName("Flight");
        toAdd.setLocation("MSP Airport");
        toAdd.setDescription("Flight from MSP to LAX");
        toAdd.setStartTime(LocalDateTime.of(2019, 10, 21, 12, 56, 00));
        toAdd.setEndTime(LocalDateTime.of(2019, 10, 21, 15, 56, 00));
        toAdd.setCategory(Category.transportation);
        toAdd.setTransportationId("abcde12345");
        toAdd.setTripId(newId);
        
        Event eventToAdd = toTest.addEvent(toAdd);
        
        Event toAdd2 = new Event();
        
        toAdd2.setName("Train");
        toAdd2.setLocation("St.Paul Amtrak");
        toAdd2.setDescription("Train ride from St.Paul to Portland");
        toAdd2.setStartTime(LocalDateTime.of(2019, 10, 21, 12, 56, 00));
        toAdd2.setEndTime(LocalDateTime.of(2019, 10, 21, 15, 56, 00));
        toAdd2.setCategory(Category.transportation);
        toAdd2.setTransportationId("abcde12345");
        toAdd2.setTripId(newId);
        
        Event eventToAdd2 = toTest.addEvent(toAdd2);
        
        List<Event> tripEvents = toTest.getEventsByTrip(newId);
        
        assertEquals(2, tripEvents.size());
    }

    /**
     * Test of updateEvent method, of class TripDbDao.
     */
    @Test
    public void testUpdateEvent() throws DaoException {
        Trip newTrip = new Trip();
        newTrip.setName("Trip");
        newTrip.setStartDate(LocalDate.of(2019, 10, 18));
        newTrip.setEndDate(LocalDate.of(2019, 10, 31));
        
        int newId = toTest.createTrip(newTrip);
        
        Event toAdd = new Event();
        
        toAdd.setName("Train");
        toAdd.setLocation("MSP Airport");
        toAdd.setDescription("Flight from MSP to LAX");
        toAdd.setStartTime(LocalDateTime.of(2019, 10, 21, 12, 56, 00));
        toAdd.setEndTime(LocalDateTime.of(2019, 10, 21, 15, 56, 00));
        toAdd.setCategory(Category.transportation);
        toAdd.setTransportationId("abcde12345");
        toAdd.setTripId(newId);
        
        Event eventToAdd = toTest.addEvent(toAdd);
        
        int id = eventToAdd.getId();
        
        Event updatedEvent = toTest.getEventById(id);
        
        updatedEvent.setName("Train");
        updatedEvent.setLocation("St. Paul Amtrak");
        updatedEvent.setDescription("Train ride from St.Paul to Portland");
        updatedEvent.setStartTime(LocalDateTime.of(2019, 10, 31, 12, 56, 00));
        updatedEvent.setEndTime(LocalDateTime.of(2019, 10, 31, 15, 56, 00));
        updatedEvent.setCategory(Category.transportation);
        updatedEvent.setTransportationId("1234567890");
        updatedEvent.setTripId(newId);
        
        toTest.updateEvent(updatedEvent);
        
        Event eventToAssert = toTest.getEventById(id);
        
        assertEquals("Train", eventToAssert.getName());
        assertEquals("St. Paul Amtrak", eventToAssert.getLocation());
        assertEquals("Train ride from St.Paul to Portland", eventToAssert.getDescription());
        assertEquals(LocalDateTime.of(2019, 10, 31, 12, 56, 00), eventToAssert.getStartTime());
        assertEquals(LocalDateTime.of(2019, 10, 31, 15, 56, 00), eventToAssert.getEndTime());
        assertEquals(Category.transportation, eventToAssert.getCategory());
        assertEquals("1234567890", eventToAssert.getTransportationId());
    }

    /**
     * Test of deleteEvent method, of class TripDbDao.
     */
    @Test
    public void testDeleteEvent() throws DaoException {
        Trip newTrip = new Trip();
        newTrip.setName("Trip");
        newTrip.setStartDate(LocalDate.of(2019, 10, 18));
        newTrip.setEndDate(LocalDate.of(2019, 10, 31));
        
        int newId = toTest.createTrip(newTrip);
        
        Event toAdd = new Event();
        
        toAdd.setName("Flight");
        toAdd.setLocation("MSP Airport");
        toAdd.setDescription("Flight from MSP to LAX");
        toAdd.setStartTime(LocalDateTime.of(2019, 10, 21, 12, 56, 00));
        toAdd.setEndTime(LocalDateTime.of(2019, 10, 21, 15, 56, 00));
        toAdd.setCategory(Category.transportation);
        toAdd.setTransportationId("abcde12345");
        toAdd.setTripId(newId);

        Event addedEvent = toTest.addEvent(toAdd);
        
        assertEquals(1, toTest.getEventsByTrip(newId).size());
        
        toTest.deleteEvent(addedEvent.getId());
        
        assertEquals(0, toTest.getEventsByTrip(newId).size());
  
    }

    /**
     * Test of getEventsByDate method, of class TripDbDao.
     */
    @Test
    public void testGetEventsByDate() throws DaoException {
        Trip newTrip = new Trip();
        newTrip.setName("Trip");
        newTrip.setStartDate(LocalDate.of(2019, 10, 18));
        newTrip.setEndDate(LocalDate.of(2019, 10, 31));
        
        int newId = toTest.createTrip(newTrip);
        
        Event toAdd = new Event();
        
        toAdd.setName("Flight");
        toAdd.setLocation("MSP Airport");
        toAdd.setDescription("Flight from MSP to LAX");
        toAdd.setStartTime(LocalDateTime.of(2019, 10, 21, 12, 56, 00));
        toAdd.setEndTime(LocalDateTime.of(2019, 10, 21, 15, 56, 00));
        toAdd.setCategory(Category.transportation);
        toAdd.setTransportationId("abcde12345");
        toAdd.setTripId(newId);

        Event addedEvent = toTest.addEvent(toAdd);
        
        List<Event> eventsByDate = toTest.getEventsByDate(LocalDate.of(2019, 10, 21), newId);
        
        assertEquals(1, eventsByDate.size());
        
    }

    /**
     * Test of getEventsByWeek method, of class TripDbDao.
     */
    @Test
    public void testGetEventsByWeek() {
    }
    
}
