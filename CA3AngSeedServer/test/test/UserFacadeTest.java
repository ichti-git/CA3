/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import deploy.DeploymentConfiguration;
import entity.User;
import facades.UserFacade;
import java.util.Arrays;
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

/**
 *
 * @author ichti
 */
public class UserFacadeTest {
    private UserFacade userFacade = new UserFacade("CA3TestPU");

    public UserFacadeTest() {
    }
    
    @Before
    public void setUp() {
        DeploymentConfiguration.PU_NAME = "CA3TestPU";
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA3TestPU");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from User").executeUpdate();
//            em.createQuery("delete from User_UserRole").executeUpdate();
//            em.createQuery("delete from UserRole").executeUpdate();
            em.getTransaction().commit();

        } 
        finally {
            em.close();
        }
        userFacade.makeUsers();
    }
    
    @After
    public void tearDown() {
    }

    //Tests for UserFacade
    @Test 
    public void testGetUserByUserId() {
        User u = userFacade.getUserByUserId("admin");
        assertEquals("admin", u.getUserName());
        assertEquals("test", u.getPassword());
        assertEquals("Admin", u.getRoles().get(0));
    }
    
    @Test
    public void testCreateUser() {
        User u = new User();
        u.setUserName("test");
        u.setPassword("test");
        boolean ret = userFacade.createUser(u);
        
        assertEquals(true, ret); 
        User ut = userFacade.getUserByUserId("test");
        assertEquals("test", ut.getUserName()); 
        assertEquals("User", ut.getRoles().get(0)); 
    }
    
    @Test
    public void testCreateExistingUser() {
        User u1 = new User();
        u1.setUserName("newuser");
        u1.setPassword("test");
        boolean ret1 = userFacade.createUser(u1);
        
        User u2 = new User();
        u2.setUserName("newuser");
        u2.setPassword("merp");
        boolean ret2 = userFacade.createUser(u2);
        assertEquals(true, ret1); 
        assertEquals(false, ret2);
        User ut = userFacade.getUserByUserId("newuser");
        assertEquals("newuser", ut.getUserName()); 
        assertEquals("User", ut.getRoles().get(0)); 
        assertEquals("test", ut.getPassword()); 
    }
    
    @Test
    public void testDeleteUserByUserId() {
        User u1 = new User();
        u1.setUserName("deleteuser");
        u1.setPassword("test");
        boolean ret1 = userFacade.createUser(u1);
        assertEquals(true, ret1); 
        User ut = userFacade.deleteUserByUserId("deleteuser");
        assertEquals(null, userFacade.getUserByUserId("deleteuser"));
        
        assertEquals("deleteuser", ut.getUserName()); 
        assertEquals("User", ut.getRoles().get(0)); 
        assertEquals("test", ut.getPassword()); 
    }
    
    @Test
    public void testDeleteUserByUserIdNotExisting() {
        User ut = userFacade.deleteUserByUserId("deleteuser");
        assertEquals(null, ut);
        assertEquals(null, userFacade.getUserByUserId("deleteuser"));
    }
    
    @Test
    public void testGetUsers() {
        //TODO
    }
    
    @Test
    public void testAuthenticateUser() {
        List<String> roles = userFacade.authenticateUser("all", "test");
        assertThat(roles, is(Arrays.asList("User", "Admin")));
        roles = userFacade.authenticateUser("admin", "test");
        assertThat(roles, is(Arrays.asList("Admin")));
        roles = userFacade.authenticateUser("user", "test");
        assertThat(roles, is(Arrays.asList("User")));
    }
    
    @Test
    public void testAuthenticateUserWrongPw() {
        List<String> roles = userFacade.authenticateUser("all", "wrong");
        assertEquals(roles, null);
    }
    
    @Test
    public void testAuthenticateUserWrongUser() {
        List<String> roles = userFacade.authenticateUser("nouser", "test");
        assertEquals(roles, null);
    }
}
