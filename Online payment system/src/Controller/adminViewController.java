package Controller;

import java.util.ArrayList;

import Entities.ApplicationUser;
import Entities.BankClient;
import Entities.Transaction;
import Services.ManagementService;
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
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class adminViewController{

    @FXML
    private AnchorPane apAView;

    @FXML
    private AnchorPane apAdd;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfPhone;

    @FXML
    private DatePicker dpDOB;

    @FXML
    private Button btnAdd;

    @FXML
    private TextField tfBalance;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnProfile;

    @FXML
    private ListView<String> lvTranOrUsers;

    @FXML
    private ToggleButton btnToggle;

    @FXML
    private Label lblError;

    @FXML
    private Button btnDelete;

    private Stage stage;
    private Utility utility;
    private ManagementService service;
    private Long userid;
    private ArrayList<ApplicationUser> users = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();
    

    @FXML
    void Toggle(ActionEvent event) {
        if(btnToggle.getText().equals("Users")){
            btnToggle.setText("Transactions");
            setListView(usersList_To_stringList(users));
            btnDelete.arm();
        }else{
            btnToggle.setText("Users");
            setListView(transactionsList_To_stringList(transactions));
            btnDelete.disarm();
        }
    }

    @FXML
    void deleteUser(ActionEvent event) {
        if(btnToggle.getText().equals("Transactions")){
            ApplicationUser user = users.remove(lvTranOrUsers.getSelectionModel().getSelectedIndex());
            service.deleteUser(user.getUserid());
            setListView(usersList_To_stringList(users));
        }
    }

    @FXML
    void addClient(ActionEvent event) {
        BankClient client = new BankClient(tfName.getText(), tfEmail.getText(),
                dpDOB.getValue(),Long.parseLong(tfBalance.getText()), Long.parseLong(tfPhone.getText()), tfAddress.getText());
        try {
            if(service.addClient(client)){
                lblError.setText("");
                users = (ArrayList<ApplicationUser>) service.getAllUsers();
                if(btnToggle.getText().equals("Transactions")){
                    setListView(usersList_To_stringList(users));
                }
            }
            else{
                lblError.setText("Email is wrong or taken");
                tfEmail.clear();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logout(ActionEvent event) {
        utility.SceneSetter(stage, "/view/loginView.fxml", event,600,400);
    }

    @FXML
    void toProfile(ActionEvent event) {
        FXMLLoader loader= utility.SceneSetter(stage, "/view/profileView.fxml/", event, 481, 284);
        ProfileController controller = loader.getController();
        controller.init_data(userid);
    }

    public ArrayList<String> usersList_To_stringList(ArrayList<ApplicationUser> userList) {
        ListSetter listViewSetter = ()->{
            ArrayList<String> usersListString = new ArrayList<>();
            for(ApplicationUser u: userList){
                String AppuserString = u.toString();
                usersListString.add(AppuserString);
            }
            return usersListString;
        };
        return listViewSetter.setList();
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

    public void setListView(ArrayList<String> s) {
        lvTranOrUsers.getItems().clear();
        lvTranOrUsers.getItems().addAll(s);
    }

    public void init_data(Long userid) {
        this.userid=userid;
        utility = new Utility();
        service = new ManagementService();

        try {
            transactions = (ArrayList<Transaction>) service.getALLTransactions();
            users = (ArrayList<ApplicationUser>) service.getAllUsers();

            setListView(transactionsList_To_stringList(transactions));

            lvTranOrUsers.setCellFactory(cell -> {
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
            btnDelete.disarm();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}

