/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.daos;

import java.time.LocalDate;
import java.time.Month;
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
        String deleteTrips = "Delete From Trip";
        jdbc.update(deleteTrips);
        
        String deleteUserTrip = "Delete From UserTrip";
        jdbc.update(deleteUserTrip);
        
        String deleteUserRole = "Delete From User_Role";
        jdbc.update(deleteUserRole);
        
        String deleteUsers = "Delete From User";
        jdbc.update(deleteUsers);
        
        String deleteEvents = "Delete From Event";
        jdbc.update(deleteEvents);
        
        
        
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
    public void testUpdatedTrip() {
    }

    /**
     * Test of addEvent method, of class TripDbDao.
     */
    @Test
    public void testAddEvent() {
    }

    /**
     * Test of getEventById method, of class TripDbDao.
     */
    @Test
    public void testGetEventById() throws Exception {
    }

    /**
     * Test of getEventsByTrip method, of class TripDbDao.
     */
    @Test
    public void testGetEventsByTrip() {
    }

    /**
     * Test of updateEvent method, of class TripDbDao.
     */
    @Test
    public void testUpdateEvent() {
    }

    /**
     * Test of deleteEvent method, of class TripDbDao.
     */
    @Test
    public void testDeleteEvent() {
    }

    /**
     * Test of getEventsByDate method, of class TripDbDao.
     */
    @Test
    public void testGetEventsByDate() {
    }

    /**
     * Test of getEventsByWeek method, of class TripDbDao.
     */
    @Test
    public void testGetEventsByWeek() {
    }
    
}
