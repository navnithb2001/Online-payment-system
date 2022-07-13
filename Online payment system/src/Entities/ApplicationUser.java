package Entities;


public class ApplicationUser{
    
    private Long userid;
    private Long clientid;
    private String username;
    private String password;
    private Roles user_role;

    public Long getUserid() {
        return userid;
    }
    public Long getClientid() {
        return clientid;
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
    public Roles getUser_role() {
        return user_role;
    }
    
    public ApplicationUser() {
    }

    

    public ApplicationUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.user_role = Roles.ADMIN;
    }
    public ApplicationUser(Long clientid, String username, String password, Roles user_role) {
        this.clientid = clientid;
        this.username = username;
        this.password = password;
        this.user_role = user_role;
    }

    public ApplicationUser(Long userid, Long clientid, String username, String password, Roles user_role) {
        this.userid = userid;
        this.clientid = clientid;
        this.username = username;
        this.password = password;
        this.user_role = user_role;
    }
    @Override
    public String toString() {
        return "UID: "+userid+" | Username: "+username+" | CID: "+clientid;
    }

    
    
}
