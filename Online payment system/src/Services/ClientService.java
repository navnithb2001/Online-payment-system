package Services;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Entities.ApplicationUser;
import Entities.BankClient;
import Entities.Transaction;
import Utilty.Utility;

public class ClientService {

    private Utility utility;
    private BasicService basicService;

    
    
    public ClientService() {
        this.utility = new Utility();
        this.basicService = new BasicService();
    }

    public boolean isClientExists(String email, Long clientid) throws IllegalAccessException {
        return basicService.isClientExist(email,clientid);
    }


    public ApplicationUser getUserByid(Long userid) throws IllegalAccessException {
        return basicService.getUserByid(userid);
    }

    public BankClient getClientByid(Long clientid){
        return basicService.getClientByid(clientid);
    }

   
    public BankClient getClientByUserid(Long userid){
        return basicService.getClientByUserid(userid);
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

    public boolean updateClient(BankClient client){
       try {
           if(utility.isEmailValid(client.getEmail()) && !isClientExists(client.getEmail(), client.getClientid())){
                Long clientid = client.getClientid();

                String query = "update bank_client set name='"+client.getName()+"',email='"+client.getEmail()+"',dob='"+Date.valueOf(client.getDob())+"',address='"+client.getAddress()+"',phoneno="+client.getPhoneno()+",balance="+client.getBalance()+" where clientid="+clientid+";";
                int rs = utility.sendUpdateQuery(query);

                if(rs > 0){
                    System.out.println("update successful");  
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

   
    public List<Transaction> getTransactionsByUser(Long userid) throws NumberFormatException, IllegalAccessException {
        ArrayList<Transaction> transactions  = new ArrayList<>();
        try {
            ApplicationUser user = getUserByid(userid);
            Long clientid = user.getClientid();

            String query = "select * from transaction where senderclientid ="+clientid+" or recieverclientid="+clientid+";";
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

    
    public boolean performTransaction(Transaction t) throws NumberFormatException, IllegalAccessException {
        try {

            Long id = basicService.getTransid();
            BankClient sender = getClientByid(t.getSender_Clientid());
            BankClient reciever = getClientByid(t.getReciever_Clientid());
            String query = "insert into transaction (transid,amount,recieverclientid,senderclientid,trans_date) values ("+id+","+t.getAmount()+","+t.getReciever_Clientid()+","+t.getSender_Clientid()+",'"+Date.valueOf(t.getTrans_date())+"');";
            sender.setBalance(sender.getBalance()-t.getAmount());
            reciever.setBalance(reciever.getBalance()+ t.getAmount());
            updateClient(sender);
            updateClient(reciever);
            int rs = utility.sendUpdateQuery(query);

            if(rs >0){
                System.out.println("Transaction performed");
                basicService.updateTrnsid();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    

}
