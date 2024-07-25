package fr.ubs.sporttrack.user.model;

import org.json.JSONObject;
import jakarta.validation.constraints.NotNull;

public class User {
    private static final String NAME_FIELD = "name";
    private static final String EMAIL_FIELD = "e-mail";
    private static final String PASSWORD_FIELD = "password";


    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    
    public User() {
    }
 
    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password= password;
    } 

 
    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public static User fromJSON(JSONObject obj){
        User act = new User(obj.getString(NAME_FIELD), obj.getString(EMAIL_FIELD), obj.getString(PASSWORD_FIELD));
        return act;
    }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put(NAME_FIELD, this.name);
        obj.put(EMAIL_FIELD, this.email);
        obj.put(PASSWORD_FIELD, this.password);
        return obj;
    }

    @Override
    public String toString(){
        return this.toJSON().toString();
    }
}
