/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author ichti
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "ExchangeRate.findAll", query= "SELECT er FROM ExchangeRate er"),
    @NamedQuery(name = "ExchangeRate.findAllByDate", query= "SELECT er FROM ExchangeRate er WHERE er.date = :date"),
    @NamedQuery(name = "ExchangeRate.findByPk", query = "SELECT er FROM ExchangeRate er WHERE er.currency = :currency AND er.date = :date")})
@Table(name = "ExchangeRate")
public class ExchangeRate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @ManyToOne
    private Currency currency;
    @Id 
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    private Double rate;

    public ExchangeRate() {
        
    }
    public ExchangeRate(Currency currency, Date date, Double rate) {
        this.currency = currency;
        this.date = date;
        this.rate = rate;
    }
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

}