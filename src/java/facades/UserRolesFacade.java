/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entites.Role;
import entites.User;
import entites.UserRoles;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nikita
 */
@Stateless
public class UserRolesFacade extends AbstractFacade<UserRoles> {

    @PersistenceContext(unitName = "SPTVR19WebPhoneShopPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserRolesFacade() {
        super(UserRoles.class);
    }

    public Boolean isRole(String roleName, User user) {
        try {
            UserRoles userRoles = (UserRoles) em.createQuery("SELECT userRoles FROM userRoles userRoles WHERE userRoles.roleName = :role AND userRoles.user = :user")
                    .setParameter("roleName", roleName).setParameter("user", user).getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
