/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.Controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sg.BestGroupProject.daos.TripDao;
import sg.BestGroupProject.daos.UserDao;
import sg.BestGroupProject.models.Event;
import sg.BestGroupProject.models.Trip;
import sg.BestGroupProject.services.Response;
import sg.BestGroupProject.services.TripService;

/**
 *
 * @author ddubs
 */
@Controller
public class ContentController {
    @Autowired
    TripService tripService;
    

    

    @GetMapping("/content")
    public String displayContentPage(Integer day, Integer tripId, Model model) {
      List<Event> events = new ArrayList<>();
      Response<Trip> response = tripService.getTripById(tripId);
      response.getData();
//       put the trip in model
        
//       do check to see if day is not null
      model.addAttribute("tripDetails",response);
      model.addAttribute("eventsList", events);
    
        
        return "content";
    }
    
    
//     @PostMapping("signUp")
//    public String signUpForm(HttpServletRequest request) {
//        username = request.getParameter("formFirstName");
//         = Integer.parseInt(request.getParameter(""));
//        
//        return "redirect:/singnUp";
//    }
}
