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
@Table(name = "Currency")
public class Currency implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String code;
    private String name;
    
    
    public Currency() {
        
    }

    public Currency(String currency) {
        this.code = currency;
        this.name = "Unknown currency";
    }
    
    public Currency(String currency, String desc) {
        this.code = currency;
        this.name = desc;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
