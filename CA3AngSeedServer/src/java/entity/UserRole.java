/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ichti
 */
@Entity
@Table(name = "UserRole")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String role;

    public UserRole() {
    }
    
    public UserRole(String role) {
        this.role = role;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
}
