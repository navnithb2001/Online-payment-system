package Services;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Entities.ApplicationUser;
import Entities.BankClient;
import Entities.Roles;
import Entities.Transaction;
import Utilty.Utility;

public class ManagementService {
    private Utility utility;
    private BasicService basicService;

    public ManagementService() {
        this.utility = new Utility();
        this.basicService = new BasicService();
    }

    public ApplicationUser getUserByid(Long userid) throws IllegalAccessException {
        return basicService.getUserByid(userid);
    }
   
    public boolean updateUsername(Long userid,
                               String username) throws IllegalAccessException {
        return basicService.updateUsername(userid, username);
    }

   
    public boolean updatePassword(Long userid,
                               String oldpassword,
                               String newpassword) throws IllegalAccessException {
        return basicService.updatePassword(userid, oldpassword, newpassword);
        
    }

    public List<ApplicationUser> getAllUsers() {
        ArrayList<ApplicationUser> users  = new ArrayList<>();
        try {
            String query = "select * from application_user;";
            ResultSet rs = utility.sendQuery(query);

            while(rs.next()){
                ApplicationUser user = basicService.getUserByid(rs.getLong("userid"));
                if(user.getUser_role() == Roles.CLIENT){
                    users.add(user);
                }
            }
            System.out.println("getting user list success");
            return users;

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

   
    public List<Transaction> getALLTransactions() throws NumberFormatException, IllegalAccessException {
        ArrayList<Transaction> transactions  = new ArrayList<>();
        try {
            String query = "select * from transaction;";
            ResultSet rs = utility.sendQuery(query);

            while(rs.next()){
                Transaction t = basicService.getTransactionbyId(rs.getLong("transid"));
                transactions.add(t);
            }
            System.out.println("getting Transaction list success");
            return transactions;

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
        
    }

    public boolean addUsers(ApplicationUser user) {
        
        Long userid = basicService.getUserid();
        int role = 1;
        if(user.getUser_role() == Roles.ADMIN){
            role =0;
        }
        String query = "insert into application_user (userid,clientid,password,user_role,username) values ("+ userid +","+ user.getClientid() +",'"+user.getPassword()+"',"+role+",'"+user.getUsername()+"');";
        int rs1 = utility.sendUpdateQuery(query);
        if(rs1 > 0){
            System.out.print("user added");
            basicService.updateUserid();
            return true;
        }
        return false;
    }

    public boolean addClient(BankClient client) throws IllegalAccessException {
        if(utility.isEmailValid(client.getEmail()) && !basicService.isClientExist(client.getEmail())){
            Long clientid = basicService.getClientid();
            String query = "insert into bank_client (clientid,address,balance,dob,email,name,phoneno) values ("+ clientid +",'"+client.getAddress()+"',"+client.getBalance()+",'"+Date.valueOf(client.getDob())+"','"+client.getEmail()+"','"+client.getName()+"',"+client.getPhoneno()+");";
            int rs = utility.sendUpdateQuery(query);
            if(rs > 0){
                System.out.print("bankclient added");
                basicService.updateClientid();
                ApplicationUser user = new ApplicationUser(clientid, client.getEmail(), "welcome@1", Roles.CLIENT);
                addUsers(user);
                return true;
            }
        }
            return false;
    }

    public boolean deleteUser(Long userid) {
        ApplicationUser user;
        try {
            user = basicService.getUserByid(userid);
            Long clientid = user.getClientid();

            if(clientid != null){
                String query = "delete from bank_client where clientid="+ clientid;
                int rs = utility.sendUpdateQuery(query);
                if(rs>0){
                    System.out.println("client with clientid="+clientid+" deleted");
                    query = "delete from application_user where userid="+ userid;
                    int rs1 = utility.sendUpdateQuery(query);
                    if(rs1 > 0){
                        System.out.println("user with userid="+userid+" deleted");
                        return true;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
}
