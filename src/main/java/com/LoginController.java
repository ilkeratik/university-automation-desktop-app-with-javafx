package com;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import com.Student.Student;
import com.Teacher.Teacher;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public ToggleGroup keepLoggedIn;
    @FXML
    private TextField signName;
    @FXML
    private TextField signEmail;
    @FXML
    private TextField signNo;
    @FXML
    private TextField signPassword;
    @FXML
    private ToggleGroup userType;
    @FXML
    private AnchorPane loginWrap;
    @FXML
    private Label signUpState;
    @FXML
    private BorderPane loginWrapper;
    @FXML
    private Label loginState;
    @FXML
    private TextField loginPassword;
    @FXML
    private TextField loginEmail;
    private Connection con;
    private static LoginController instance;
    private static User user;
    private boolean isStudent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            con = DB_Connector.getInstance().getConnection();
            instance = this;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static LoginController getInstance(){
        if (instance == null) {
            instance = new LoginController();
        }
        return instance;
    }
    public User getUser(){
        return user;
    }

    public void login(ActionEvent event) throws IOException {

        if(loginEmail.getText().equals("") || loginPassword.getText().equals("") ){
            loginState.setText("Lutfen tum alanlari doldurunuz.");
            return;
        }
        isStudent = userType.getToggles().get(0).isSelected() ;//checking if student selected
        try {
            PreparedStatement stmt;
            if(isStudent){
                stmt= con.prepareStatement("select id, password from university.student where email=?",ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
            }
            else{
                stmt= con.prepareStatement("select id, password from university.teacher where email=?",ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
            }
            stmt.setString(1,loginEmail.getText());
            ResultSet rs = stmt.executeQuery();
            if(rs.first()){//checking if user exits
                if (loginPassword.getText().equals(rs.getString(2))){//password check
                    loginState.setText("Giris basarili");
                    if (isStudent){ //switch to student mang. window
                        System.out.println("Ogrenci Ekranina geciliyor");
                        user = Student.getInstance(rs.getString(1));
                        SceneBuilder.getInstance().sceneSwitcher(user.getScene());
                    }else{//switch to teacher mang. window
                        System.out.println("Ogretmen ekranina geciliyor");
                        user = Teacher.getInstance(rs.getString(1));
                        SceneBuilder.getInstance().sceneSwitcher(user.getScene());
                    }
                }
                else{
                    loginState.setText("Sifre yanlis!");
                }
            }
            else{
                loginState.setText("Boyle bir email kayitli degil!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void signUp(ActionEvent event) {
        try {
            PreparedStatement stmt;
            boolean studentSelected = userType.getToggles().get(0).isSelected() ;//checking if student selected
            if(studentSelected){
                stmt = con.prepareStatement("insert into university.student(id,name,email,password) values (?,?,?,?)");
            }
            else{
                stmt = con.prepareStatement("insert into university.teacher(id,name,email,password) values (?,?,?,?)");
            }
            if(signNo.getText().equals("") || signName.getText().equals("")|| signEmail.getText().equals("") ||
            signPassword.getText().equals("")){//all fields should be filled
                signUpState.setText("Tum alanlari doldurunuz!");
                return;
            }
            stmt.setString(1, signNo.getText());
            stmt.setString(2, signName.getText());
            stmt.setString(3, signEmail.getText());
            stmt.setString(4, signPassword.getText());
            stmt.executeUpdate();
            signUpState.setText("Kayit Basarili..");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void goSignUp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/signUp.fxml"));
        loginWrapper.getScene().setRoot(root);
    }

    public void goSignIn(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
        loginWrap.getScene().setRoot(root);
    }
}
