package netappspractical.demo.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "privileges")
public class Privilege implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 4565035592714934614L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    public Privilege(String name) {
        this.name = name;
    }

    public Privilege() {
    }

    // ✅
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}

