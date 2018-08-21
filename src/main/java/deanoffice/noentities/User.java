package deanoffice.noentities;

public class User {
    private String username;
    private String password;
    private String role;
    private Boolean enable;

    public User(){

    }

    public User(String username, String role){
        this.username = username;
        this.role = role;
    }

    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * Change it!
     */
    public void encodePassword(){
        org.springframework.security.crypto.password.PasswordEncoder encoder
                = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        // $2a$10$lB6/PKg2/JC4XgdMDXyjs.dLC9jFNAuuNbFkL9udcXe/EBjxSyqxW
        // true
        // $2a$10$KbQiHKTa1WIsQFTQWQKCiujoTJJB7MCMSaSgG/imVkKRicMPwgN5i
        // true
        // $2a$10$5WfW4uxVb4SIdzcTJI9U7eU4ZwaocrvP.2CKkWJkBDKz1dmCh50J2
        // true
        // $2a$10$0wR/6uaPxU7kGyUIsx/JS.krbAA9429fwsuCyTlEFJG54HgdR10nK
        // true
        // $2a$10$gfmnyiTlf8MDmwG7oqKJG.W8rrag8jt6dNW.31ukgr0.quwGujUuO
        // true
        for (int i = 0; i < 5; i++) {
            // "123456" - plain text - user input from user interface
            String passwd = encoder.encode("123456");

            // passwd - password from database
            System.out.println(passwd); // print hash

            // true for all 5 iteration
            System.out.println(encoder.matches("123456", passwd));
        }
    }
}
