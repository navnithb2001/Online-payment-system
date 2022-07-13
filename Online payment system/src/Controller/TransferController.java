package Controller;

import Entities.ApplicationUser;
import Entities.BankClient;
import Entities.Transaction;
import Services.ClientService;
import Utilty.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TransferController{

    @FXML
    private TextField tfReciever;

    @FXML
    private TextField tfAmount;

    @FXML
    private Button btnTranfer;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblToError;

    @FXML
    private Label lblAmountError;

    private Utility utility;
    private ClientService service;
    private ApplicationUser user;
    private BankClient client;
    private Stage stage;

    @FXML
    void back(ActionEvent event) {
        FXMLLoader loader= utility.SceneSetter(stage, "/view/clientView.fxml/", event,930,430);
        clientViewController controller = loader.getController();
        controller.init_data(user.getUserid());
    }

    @FXML
    void transfer(ActionEvent event) throws NumberFormatException, IllegalAccessException {
        ApplicationUser reciever = service.getUserByid(Long.parseLong(tfReciever.getText()));
        boolean r=false,a=false;
        if(reciever == null){
            r= false;
            lblToError.setText("Wrong RecieverId");
            tfReciever.clear();
        }
        else{
            r = true;
            lblToError.setText("");
        }

        if(Long.parseLong(tfAmount.getText()) > client.getBalance()){
                a = false;
                lblAmountError.setText("Low balance");
                tfAmount.clear();
        }
        else if(Long.parseLong(tfAmount.getText())<0){
                a = false;
                lblAmountError.setText("Wrong amount");
                tfAmount.clear();
        }
        else{
                a = true;
                Transaction t = new Transaction(user.getClientid(), reciever.getClientid(), Long.parseLong(tfAmount.getText()));
                service.performTransaction(t);
        }
        
        if(r && a){
            back(event);
        }
    }

    public void init_data(Long userid) {
        System.out.println("in Transfer userid = "+ userid);
        utility = new Utility();
        service = new ClientService();

        try {
            user = service.getUserByid(userid);
            client = service.getClientByid(user.getClientid());

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
