/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonServlets.builders;

import entites.Picture;
import entites.Product;
import facades.PictureFacade;
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
public class JsonPictureBuilder {
    @EJB private ProductFacade productFacade;
    @EJB private PictureFacade pictureFacade;
    
    public JsonPictureBuilder() {
        Context ctx;
        try {
            ctx = new InitialContext();
            this.pictureFacade = (PictureFacade) ctx.lookup("java:global/JKTVR19WebLibrary/PictureFacade");
            this.productFacade = (ProductFacade) ctx.lookup("java:global/JKTVR19WebLibrary/ProductFacade");
        } catch (NamingException ex) {
            Logger.getLogger(JsonProductBuilder.class.getName()).log(Level.SEVERE, "Нет такого класса", ex);
        }
    }
    
    public JsonObject createProductJson(Picture picture){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("id", picture.getId())
                .add("description", picture.getDescription())
                .add("path", picture.getPath());
        return job.build();
    }
}
