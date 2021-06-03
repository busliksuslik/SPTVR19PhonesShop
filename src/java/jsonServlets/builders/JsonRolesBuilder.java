/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonServlets.builders;

import entites.Role;
import facades.ProductFacade;
import facades.RoleFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author user
 */
public class JsonRolesBuilder {
    @EJB RoleFacade roleFacade;
    public JsonRolesBuilder() {
        Context ctx;
        try {
            ctx = new InitialContext();
            this.roleFacade = (RoleFacade) ctx.lookup("java:global/SPTVR19PhonesShop/RoleFacade");
        } catch (NamingException ex) {
            Logger.getLogger(JsonProductBuilder.class.getName()).log(Level.SEVERE, "Нет такого класса", ex);
        }
    }
    public JsonArray createRoles(){
        JsonArrayBuilder jab = Json.createArrayBuilder();
        JsonObjectBuilder job ;
        List<Role> roles = roleFacade.findAll();
        for (Role r : roles){
            job = Json.createObjectBuilder();
            jab.add(job.add("id", r.getId()).add("name", r.getName()).build());
        }
        return jab.build();
    }
}
