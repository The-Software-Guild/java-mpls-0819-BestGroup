/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sg.BestGroupProject.models.Role;
import sg.BestGroupProject.models.SiteUser;

/**
 *
 * @author ddubs
 */
@Component
public class UserDbDao implements UserDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public SiteUser getUserById(int id) {
        String select = "SELECT id, username, password, enabled, firstname, lastname, schoolname,"
                + " emergencycontactname, emergencycontactphone FROM user where id = ?";

        SiteUser toReturn = jdbc.queryForObject(select, new UserMapper(), id);

        Set<Role> userRoles = getRolesForUser(id);

        toReturn.setRoles(userRoles);

        return toReturn;
    }

    @Override
    public SiteUser getUserByUsername(String username) {
        String select = "SELECT id, username, password, enabled, firstname, lastname, schoolname,"
                + " emergencycontactname, emergencycontactphone FROM user where username = ?";

        SiteUser toReturn = jdbc.queryForObject(select, new UserMapper(), username);

        Set<Role> userRoles = getRolesForUser(toReturn.getId());

        toReturn.setRoles(userRoles);

        return toReturn;
    }

    @Override
    public List<SiteUser> getAllUsers() {
        String select = "SELECT id, username, password, enabled, firstname, lastname, schoolname,"
                + " emergencycontactname, emergencycontactphone FROM user";

        List<SiteUser> allUsers = jdbc.query(select, new UserMapper());

        for (SiteUser toFinish : allUsers) {
            Set<Role> userRoles = this.getRolesForUser(toFinish.getId());
            toFinish.setRoles(userRoles);
        }

        return allUsers;
    }

    @Override
    public void updateUser(SiteUser user) {

        String updateStatement = "Update user SET username = ?, password = ?, enabled = ?, firstname = ?,"
                + " lastname = ?, schoolname = ?, emergencycontactname = ?, emergencycontactphone = ?, WHERE id = ?";

        int updateRowsAffected = jdbc.update(updateStatement, user.getUsername(),
                user.getPassword(), user.isEnabled(), user.getFirstName(), user.getLastName(), 
                user.getSchoolName(), user.getEmergencyContactName(), user.getEmergencyContactPhone(), user.getId());

        //TODO: CHECK THAT ROWS AFFECTED =1
        String deleteStatement = "DELETE FROM user_role WHERE user_id = ?";

        int deleteRowsAffected = jdbc.update(deleteStatement, user.getId());

        String insert = "INSERT INTO user_role (user_id, role_id) VALUES (?, ?)";

        for (Role toAdd : user.getRoles()) {

            int insertRowsAffected = jdbc.update(insert, user.getId(), toAdd.getId());

            //TODO: CHECK THAT ROWS AFFECTED = 1
        }
    }

    @Override
    public void deleteUser(int id) {
        String roleDelete = "DELETE FROM user_role where user_id = ?";

        int roleRowsAffected = jdbc.update(roleDelete, id);

        //TODO: make sure rows affected = 1
        String userDelete = "DELETE FROM user WHERE id = ?";

        int deleteRowsAffected = jdbc.update(userDelete, id);

    }

    @Transactional
    @Override
    public SiteUser createUser(SiteUser user, int tripId) {
        String insertStatement = "INSERT INTO user (username, password, enabled, firstname, lastname, schoolname, "
                + "emergencycontactname, emergencycontactphone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        int insertRows = jdbc.update(insertStatement, user.getUsername(), user.getPassword(), user.isEnabled(),
                user.getFirstName(), user.getLastName(), user.getSchoolName(), 
                user.getEmergencyContactName(), user.getEmergencyContactPhone());

        int newId = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
        user.setId(newId);

        String roleInsert = "INSERT INTO user_role (user_id, role_id) VALUES (?, ?)";

        for (Role toAdd : user.getRoles()) {
            int roleRowsAffected = jdbc.update(roleInsert, newId, toAdd.getId());
            //TODO: Make sure 1 row was affected
        }
        
        String userTripInsert = "INSERT INTO usertrip (UserId, TripId) VALUES (?, ?)";
        
        jdbc.update(userTripInsert, user.getId(), tripId);

        return user;
    }

    private Set<Role> getRolesForUser(int id) {

        String selectStatement = "SELECT id, `role`\n"
                                + "FROM `role`\n"
                                + "INNER JOIN user_role ON id = role_id\n"
                                + "WHERE user_id = ?";
        
        Set<Role> roles = new HashSet<Role>(jdbc.query(selectStatement, new RoleMapper(), id));
        
        return roles;
    }

    @Override
    public Role getRoleById(int id) {
        String select = "SELECT id, role FROM role where id=?";

        Role toReturn = jdbc.queryForObject(select, new RoleMapper(), id);

        return toReturn;    
    }

    @Override
    public Role getRoleByRole(String role) {
        String select = "SELECT id, role FROM role where role = ?";

        Role toReturn = jdbc.queryForObject(select, new RoleMapper(), role);

        return toReturn;    
    }

    @Override
    public List<Role> getAllRoles() {
        String select = "SELECT id, role FROM role";

        List<Role> allRoles = jdbc.query(select, new RoleMapper());

        return allRoles;    
    }

    @Override
    public void deleteRole(int id) {
        String userDelete = "DELETE FROM user_role where role_id = ?";

        int userRowsAffected = jdbc.update(userDelete, id);

        //TODO: make sure rows affected = 1
        String roleDelete = "DELETE FROM role WHERE id = ?";

        int deleteRowsAffected = jdbc.update(roleDelete, id);

    }

    @Override
    public void updateRole(Role role) {

        String updateStatement = "Update role SET role = ? WHERE id = ?";

        int updateRowsAffected = jdbc.update(updateStatement, role.getRole(), role.getId());

        //TODO: CHECK THAT ROWS AFFECTED =1    
    }
    
    @Override
    public Role createRole(Role role) {
     String insertStatement = "INSERT INTO role (role) VALUES (?)";

        int insertRows = jdbc.update(insertStatement, role.getRole());

        int newId = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
        role.setId(newId);

        return role;    
    }

    
    public static class UserMapper implements RowMapper<SiteUser> {

        @Override
        public SiteUser mapRow(ResultSet results, int rowNum) throws SQLException {
            SiteUser toReturn = new SiteUser();
            
            toReturn.setId(results.getInt("id"));
            toReturn.setPassword(results.getString("password"));
            toReturn.setEnabled(results.getBoolean("enabled"));
            toReturn.setUsername(results.getString("username"));
            toReturn.setFirstName(results.getString("firstname"));
            toReturn.setLastName(results.getString("lastname"));
            toReturn.setSchoolName(results.getString("schoolname"));
            toReturn.setEmergencyContactName(results.getString("emergencycontactname"));
            toReturn.setEmergencyContactPhone(results.getInt("emergencycontactphone"));
            
            return toReturn;

        }

    }
    
    public static class RoleMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet results, int rowNum) throws SQLException {
            Role toReturn = new Role();
            
            toReturn.setId(results.getInt("id"));
            toReturn.setRole(results.getString("role"));
            
            return toReturn;
        }   
    }

}
