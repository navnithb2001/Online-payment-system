package Controller;

import java.util.ArrayList;

import Entities.ApplicationUser;
import Entities.BankClient;
import Entities.Transaction;
import Services.ClientService;
import Utilty.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class clientViewController{
    @FXML
    private AnchorPane apClientView;

    @FXML
    private AnchorPane apUpdate;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfPhone;

    @FXML
    private Label laBalance;

    @FXML
    private DatePicker dpDOB;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnTransfer;

    @FXML
    private Button btnProfile;

    @FXML
    private ListView<String> lvTransactions;

    @FXML
    private Label lblError;


    private ClientService service;
    private ApplicationUser user;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private Stage stage;
    private Utility utility;
    private Long userid;
    

    @FXML
    void transfer(ActionEvent event) {
        FXMLLoader loader= utility.SceneSetter(stage, "/view/transferView.fxml/", event, 481, 284);
        System.out.println(loader.getController().getClass());
        TransferController controller = loader.getController();
        controller.init_data(userid);
    }

    @FXML
    void logout(ActionEvent event) {
        utility.SceneSetter(stage, "/view/loginView.fxml", event,600,400);
    }

    @FXML
    void updateClient(ActionEvent event) throws NumberFormatException, IllegalAccessException {
        BankClient client = new BankClient(user.getClientid(), tfName.getText(), tfEmail.getText(),
                dpDOB.getValue(),Long.parseLong(laBalance.getText()), Long.parseLong(tfPhone.getText()), tfAddress.getText());
        if(service.updateClient(client)){
            lblError.setText("");
        }
        else{
            lblError.setText("Email is wrong or taken");
            tfEmail.clear();
        }
    }

    @FXML
    void toProfile(ActionEvent event) {
        FXMLLoader loader= utility.SceneSetter(stage, "/view/profileView.fxml/", event, 481, 284);
        System.out.println(loader.getController().getClass());
        ProfileController controller = loader.getController();
        controller.init_data(userid);
    }

    public ArrayList<String> transactionsList_To_stringList(ArrayList<Transaction> TransactionsList) {
        ListSetter listViewSetter = ()->{
            ArrayList<String> tranListString = new ArrayList<>();
            for(Transaction trans: TransactionsList){
                String transactionString = trans.toString();
                tranListString.add(transactionString);
            }
        return tranListString;
        };
        return listViewSetter.setList();
    }

    public void init_data(Long userid) {
        this.userid= userid;
        System.out.println(userid);
        utility = new Utility();
        service = new ClientService();
        try {
            user = service.getUserByid(userid);
            BankClient client = service.getClientByUserid(userid);
            transactions = (ArrayList<Transaction>) service.getTransactionsByUser(userid);
            tfAddress.setText(client.getAddress());
            tfEmail.setText(client.getEmail());
            tfName.setText(client.getName());
            tfPhone.setText(client.getPhoneno().toString());
            laBalance.setText(client.getBalance().toString());
            dpDOB.setValue(client.getDob());

            lvTransactions.setCellFactory(cell -> {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            setFont(Font.font(18));
                        }
                    }
                };
            });
            lvTransactions.getItems().addAll(transactionsList_To_stringList(transactions));
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    


}


