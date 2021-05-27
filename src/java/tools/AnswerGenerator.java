/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author user
 */
public class AnswerGenerator {
    
    public String generateAnswer(String info, String msg){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("info", info);
        job.add("requestStatus",msg);     
        JsonObject jsonResponse = job.build();
        return jsonResponse.toString();
    }
}
