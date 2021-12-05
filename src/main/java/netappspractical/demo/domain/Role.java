//package netappspractical.demo.domain;
//
//import java.io.Serializable;
//import java.util.Collection;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "roles")
//public class Role implements Serializable{
//
//    /**
//     *
//     */
//    private static final long serialVersionUID = 558572439237793526L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String name;
//    @ManyToMany(mappedBy = "roles")
//    private Collection<_User> users;
//
//    @ManyToMany
//    @JoinTable(
//            name = "roles_privileges",
//            joinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "privilege_id", referencedColumnName = "id"))
//    private Collection<Privilege> privileges;
//
//
//    public Role() {}
//    public Role(Long id, String name, Collection<_User> users, Collection<Privilege> privileges) {
//        this.id = id;
//        this.name = name;
//        this.users = users;
//        this.privileges = privileges;
//    }
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
//    public Collection<_User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Collection<_User> users) {
//        this.users = users;
//    }
//
//    public Collection<Privilege> getPrivileges() {
//        return privileges;
//    }
//
//    public void setPrivileges(Collection<Privilege> privileges) {
//        this.privileges = privileges;
//    }
//}
