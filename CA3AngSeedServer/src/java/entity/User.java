/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;

/**
 *
 * @author ichti
 */
@Entity
@Table(name = "User")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String userName;
    private String password; //todo not plaintext
    @OneToMany(cascade = CascadeType.PERSIST)
    List<UserRole> roles;

    public User() {
        
    }
    public User(String userName, String password) {
        this.roles = new ArrayList();
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password,List<UserRole> roles) {
        this.roles = new ArrayList();
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }    
    
    public String getUserName() {
        return userName;
    }
    
    public void AddRole(UserRole role){
        //UserRole newRole = new UserRole(role);
        roles.add(role);
    }

    public List<String> getRoles() {
        List<String> out = new ArrayList<>();
        for(Iterator<UserRole> i = roles.iterator(); i.hasNext(); ) {
            out.add(i.next().getRole());
        }
        return out;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }
}
