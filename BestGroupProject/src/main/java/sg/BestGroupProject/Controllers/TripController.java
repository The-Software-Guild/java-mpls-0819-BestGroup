/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.Controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sg.BestGroupProject.models.Category;
import sg.BestGroupProject.models.DayOfActivity;
import sg.BestGroupProject.models.Event;
import sg.BestGroupProject.models.Trip;
import sg.BestGroupProject.services.Response;
import sg.BestGroupProject.services.TripService;

/**
 *
 * @author ddubs
 */
@Controller
public class TripController {

    @Autowired
    TripService tripService;

    @GetMapping("/tripHome/{tripId}")
    public String displayContentPage(@PathVariable Integer tripId, Model model) {
        
        Response<Trip> trip = tripService.getTripById(tripId);
        LocalDate day = trip.getData().getStartDate();
        TemporalField field = WeekFields.of(Locale.getDefault()).dayOfWeek();
        LocalDate date = day.with(field, 1);

        Response<List<DayOfActivity>> days = tripService.getDaysOfActivities(date, tripId);

//        List<Event> events = new ArrayList<>();
//        Response<Trip> response = tripService.getTripById(tripId);
//        response.getData();
//       put the trip in model
//       do check to see if day is not null
//        model.addAttribute("tripDetails", response);
//        model.addAttribute("eventsList", events);
        model.addAttribute("daysList", days.getData());

        return "tripHome";
    }
    
    @GetMapping("/showEvent/{eventId}")
    public String displayEvent(@PathVariable Integer eventId, Model model){

        Response<Event> response = tripService.getEventById(eventId);

        model.addAttribute("event", response.getData());
        return "showEvent";
    }

//     @PostMapping("signUp")
//    public String signUpForm(HttpServletRequest request) {
//        username = request.getParameter("formFirstName");
//         = Integer.parseInt(request.getParameter(""));
//        
//        return "redirect:/singnUp";
//    }
    @GetMapping("/addEvent/{tripId}")
    public String displayAddEvent(@PathVariable Integer tripId, Model model) {
        EnumSet<Category> categories = EnumSet.allOf(Category.class);

        model.addAttribute("categories", categories);
        model.addAttribute("tripId", tripId);

        return "addEvent";
    }

    @PostMapping("/addEvent")
    public String addEvent(Event toAdd) {
//        String name = request.getParameter("name");
//        String location = request.getParameter("location");
//        String startTime = request.getParameter("starttime");
//        String endTime = request.getParameter("endtime");
//        String description = request.getParameter("description");
//        String categoryId = request.getParameter("categoryId");
//        String transportationId = request.getParameter("TransportationId");
//
//        Event event = new Event();
//        event.setName(name);
//        event.setLocation(location);
//        event.setDescription(description);
//        event.setStartTime(LocalDateTime.parse(startTime));
//        event.setEndTime(LocalDateTime.parse(endTime));
//        event.setCategory(Category.valueOf(categoryId));
//        event.setTransportationId(transportationId);
        
        tripService.addEvent(toAdd);
        

        return "redirect:/";
    }

}
