/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.daos;

import java.util.List;
import sg.BestGroupProject.models.Role;
import sg.BestGroupProject.models.SiteUser;

/**
 *
 * @author ddubs
 */
public interface UserDao {
    SiteUser getUserById(int id);
    SiteUser getUserByUsername(String username);
//    may be able to delete
    List<SiteUser> getAllUsers();
    void updateUser(SiteUser user);
    void deleteUser(int id);
    SiteUser createUser(SiteUser user, int tripId);
    
    Role getRoleById(int id);
    Role getRoleByRole(String role);
    List<Role> getAllRoles();
    void deleteRole(int id);
    void updateRole(Role role);
    Role createRole(Role role);
}
