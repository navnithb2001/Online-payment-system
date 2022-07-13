package Utilty;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

interface UtilityFunctions{
    public FXMLLoader SceneSetter(Stage s,String path, ActionEvent event,int width,int height);
    public Connection getConnection();
    public ResultSet sendQuery(String query);
    public int sendUpdateQuery(String query);
    public boolean isEmailValid(String email);
}

public class Utility implements UtilityFunctions{

    @Override
    public FXMLLoader SceneSetter(Stage s,String path, ActionEvent event,int width,int height) {
        Node node = (Node) event.getSource();
        s = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource(path));
            AnchorPane root = loader.load();
            Scene main = new Scene(root, width,height);
            s.setScene(main);
            s.show();
            return loader;
        } catch (IOException e) {
        e.printStackTrace();
        }
        return null;
    }

    @Override
    public Connection getConnection() {
        Connection conn;
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "rockon123$");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ResultSet sendQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }  
    }

    @Override
    public int sendUpdateQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            int rs = st.executeUpdate(query);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }  
    }

    @Override
    public boolean isEmailValid(String email){
        Pattern pat = Pattern.compile("^(.+)@(.+)$");
        Matcher mat = pat.matcher(email);
        if(mat.matches()){
            return true;
        }
        return false;
    }

    
}
