package Services;

import java.sql.ResultSet;

import Entities.ApplicationUser;
import Utilty.Utility;

public class CommonService {

    private Utility utility;
    private BasicService basicService;

    public CommonService() {
        this.utility = new Utility();
        this.basicService = new BasicService();

    }

    public ApplicationUser login(String username,String password){
        String query = "select * from application_user where username = '"+username+"' and password = '"+password+"';";
        ResultSet rs = utility.sendQuery(query);
        try {
            if(rs != null && rs.next()){
                    ApplicationUser user = basicService.getUserByid(rs.getLong("userid"));
                    return user;
            }
            else{
                throw new IllegalStateException("username or password is wrong");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
}
