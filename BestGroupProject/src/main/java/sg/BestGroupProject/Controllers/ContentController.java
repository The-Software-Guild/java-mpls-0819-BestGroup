/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.Controllers;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sg.BestGroupProject.models.Category;

/**
 *
 * @author ddubs
 */
@Controller
public class ContentController {

//    @GetMapping("/content")
//    public String displayContentPage(Integer day, Integer tripId, Model model) {
//      List<Event> events = new ArrayList<>();
//      call getTripById 
//       put the trip in model
//        getEventsByDate();
//       do check to see if day is not null
//    
//      model.addAttribute("eventsList" events);
//    
//        
//        return "content";
//    }
    @GetMapping("/addEvent")
    public String retrieveCategories(Model model){
        EnumSet<Category> categories = EnumSet.allOf(Category.class);

        List<Category> categoriesList = new ArrayList<>(categories);
        model.addAttribute("categories", categories);
        
        return "categories";
    }
    
}
