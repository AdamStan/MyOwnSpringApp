package deanoffice.mocks;

import org.springframework.security.core.GrantedAuthority;

public class MockAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 1L;
    private String auth;
    
    public MockAuthority(String authtority) {
        auth = authtority;
    }

    @Override
    public String getAuthority() {
        return auth;
    }

}
