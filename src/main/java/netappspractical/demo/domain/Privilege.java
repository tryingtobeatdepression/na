//package netappspractical.demo.domain;
//
//import java.io.Serializable;
//import java.util.Collection;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "privileges")
//public class Privilege implements Serializable{
//
//    /**
//     *
//     */
//    private static final long serialVersionUID = 4565035592714934614L;
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String name;
//
//    @ManyToMany(mappedBy = "privileges")
//    private Collection<Role> roles;
//
//    public Privilege(Long id, String name, Collection<Role> roles) {
//        this.id = id;
//        this.name = name;
//        this.roles = roles;
//    }
//    public Privilege() {}
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Collection<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Collection<Role> roles) {
//        this.roles = roles;
//    }
//}
//
