/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonServlets.builders;

import entites.Product;
import entites.Role;
import entites.Tag;
import facades.ProductTagFacade;
import facades.TagFacade;
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
public class JsonTagBuilder {
        @EJB private ProductTagFacade productTagFacade;
        @EJB private TagFacade tagFacade;
    
    public JsonTagBuilder() {
        Context ctx;
        try {
            ctx = new InitialContext();
            this.productTagFacade = (ProductTagFacade) ctx.lookup("java:global/SPTVR19PhonesShop/ProductTagFacade");
            this.tagFacade = (TagFacade) ctx.lookup("java:global/SPTVR19PhonesShop/TagFacade");
        } catch (NamingException ex) {
            Logger.getLogger(JsonProductBuilder.class.getName()).log(Level.SEVERE, "Нет такого класса", ex);
        }
    }
    
    public JsonArray createTagsJson(Product product){
        JsonArrayBuilder jab = Json.createArrayBuilder();
        List<Tag> tags = productTagFacade.findTags(product);
        
        tags.forEach((tag) -> {
            jab.add(tag.getName());
        });
        
                
        return jab.build();
    }

    public JsonArray createAllTagsJson(){
        JsonArrayBuilder jab = Json.createArrayBuilder();
        List<Tag> tags = tagFacade.findAll();
        tags.forEach((tag) -> {
            JsonObjectBuilder job = Json.createObjectBuilder();
            job.add("name", tag.getName())
                    .add("id", tag.getId());
            jab.add(job.build());
        });
        return jab.build();
    }
}
