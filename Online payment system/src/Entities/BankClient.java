package Entities;

import java.time.LocalDate;
import java.time.Period;

public class BankClient{

    private Long clientid;
    private String name;
    private String email;
    private LocalDate dob;
    private Long balance;
    private Long phoneno;
    private String address;

    
    public Long getClientid() {
        return clientid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(Long phoneno) {
        this.phoneno = phoneno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAge() {
        return (long) Period.between(this.dob, LocalDate.now()).getYears(); 
    }

    public BankClient() {
    }

    public BankClient(String name, String email, LocalDate dob, Long balance, Long phoneno, String address) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.balance = balance;
        this.phoneno = phoneno;
        this.address = address;
    }

    public BankClient(Long clientid, String name, String email, LocalDate dob, Long balance, Long phoneno,
            String address) {
        this.clientid = clientid;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.balance = balance;
        this.phoneno = phoneno;
        this.address = address;
    }
    
}
