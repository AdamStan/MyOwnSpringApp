package deanoffice.security;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Authorities")
public class Role {
    @Id
    @Column(nullable = false)
    private String username;
    private String authority;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<User> users;

    public Role() {
    }

    public Role(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role [username=" + username + ", authority=" + authority + "]";
    }

}
