/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entites.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nikita
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "SPTVR19WebPhoneShopPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public UserFacade() {
        super(User.class);
    }
    
    public User userFind(String name, String pass){
        try {
            return (User) em.createQuery("SELECT u FROM User u WHERE u.login = '" + name + "' AND u.password = '" + pass + "'")
                    .getResultList()
                    .get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
    public User userFind(String name){
        try {
            return (User) em.createQuery("SELECT u FROM User u WHERE u.login = '" + name + "'")
                    .getResultList()
                    .get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean userExist(String name, String pass, String role){
        try {
            return !em.createQuery("SELECT u FROM User u WHERE u.login = '" + name + "' AND u.password = '" + pass + "' AND u.role = '" + role + "'")
                    .getResultList()
                    .isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean userExist(String name, String pass){
        try {
            return !em.createQuery("SELECT u FROM User u WHERE u.login = '" + name + "' AND u.password = '" + pass + "'")
                    .getResultList()
                    .isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean userExist(String name){
        try {
            return !em.createQuery("SELECT u FROM User u WHERE u.login = '" + name + "'")
                    .getResultList()
                    .isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
}
