package facades;

import entity.User;
import entity.UserRole;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserFacade implements UserFacadeInterface {
    private static EntityManagerFactory emf;

    public UserFacade() {
        emf = Persistence.createEntityManagerFactory(deploy.DeploymentConfiguration.PU_NAME);
    }
    
    public UserFacade(String pu) {
        emf = Persistence.createEntityManagerFactory(pu);
    }
    
    @Override
    public void makeUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            //String pw = PasswordHash.createHash("test");
            String pw = "test";
            User u;
            UserRole r;
            em.getTransaction().begin();
            r = em.find(UserRole.class, "User");
            if (r == null) {
                UserRole roleUser = new UserRole("User");
                em.persist(roleUser);
            }
            r = em.find(UserRole.class, "Admin");
            if (r == null) {
                UserRole roleAdmin = new UserRole("Admin");
                em.persist(roleAdmin);
            }
            
            u = em.find(User.class, "user");
            if (u == null) {
                
                User user = new User("user", pw);
                user.AddRole(em.find(UserRole.class, "User"));
                em.persist(user);
            }
            u = em.find(User.class, "admin");
            if (u == null) {
                User admin = new User("admin", pw);
                admin.AddRole(em.find(UserRole.class, "Admin"));
                em.persist(admin);
            }
            u = em.find(User.class, "all");
            if (u == null) {
                User both = new User("all", pw);
                both.AddRole(em.find(UserRole.class, "User"));
                both.AddRole(em.find(UserRole.class, "Admin"));
                em.persist(both);
            }
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }
  
    @Override
    public boolean createUser(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = em.find(User.class, user.getUserName());
        if (u == null) {
            System.out.println(user.getUserName());
            User newUser = new User(user.getUserName(), user.getPassword());
            UserRole r = em.find(UserRole.class, "User");
            newUser.AddRole(r);
            em.persist(newUser);
            em.getTransaction().commit();
            em.close();
            return true;
        }
        else {
            return false;
            //error handled in rest client.
            //TODO: error handling, user already exist. 
        }
    }
    @Override
    public User getUserByUserId(String userName) {
        EntityManager em = emf.createEntityManager();
        User u = em.find(User.class, userName);
        em.close();
        return u;
    }
    
    @Override
    public User deleteUserByUserId(String userName) {
        EntityManager em = emf.createEntityManager();
        
        User u = em.find(User.class, userName);
        if (u != null) {
            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();
        }
        em.close();
        return u;
    }
    
    @Override
    public List<User> getUsers() {
        EntityManager em = emf.createEntityManager();
        List<User> users = em.createNamedQuery("User.findAll").getResultList();
        em.close();
        return users;
    }
  /*
  Return the Roles if users could be authenticated, otherwise null
  */
    @Override
    public List<String> authenticateUser(String userName, String password) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, userName);
        em.close();
        return user != null && user.getPassword().equals(password) ? user.getRoles(): null;
    }
/*
      public List<String> authenticateUser(String userName, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, userName);
        
        
        em.close();
        return user != null && 
               PasswordHash.validatePassword(password, user.getPassword()) ? user.getRoles(): null;
    }
  */
}
