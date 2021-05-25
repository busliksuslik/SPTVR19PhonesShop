/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonServlets.builders;

import entites.History;
import facades.ProductFacade;
import facades.UserFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author user
 */
public class JsonHistoryBuilder {
    @EJB private ProductFacade productFacade;
    @EJB private UserFacade userFacade;
    
    public JsonHistoryBuilder() {
        Context ctx;
        try {
            ctx = new InitialContext();
            this.userFacade = (UserFacade) ctx.lookup("java:global/SPTVR19PhonesShop/UserFacade");
            this.productFacade = (ProductFacade) ctx.lookup("java:global/SPTVR19PhonesShop/ProductFacade");
        } catch (NamingException ex) {
            Logger.getLogger(JsonProductBuilder.class.getName()).log(Level.SEVERE, "Нет такого класса", ex);
        }
    }
    
    public JsonObject createProductJson(History history){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("id", history.getId())
                .add("count", history.getCount())
                .add("name", history.getProduct().getName())
                .add("login", history.getUser().getLogin())
                .add("date", history.getTakeOn().toString());
        return job.build();
    }
}
