/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.BestGroupProject.models;

import java.util.Objects;
import java.util.Set;

/**
 *
 * @author ddubs
 */
public class SiteUser {

    private int id;
    private String username;
    private String password;
    private boolean enabled;
    private Set<Role> roles;
    private String firstName;
    private String lastName;
    private String schoolName;
    private String emergencyContactName;
    private int emergencyContactPhone;
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.getId();
        hash = 89 * hash + Objects.hashCode(this.getUsername());
        hash = 89 * hash + Objects.hashCode(this.getPassword());
        hash = 89 * hash + (this.isEnabled() ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.getRoles());
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(other == null) return false;
        if(this.getClass() != other.getClass()) return false;
        
        final SiteUser that = (SiteUser)other;
        
        if(this.getId() != that.getId()) return false;
        if(!this.getPassword().equals(that.getPassword())) return false;
        if(!this.getUsername().equals(that.getUsername())) return false;
        
        return Objects.equals(this.getRoles(), that.getRoles());
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the schoolName
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * @param schoolName the schoolName to set
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * @return the emergencyContactName
     */
    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    /**
     * @param emergencyContactName the emergencyContactName to set
     */
    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    /**
     * @return the emergencyContactPhone
     */
    public int getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    /**
     * @param emergencyContactPhone the emergencyContactPhone to set
     */
    public void setEmergencyContactPhone(int emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

}
