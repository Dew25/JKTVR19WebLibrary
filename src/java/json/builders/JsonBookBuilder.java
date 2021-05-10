/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json.builders;

import entity.Book;
import entity.Picture;
import entity.Text;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author Melnikov
 */
public class JsonBookBuilder {
    public JsonObject createJsonBook(Book book,Picture picture, Text text){
        JsonObjectBuilder jsonPictureBuilder = Json.createObjectBuilder();
        jsonPictureBuilder.add("description", picture.getDescription())
                .add("path", picture.getPathToFile())
                .add("url", picture.getUrl());
        JsonObjectBuilder jsonTextBuilder = Json.createObjectBuilder();
        jsonTextBuilder.add("description", text.getDescription())
                .add("path", text.getPathToFile())
                .add("url", text.getUrl());
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("id", book.getId())
                .add("name", book.getName())
                .add("publishedYear", book.getPublishedYear())
                .add("picture", jsonPictureBuilder.build())
                .add("text", jsonTextBuilder.build());
        return job.build();
    }
}
