/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.User;
import java.util.List;

/**
 *
 * @author ichti
 */
public interface UserFacadeInterface {
    public List<String> authenticateUser(String userName, String password);
    public List<User> getUsers();
    public User deleteUserByUserId(String userName);
    public User getUserByUserId(String userName);
    public boolean createUser(User user);
    public void makeUsers();
}
