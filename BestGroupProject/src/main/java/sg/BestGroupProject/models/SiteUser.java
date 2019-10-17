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
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.username);
        hash = 89 * hash + Objects.hashCode(this.password);
        hash = 89 * hash + (this.enabled ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.roles);
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
}
