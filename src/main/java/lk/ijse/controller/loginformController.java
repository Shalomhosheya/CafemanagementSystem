package lk.ijse.controller;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class loginformController {
    public TextField fp_username;
    public TextField fp_answer;
    @FXML
    private AnchorPane MainForm;

    @FXML
    private Button fp_back;

    @FXML
    private TextField fp_proceedBtn;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private TextField np_ConfirmPassword;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePassBtn;

    @FXML
    private Label np_newPassForm;

    @FXML
    private TextField np_newPassword;

    @FXML
    private Hyperlink si_forgetPass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private Button si_loginBtn1;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private AnchorPane si_loginForm11;

    @FXML
    private TextField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_AlreadyHaveBtn;

    @FXML
    private Button side_CreateBtn;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private TextField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private TextField su_username;


    private Connection connect;
    private PreparedStatement prepare;

    private Alert alert;
    private ResultSet result;

    private final String[] questionList = {"What is your favorite Color?", "What is your favorite food?", "what is your birth date?"};

    public void  switchForgotPass() {
        fp_questionForm.setVisible(true);
        si_loginForm.setVisible(false);

        forgotPassQuestionList();
    }
    public void proceedBtn() {

        if (fp_username.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem() == null
                || fp_answer.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            String selectData = "SELECT username, question, answer FROM employee WHERE username = ? AND question = ? AND answer = ?";
            connect = DbConnection.connectDB();

            try {

                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, fp_username.getText());
                prepare.setString(2, (String) fp_question.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_answer.getText());

                result = prepare.executeQuery();



                if (result.next()) {
                    np_newPassForm.setVisible(true);
                    fp_questionForm.setVisible(false);
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Information");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
    public void forgotPassQuestionList() {

        List<String> listQ = new ArrayList<>();

        for (String data : questionList) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        fp_question.setItems(listData);

    }
    public void changePassBtn() {

        if (np_newPassword.getText().isEmpty() || np_ConfirmPassword.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {

            if (np_newPassword.getText().equals(np_ConfirmPassword.getText())) {
                String getDate = "SELECT date FROM employee WHERE username = '"
                        + fp_username.getText() + "'";

                connect = DbConnection.connectDB();

                try {

                    prepare = connect.prepareStatement(getDate);
                    result = prepare.executeQuery();

                    String date = "";
                    if (result.next()) {
                        date = result.getString("date");
                    }

                    String updatePass = "UPDATE employee SET password = '"
                            + np_newPassword.getText() + "', question = '"
                            + fp_question.getSelectionModel().getSelectedItem() + "', answer = '"
                            + fp_answer.getText() + "', date = '"
                            + date + "' WHERE username = '"
                            + fp_username.getText() + "'";

                    prepare = connect.prepareStatement(updatePass);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully changed Password!");
                    alert.showAndWait();

                    si_loginForm.setVisible(true);
                    np_newPassForm.setVisible(false);

                    // TO CLEAR FIELDS
                    np_ConfirmPassword.setText("");
                    np_newPassword.setText("");
                    fp_question.getSelectionModel().clearSelection();
                    fp_answer.setText("");
                    fp_username.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Not match");
                alert.showAndWait();
            }
        }
    }

    public void backToLoginForm(){
        si_loginForm.setVisible(true);
        fp_questionForm.setVisible(false);
    }

    public void backToQuestionForm(){
        fp_questionForm.setVisible(true);
        np_newPassForm.setVisible(false);
    }

    public void loginBtn() {

        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username/Password");
            alert.showAndWait();
        } else {

            String selctData = "SELECT username, password FROM employee WHERE username = ? and password = ?";

            connect = DbConnection.connectDB();
            try {

                prepare = connect.prepareStatement(selctData);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());

                result = prepare.executeQuery();
                // IF SUCCESSFULLY LOGIN, THEN PROCEED TO ANOTHER FORM WHICH IS OUR MAIN FORM
                if (result.next()) {
                    // TO GET THE USERNAME THAT USER USED
                   data.username = si_username.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();

                    // LINK YOUR MAIN FORM
                    Parent root = FXMLLoader.load(this.getClass().getResource("/view/mainForm.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setTitle("Cafe Shop Management System");
                    stage.setMinWidth(1100);
                    stage.setMinHeight(600);

                    stage.setScene(scene);
                    stage.show();

                    si_loginBtn.getScene().getWindow().hide();

                } else { // IF NOT, THEN THE ERROR MESSAGE WILL APPEAR
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void regBtn() {
        if (!this.su_username.getText().isEmpty() && !this.su_password.getText().isEmpty() && this.su_question.getSelectionModel().getSelectedItem() != null && !this.su_answer.getText().isEmpty()) {
            String regData = "INSERT INTO employee (username, password, question, answer, date) VALUES(?,?,?,?,?)";
            this.connect = DbConnection.connectDB();

            try {
                String checkUsername = "SELECT username FROM employee WHERE username = '" + this.su_username.getText() + "'";
                this.prepare = this.connect.prepareStatement(checkUsername);
                this.result = this.prepare.executeQuery();
                if (this.result.next()) {
                    this.alert = new Alert(Alert.AlertType.ERROR);
                    this.alert.setTitle("Error Message");
                    this.alert.setHeaderText(null);
                    this.alert.setContentText(this.su_username.getText() + " is already taken");
                    this.alert.showAndWait();
                } else if (this.su_password.getText().length() < 8) {
                    this.alert = new Alert(Alert.AlertType.ERROR);
                    this.alert.setTitle("Error Message");
                    this.alert.setHeaderText(null);
                    this.alert.setContentText("Invalid Password, atleast 8 characters are needed");
                    this.alert.showAndWait();
                } else {
                    this.prepare = this.connect.prepareStatement(regData);
                    this.prepare.setString(1, this.su_username.getText());
                    this.prepare.setString(2, this.su_password.getText());
                    this.prepare.setString(3, (String)this.su_question.getSelectionModel().getSelectedItem());
                    this.prepare.setString(4, this.su_answer.getText());
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    this.prepare.setString(5, String.valueOf(sqlDate));
                    this.prepare.executeUpdate();
                    this.alert = new Alert(Alert.AlertType.INFORMATION);
                    this.alert.setTitle("Information Message");
                    this.alert.setHeaderText(null);
                    this.alert.setContentText("Successfully registered Account!");
                    this.alert.showAndWait();
                    this.su_username.setText("");
                    this.su_password.setText("");
                    this.su_question.getSelectionModel().clearSelection();
                    this.su_answer.setText("");
                    TranslateTransition slider = new TranslateTransition();
                    slider.setNode(this.side_form);
                    slider.setToX(0.0);
                    slider.setDuration(Duration.seconds(0.5));
                    slider.setOnFinished((e) -> {
                        this.side_AlreadyHaveBtn.setVisible(false);
                        this.side_CreateBtn.setVisible(true);
                    });
                    slider.play();
                }
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        } else {
            this.alert = new Alert(Alert.AlertType.ERROR);
            this.alert.setTitle("Error Message");
            this.alert.setHeaderText(null);
            this.alert.setContentText("Please fill all blank fields");
            this.alert.showAndWait();
        }

    }


    public void regLquestionList() {
        List<String> listQ = new ArrayList<>();

        Collections.addAll(listQ, questionList);

        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_question.setItems(listData);
    }
    public void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();
        if (event.getSource() == this.side_CreateBtn) {
            slider.setNode(this.side_form);
            slider.setToX(300.0);
            slider.setDuration(Duration.seconds(0.5));
            slider.setOnFinished((e) -> {
                this.side_AlreadyHaveBtn.setVisible(true);
                this.side_CreateBtn.setVisible(false);
                this.su_question.setVisible(true);
                this.si_loginForm.setVisible(true);
                this.su_password.setVisible(true);
                this.regLquestionList();
                this.fp_questionForm.setVisible(false);
                this.si_loginForm.setVisible(true);
                this.np_newPassForm.setVisible(false);
            });
            slider.play();
        } else if (event.getSource() == this.side_AlreadyHaveBtn) {
            slider.setNode(this.side_form);
            slider.setToX(0.0);
            slider.setDuration(Duration.seconds(0.5));
            slider.setOnFinished((e) -> {
                this.side_AlreadyHaveBtn.setVisible(false);
                this.side_CreateBtn.setVisible(true);
                this.su_question.setVisible(false);
                this.si_loginForm.setVisible(true);
                this.su_password.setVisible(false);
                this.fp_questionForm.setVisible(false);
                this.si_loginForm.setVisible(true);
                this.np_newPassForm.setVisible(false);
            });

        slider.play();
        }

    }


}
