package Entities;

import java.time.LocalDate;

public class Transaction{

    private Long transid;
    private Long senderclientid;
    private Long recieverclientid;
    private Long amount;
    private LocalDate trans_date;
    
    public Long getTransid() {
        return transid;
    }
    public Long getSender_Clientid() {
        return senderclientid;
    }
    public void setSender_Clientid(Long senderclientid) {
        this.senderclientid = senderclientid;
    }
    public Long getReciever_Clientid() {
        return recieverclientid;
    }
    public void setReciever_Clientid(Long recieverclientid) {
        this.recieverclientid = recieverclientid;
    }
    public Long getAmount() {
        return amount;
    }
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public LocalDate getTrans_date() {
        return trans_date;
    }
    
    public Transaction() {
        this.trans_date = LocalDate.now();
    }
    public Transaction(Long sender_Clientid, Long recieverclientid, Long amount) {
        this.senderclientid = sender_Clientid;
        this.recieverclientid = recieverclientid;
        this.amount = amount;
        this.trans_date = LocalDate.now();
    }
    public Transaction(Long transid, Long sender_Clientid, Long recieverclientid, Long amount) {
        this.transid = transid;
        this.senderclientid = sender_Clientid;
        this.recieverclientid = recieverclientid;
        this.amount = amount;
        this.trans_date = LocalDate.now();
    }
    public Transaction(Long transid, Long senderclientid, Long recieverclientid, Long amount, LocalDate trans_date) {
        this.transid = transid;
        this.senderclientid = senderclientid;
        this.recieverclientid = recieverclientid;
        this.amount = amount;
        this.trans_date = trans_date;
    }
    @Override
    public String toString() {
        return "TID: "+transid+" | From: "+senderclientid+" | To: "+recieverclientid+" | Amount: "+amount+" | Date: "+trans_date;
    }
    
    
 
}
