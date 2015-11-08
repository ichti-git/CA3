/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import deploy.DeploymentConfiguration;
import entity.Currency;
import entity.ExchangeRate;
import facades.CurrencyFacade;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import serv.XmlReader;

/**
 *
 * @author ichti
 */
public class CurrencyFacadeTest {
    private CurrencyFacade currencyFacade = new CurrencyFacade("CA3TestPU");
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA3TestPU");
    public CurrencyFacadeTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    @Before
    public void setUp() {
        DeploymentConfiguration.PU_NAME = "CA3TestPU";
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from ExchangeRate").executeUpdate();
            em.createQuery("delete from Currency").executeUpdate();
            Currency c1 = new Currency("DKK", "Danske Kroner");
            Currency c2 = new Currency("FM", "Falske Mønter");
            em.persist(c1);
            em.persist(c2);
            em.getTransaction().commit();

        } 
        finally {
            em.close();
        }

    }
    
    @After
    public void tearDown() {
    }

    //public void addCurrency(String code, String name);
    @Test
    public void testAddCurrency() {
        currencyFacade.addCurrency("NEW", "New currency");
        EntityManager em = emf.createEntityManager();
        Currency c = em.find(Currency.class, "NEW");
        assertEquals("NEW", c.getCode());
        assertEquals("New currency", c.getName());
    }
    
    //TODO
    //public Double getExchangeRate(String currencycode);
    //public Double getExchangeRate(String currencycode, Date date);
    
    //public void addExchangeRate(String currency, Date date, Double rate);
    @Test 
    public void testAddExchangeRate() {
        String c = "DKK";
        Date date = new Date();
        Double rate = 4.5;
        currencyFacade.addExchangeRate(c, date, rate);
        Double rt = currencyFacade.getExchangeRate("DKK");
        assertThat(rt, is(4.5));
    }
    
    @Test 
    public void testAddExchangeRateNewCurrency() {
        String c = "NEW";
        Date date = new Date();
        Double rate = 4.5;
        currencyFacade.addExchangeRate(c, date, rate);
        Double rt = currencyFacade.getExchangeRate("NEW");
        assertThat(rt, is(4.5));
    }
    
    @Test 
    public void testGetExchangeRateNotExisting() {
        Double rt = currencyFacade.getExchangeRate("NON");
        assertThat(rt, is(0.0));
    }
    
    //public List<ExchangeRate> getExchangeRates();
    @Test
    public void testGetExchangeRates() {
        //TODO
    }
    
    //public void updateExchangeRates();
    @Test
    public void testUpdateExchangeRates() {
        //TODO
    }
    
}
