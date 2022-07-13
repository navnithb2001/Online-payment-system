package Controller;

import Entities.ApplicationUser;
import Entities.Roles;
import Services.ClientService;
import Utilty.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProfileController{

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField tfOldpasswd;

    @FXML
    private PasswordField tfNewpasswd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblUsernameError;

    @FXML
    private Label lblPasswdError;

    private Utility utility;
    private Long userid;
    private ClientService service;
    private ApplicationUser user;
    private Stage stage;

    @FXML
    void back(ActionEvent event) {
        if(user != null && user.getUser_role() == Roles.ADMIN){
            FXMLLoader loader= utility.SceneSetter(stage, "/view/adminView.fxml", event,930,430);
            adminViewController controller = loader.getController();
            controller.init_data(user.getUserid());
         }
         if(user != null && user.getUser_role() == Roles.CLIENT){
             FXMLLoader loader= utility.SceneSetter(stage, "/view/clientView.fxml/", event,930,430);
             clientViewController controller = loader.getController();
             controller.init_data(user.getUserid());
         }
    }

    @FXML
    public void update(ActionEvent event) throws IllegalAccessException{
        boolean u=false,p=false;
        if(!service.updateUsername(userid, tfUsername.getText())){
            u=false;
            lblUsernameError.setText("username is already taken");
        }
        else{
            u=true;
        }
        
        if(!(tfOldpasswd.getText().isEmpty() || tfNewpasswd.getText().isEmpty() || 
                    tfOldpasswd.getText().isBlank() || tfNewpasswd.getText().isEmpty())){
            
            if(!service.updatePassword(userid, tfOldpasswd.getText(), tfNewpasswd.getText())){
                p = false;
                lblPasswdError.setText("Wrong password");
            }
            else{
                p=true;
            }
        }
        else{
            lblPasswdError.setText("");
            p = true;
        }

        if(u && p){
            back(event);
        }
    }
    

    public void init_data(Long userid) {
        this.userid=userid;
        System.out.println("in Profile userid = "+userid);
        utility = new Utility();
        service = new ClientService();

        try {
            user = service.getUserByid(userid);
            tfUsername.setText(user.getUsername());

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}