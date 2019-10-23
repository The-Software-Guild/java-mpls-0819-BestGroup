/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.Controllers;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/tripHome")
    public String displayContentPage(LocalDate day, Integer tripId, Model model) {
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
    
    @GetMapping("/showEvent")
    public String displayEvent(Integer eventId, Model model){
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
}
