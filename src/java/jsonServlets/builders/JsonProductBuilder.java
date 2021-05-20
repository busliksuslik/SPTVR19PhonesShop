/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonServlets.builders;

import entites.Product;
import facades.ProductFacade;
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
public class JsonProductBuilder {
    @EJB private ProductFacade productFacade;
    
    public JsonProductBuilder() {
        Context ctx;
        try {
            ctx = new InitialContext();
            this.productFacade = (ProductFacade) ctx.lookup("java:global/JKTVR19WebLibrary/ProductFacade");
        } catch (NamingException ex) {
            Logger.getLogger(JsonProductBuilder.class.getName()).log(Level.SEVERE, "Нет такого класса", ex);
        }
    }
    
    public JsonObject createProductJson(Product product){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("id", product.getId())
                .add("name", product.getName())
                .add("count", product.getCount())
                .add("price", product.getPrice());
        return job.build();
    }
}
