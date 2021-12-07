package netappspractical.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class UserObjectResponse implements Serializable {

    private String name, email;
    private Integer id;


    public UserObjectResponse(String name, String email, Integer id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getId() {
        return id;
    }
}
