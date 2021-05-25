/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonServlets.builders;

import entites.Product;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author nikita
 */
public class JsonPairBuilder {
    public JsonObject createPairJson(Product key, Integer value){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("product", new JsonProductBuilder().createProductJson(key))
                .add("count", value);
        return job.build();
    }
}
