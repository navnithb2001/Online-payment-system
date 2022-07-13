package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Entities.ApplicationUser;
import Entities.Roles;
import Services.CommonService;
import Utilty.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable{

    private Stage stage;
    private Utility utility;

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblError;

    private CommonService service;

    @FXML
    void login(ActionEvent event){
        System.out.println("login");
        ApplicationUser user = service.login(tfUsername.getText(), tfPassword.getText());
        if(user == null){
            lblError.setText("Wrong password or username");
            tfPassword.clear();
            tfUsername.clear();
        }
        if(user != null && user.getUser_role() == Roles.ADMIN){
            lblError.setText("");
           FXMLLoader loader= utility.SceneSetter(stage, "/view/adminView.fxml", event,930,430);
           adminViewController controller = loader.getController();
           controller.init_data(user.getUserid());
        }
        if(user != null && user.getUser_role() == Roles.CLIENT){
            lblError.setText("");
            FXMLLoader loader= utility.SceneSetter(stage, "/view/clientView.fxml/", event,930,430);
            clientViewController controller = loader.getController();
            controller.init_data(user.getUserid());
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        utility = new Utility();
        service = new CommonService();
    }

}
