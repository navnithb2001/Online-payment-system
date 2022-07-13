package Services;

import java.sql.ResultSet;
import java.sql.SQLException;

import Entities.ApplicationUser;
import Entities.BankClient;
import Entities.Roles;
import Entities.Transaction;
import Utilty.Utility;

public class BasicService {
    private Utility utility;

    public BasicService() {
        this.utility = new Utility();
    }

    public ApplicationUser getUserByid(Long userid) throws IllegalAccessException {
        String query = "select * from application_user where userid ="+userid+";";
        ResultSet rs = utility.sendQuery(query);
        try {
            if(rs != null && rs.next()){
                    Roles role = Roles.ADMIN;
                    if(rs.getInt("user_role") == 1){
                        role = Roles.CLIENT;
                    }
                    ApplicationUser user = new ApplicationUser(rs.getLong("userid"), rs.getLong("clientid"),
                                            rs.getString("username"),rs.getString("password") ,role);
                    System.out.println("getting user with that userid="+userid+" successful");
                    return user;
            }
            else{
                System.out.println("user with that userid="+userid+" does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ApplicationUser getUserByUsername(String username) throws IllegalAccessException {
        String query = "select * from application_user where username ='"+username+"';";
        ResultSet rs = utility.sendQuery(query);
        try {
            if(rs != null && rs.next()){
                Roles role = Roles.ADMIN;
                if(rs.getInt("user_role") == 1){
                    role = Roles.CLIENT;
                }
                ApplicationUser user = new ApplicationUser(rs.getLong("userid"), rs.getLong("clientid"),
                                            username,rs.getString("password") ,role);
                System.out.println("getting user with that username="+username+" successful");
                return user;
            }
            else{
                System.out.println("user with that username="+username+" does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isUserExist(String username) throws IllegalAccessException {
        String query = "select * from application_user where username ='"+username+"';";
        ResultSet rs = utility.sendQuery(query);
        try {
            if(!rs.next()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isUserExist(String username,Long userid) throws IllegalAccessException {
        String query = "select * from application_user where username ='"+username+"' and userid !="+userid+";";
        ResultSet rs = utility.sendQuery(query);
        try {
            if(!rs.next()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public BankClient getClientByid(Long clientid){
        try {

            String query = "select * from bank_client where clientid ="+clientid+";";
            ResultSet rs = utility.sendQuery(query);

            if(rs.next()){
                BankClient client = new BankClient(clientid, rs.getString("name"), rs.getString("email")
                  ,rs.getDate("dob").toLocalDate(), rs.getLong("balance"), rs.getLong("phoneno"), rs.getString("address"));

                return client;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public BankClient getClientbyEmail(String email) throws IllegalAccessException {
        String query = "select * from bank_client where email='"+email+"';";
        ResultSet rs = utility.sendQuery(query);
        try {
            if(rs != null && rs.next()){
                    BankClient client = new BankClient(rs.getLong("clientid"), rs.getString("name"), rs.getString("email")
                    ,rs.getDate("dob").toLocalDate(), rs.getLong("balance"), rs.getLong("phoneno"), rs.getString("address"));

                    return client;
            }
            else{
                System.out.println("client with that email="+email+" does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isClientExist(String email, Long clientid) throws IllegalAccessException {
        String query = "select * from bank_client where email ='"+email+"' and clientid !="+clientid+";";
        ResultSet rs = utility.sendQuery(query);
        try {
            if(!rs.next()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isClientExist(String email) throws IllegalAccessException {
        String query = "select * from bank_client where email ='"+email+"';";
        ResultSet rs = utility.sendQuery(query);
        try {
            if(!rs.next()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

   
    public BankClient getClientByUserid(Long userid){
        try {
            ApplicationUser user = getUserByid(userid);
            Long clientid = user.getClientid();

            String query = "select * from bank_client where clientid ="+clientid+";";
            ResultSet rs = utility.sendQuery(query);

            if(rs.next()){
                BankClient client = getClientByid(clientid);
                return client;
            }
            else{
                System.out.println("Bank Account belonging to user="+userid+" does not exist");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public  Transaction getTransactionbyId(Long transid) {
        try {

            String query = "select * from transaction where transid ="+transid+";";
            ResultSet rs = utility.sendQuery(query);

            if(rs.next()){
                Transaction t = new Transaction(rs.getLong("transid"),rs.getLong("senderclientid"),
                                rs.getLong("recieverclientid"), rs.getLong("amount"),rs.getDate("trans_date").toLocalDate());

                return t;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public boolean updateUsername(Long userid,
                               String username) {
        try {
            if(isUserExist(username,userid) || username.equals("")){
                return false;
            }
            else{ 
                String query = "update application_user set username='"+username+"' where userid="+userid+";";
                int rs = utility.sendUpdateQuery(query);

                if(rs > 0){
                    System.out.println("update username successful");  
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

   
    public boolean updatePassword(Long userid,
                               String oldpassword,
                               String newpassword){
        try {
            ApplicationUser user = getUserByid(userid);
            if(user.getPassword().equals(oldpassword)){
                String query = "update application_user set password='"+newpassword+"' where userid="+userid+";";
                int rs = utility.sendUpdateQuery(query);

                if(rs > 0){
                    System.out.println("update password successful");  
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
        
    }

    public void updateTrnsid() {
        Long id = getTransid();
        String query = "update transaction_sequence set next_val="+(id+1);
        int r1 = utility.sendUpdateQuery(query);
        if(r1>0){
            System.out.println("next_val update success");
        }
    }

    public Long getTransid() {
        String query = "select * from transaction_sequence;";
        ResultSet r = utility.sendQuery(query);
        try {
            if(r.next()){
                Long id = r.getLong("next_val");
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateClientid() {
        Long id = getClientid();
        String query = "update bank_client_sequence set next_val="+(id+1);
        int r1 = utility.sendUpdateQuery(query);
        if(r1>0){
            System.out.println("next_val update success");
        }
    }

    public Long getClientid() {
        String query = "select * from bank_client_sequence;";
        ResultSet r = utility.sendQuery(query);
        try {
            if(r.next()){
                Long id = r.getLong("next_val");
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUserid() {
        Long id = getUserid();
        String query = "update user_sequence set next_val="+(id+1);
        int r1 = utility.sendUpdateQuery(query);
        if(r1>0){
            System.out.println("next_val update success");
        }
    }

    public Long getUserid() {
        String query = "select * from user_sequence;";
        ResultSet r = utility.sendQuery(query);
        try {
            if(r.next()){
                Long id = r.getLong("next_val");
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
