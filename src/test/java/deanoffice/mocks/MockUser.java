package deanoffice.mocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public enum MockUser implements UserDetails {
    STUDENT("STUDENT1", List.of(new MockAuthority("ROLE_STUDENT"))),
    TUTOR("TUTOR1", List.of(new MockAuthority("ROLE_TUTOR"))),
    ADMIN("ADMIN1", List.of(new MockAuthority("ROLE_ADMIN")));
    
    private static final long serialVersionUID = 1L;
    private final String username;
    private final Collection<? extends GrantedAuthority> authorities;

    private MockUser(String username, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(authorities);
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
