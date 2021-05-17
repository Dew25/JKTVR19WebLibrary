/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json.builders;

import entity.Reader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Melnikov
 */
public class JsonReaderBuilder {
    public JsonObject createJsonReader(Reader reader){
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("id", reader.getId())
           .add("firstname", reader.getFirstname())
           .add("lastname", reader.getLastname())
           .add("phone", reader.getPhone());
        return job.build();
    }
}
