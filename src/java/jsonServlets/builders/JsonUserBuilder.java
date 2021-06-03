/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonServlets.builders;

import entites.Role;
import entites.User;
import facades.RoleFacade;
import facades.UserFacade;
import facades.UserRolesFacade;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author user
 */
public class JsonUserBuilder {
    @EJB private UserRolesFacade userRolesFacade;
    @EJB private RoleFacade roleFacade;
    @EJB private UserFacade userFacade;
    public JsonUserBuilder() {
        Context ctx;
        try {
            ctx = new InitialContext();
            this.roleFacade = (RoleFacade) ctx.lookup("java:global/SPTVR19PhonesShop/RoleFacade");
            this.userRolesFacade = (UserRolesFacade) ctx.lookup("java:global/SPTVR19PhonesShop/UserRolesFacade");
            this.userFacade = (UserFacade) ctx.lookup("java:global/SPTVR19PhonesShop/UserFacade");
        } catch (NamingException ex) {
            Logger.getLogger(JsonProductBuilder.class.getName()).log(Level.SEVERE, "Нет такого класса", ex);
        }
    }
        public JsonObject createUserJson(User user){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("id",user.getId())
                .add("login", user.getLogin())
                .add("money", user.getMoney())
                .add("topRole", roleFacade.find(userRolesFacade.getTopUserRole(user).getId()).getName());
        return job.build();
    }
    public JsonObject createRoleJson(Role role){
        JsonObjectBuilder job = Json.createObjectBuilder();
        
        job.add("id", role.getId())
                .add("name", role.getName());
        return job.build();
    }
    public JsonArray createAllRolesJson(){
        JsonArrayBuilder jab = Json.createArrayBuilder();
        List<Role> roles = roleFacade.findAll();
        roles.forEach((role) -> {
            jab.add(createRoleJson(role));
        });
        
        return jab.build();
    }
    public JsonArray createAllUsersJson(){
        JsonArrayBuilder jab = Json.createArrayBuilder();
        JsonObjectBuilder job;
        List<User> users = userFacade.findAllExceptAdmin();
        for (User u: users){
             job = Json.createObjectBuilder();
            jab.add(job.add("id",u.getId())
                .add("login", u.getLogin())
                .add("money", u.getMoney()));
        }
        return jab.build();
    }
}
