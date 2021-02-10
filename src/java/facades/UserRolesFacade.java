/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entites.Role;
import entites.User;
import entites.UserRoles;
import java.util.ArrayList;
import java.util.List;
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

    public boolean isRole(String name, User user) {
        try {
            UserRoles userRole = (UserRoles) em.createQuery("SELECT userRoles FROM UserRoles userRoles WHERE userRoles.role.name = :name AND userRoles.user = :user")
                    .setParameter("name", name)
                    .setParameter("user", user)
                    .getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Role> getRoles(User u) {
        try {
            return em.createQuery("SELECT ur.role FROM UserRoles ur WHERE ur.user = :user")
                    .setParameter("user", u)
                    .getResultList();
        } catch (Exception e) {
            return (List<Role>) new ArrayList<Role>();
        }
    }

    public void setRoleToUser(Role r, User u) {
        if(!this.isRole(r.getName(), u)){
            UserRoles ur = new UserRoles(u, r);
            this.create(ur);
        }
    }

    public void removeRoleFromUser(Role r, User u) {
        if(this.isRole(r.getName(), u)){
            em.createQuery("DELETE FROM UserRoles ur WHERE ur.user = :user AND ur.role = :role")
                    .setParameter("user", u)
                    .setParameter("role", r)
                    .executeUpdate();
        }
    }

    
}
