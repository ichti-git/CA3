/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Currency;
import entity.ExchangeRate;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author ichti
 */
public class CurrencyFacade implements CurrencyFacadeInterface {
    private static EntityManagerFactory emf;
    private static List<ExchangeRate> exchangeRates;

    public CurrencyFacade() {
        emf = Persistence.createEntityManagerFactory(deploy.DeploymentConfiguration.PU_NAME);
    }
    
    public CurrencyFacade(String pu) {
        emf = Persistence.createEntityManagerFactory(pu);
    }
    
    
    @Override
    public void addExchangeRate(String currency, Date date, Double rate) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Currency c = em.find(Currency.class, currency);
            if (c == null) {
                c = new Currency(currency);
                em.persist(c);
            }
            Query q = em.createNamedQuery("ExchangeRate.findByPk").setParameter("currency", c).setParameter("date", date);
            if (q.getResultList().isEmpty()) {
                ExchangeRate newExchangeRate= new ExchangeRate(c, date, rate);
                em.persist(newExchangeRate);
                em.getTransaction().commit();
            }
            else {
                //Just ignore duplicate keys here
                System.out.println("Tried to insert duplicate key in exchange rate: " + currency + ", " + date);
            }
        }
        finally {
            em.close();
        }
    }
    
    @Override
    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }
    
    @Override
    public void updateExchangeRates(Date date) {
        EntityManager em = emf.createEntityManager();
        //Date date = new Date();
        Query q = em.createNamedQuery("ExchangeRate.findAllByDate").setParameter("date", date);
        if (!q.getResultList().isEmpty()) {
            exchangeRates = q.getResultList();
        }
        else {
            date = Yesterday(date);
            q.setParameter("date", date);
            exchangeRates = q.getResultList();
        }
        em.close();
    }
    
    @Override
    public Double getExchangeRate(String currencycode) {
        Date date = new Date();
        return getExchangeRate(currencycode, date);
    }
    
    // Returns 0.0 if currency codes not exist. Should probably be en error
    @Override
    public Double getExchangeRate(String currencycode, Date date) {
        EntityManager em = emf.createEntityManager();
        Double rate = 0.0;
        try {
            Currency c = em.find(Currency.class, currencycode);
            if (c != null) {
                Query q = em.createNamedQuery("ExchangeRate.findByPk").setParameter("currency", c).setParameter("date", date);
                q.setParameter("currency", c);
                q.setParameter("date", date);
                //if there is no result, it is probably because there is no rate for the current day
                //subtract a day and try again. If still not found, throw an error/return 0.0.
                if (!q.getResultList().isEmpty()) {
                    rate = ((ExchangeRate)q.getSingleResult()).getRate();
                }
                else {
                    date = Yesterday(date);
                    q.setParameter("date", date);
                    if (!q.getResultList().isEmpty()) {
                        rate = ((ExchangeRate)q.getSingleResult()).getRate();
                    }
                }
            }
        }
        finally {
            em.close();
        }
        return rate;
    }
    
    @Override
    public void addCurrency(String code, String name) {
        EntityManager em = emf.createEntityManager();
        Currency c = em.find(Currency.class, code);
        if (c == null) {
            c = new Currency();
            c.setCode(code);
            c.setName(name);
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
        }
        else {
            c.setName(name);
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
        }
        em.close();
    }
    
    //Subtracts one day from the given date
    private Date Yesterday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        date = cal.getTime();
        return date;
    }
}
