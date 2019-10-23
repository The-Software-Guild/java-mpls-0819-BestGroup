/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.Controllers;

import java.util.EnumSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sg.BestGroupProject.models.Category;

/**
 *
 * @author ddubs
 */
@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String displayHomePage() {

        return "home";
    }

    @GetMapping("/addEvent")
    public String displayAddEvent(Model model) {
        EnumSet<Category> categories = EnumSet.allOf(Category.class);

        model.addAttribute("categories", categories);

        return "addEvent";
    }

}
