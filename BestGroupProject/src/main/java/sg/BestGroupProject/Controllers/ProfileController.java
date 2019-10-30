/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.Controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import sg.BestGroupProject.daos.UserDao;
import sg.BestGroupProject.models.Role;
import sg.BestGroupProject.models.SiteUser;
import sg.BestGroupProject.models.Trip;
import sg.BestGroupProject.services.Response;
import sg.BestGroupProject.services.TripService;

/**
 *
 * @author ddubs
 */
@Controller
public class ProfileController {

    @Autowired
    UserDao users;

    @Autowired
    TripService trip;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/profile")
    public String displayProfilePage(Model model) {
        String userName = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        SiteUser user = users.getUserByUsername(userName);
        Response<List<Trip>> tResponse = trip.getTripsByUser(user.getId());
        List<Trip> trips = tResponse.getData();
        model.addAttribute("trips", trips);

        return "profile";
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("users", users.getAllUsers());

        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(String username, String password, Integer tripId) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEnabled(true);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(users.getRoleByRole("ROLE_USER"));
        user.setRoles(userRoles);

        users.createUser(user, tripId);

        return "redirect:/profile";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(Integer id) {
        users.deleteUser(id);
        return "redirect:/profile";
    }

    @PostMapping(value = "/editUser")
    public String editUserAction(String[] roleIdList, Boolean enabled, Integer id, Integer tripId) {
        SiteUser user = users.getUserById(id);
        if (enabled != null) {
            user.setEnabled(enabled);
        } else {
            user.setEnabled(false);
        }

        Set<Role> roleList = new HashSet<>();
        for (String roleId : roleIdList) {
            Role role = users.getRoleById(Integer.parseInt(roleId));
            roleList.add(role);
        }
        user.setRoles(roleList);
        users.updateUser(user, tripId.intValue());

        return "redirect:/profile";
    }

    @PostMapping("editPassword")
    public String editPassword(Integer id, String password, String confirmPassword) {
        SiteUser user = users.getUserById(id);

        if (password.equals(confirmPassword)) {
            user.setPassword(encoder.encode(password));
            users.updateUserPassword(user);
            return "redirect:/profile";
        } else {
            return "redirect:/editUser?id=" + id + "&error=1";
        }
    }

    @GetMapping("/editUser")
    public String editUserDisplay(Model model, Integer id, Integer error) {
        SiteUser user = users.getUserById(id);
        List roleList = users.getAllRoles();

        model.addAttribute("user", user);
        model.addAttribute("roles", roleList);

        if (error != null) {
            if (error == 1) {
                model.addAttribute("error", "Passwords did not match, password was not updated.");
            }
        }

        return "editUser";
    }

}
