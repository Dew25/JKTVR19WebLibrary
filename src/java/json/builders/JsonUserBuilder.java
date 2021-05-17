/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json.builders;

import entity.Book;
import entity.Picture;
import entity.Text;
import entity.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import session.UserRolesFacade;

/**
 *
 * @author Melnikov
 */
public class JsonUserBuilder {
    @EJB private UserRolesFacade userRolesFacade;
    public JsonUserBuilder() {
        Context ctx;
        try {
            ctx = new InitialContext();
            this.userRolesFacade = (UserRolesFacade) ctx.lookup("java:global/JKTVR19WebLibrary/UserRolesFacade");
        } catch (NamingException ex) {
            Logger.getLogger(JsonUserBuilder.class.getName()).log(Level.SEVERE, "Нет такого класса", ex);
        }
    }
    
    public JsonObject createUserJson(User user){
        String role = userRolesFacade.getTopRole(user);
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("id", user.getId())
                .add("login", user.getLogin())
                .add("reader", new JsonReaderBuilder().createJsonReader(user.getReader()))
                .add("role", role);
        return job.build();
    }
}
