package cz.osu.kip.swi.Controllers;

import cz.osu.kip.swi.Methods.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrderDetailController {

    private int orderID;

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    @FXML
    private TextField firstName;

    @FXML
    private TextField emailAddress;

    @FXML
    private TextField brand;

    @FXML
    private TextField lastName;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField model;

    @FXML
    private TextField ico;

    @FXML
    private TextField address;

    @FXML
    private TextField spz;

    @FXML
    private TextField date;

    @FXML
    private TextField city;

    @FXML
    private TextField yearOfProd;

    @FXML
    private TextField time;

    @FXML
    private TextField zip;

    @FXML
    private TextField status;

    @FXML
    private TextArea description;

    @FXML
    private CheckBox tow;

    @FXML
    private Button closeButton;

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private Button previousOrder;

    @FXML
    private Button nextOrder;

    @FXML
    private Text currentOrderID;

    public void initialize() {
        currentOrderID.setText(String.valueOf(orderID));
        loadData();
    }

    private void loadData() {
        ResultSet result = Database.getOrderByID(orderID);

        try {
            while (result.next()) {
                firstName.setText(result.getString("firstname"));
                lastName.setText(result.getString("lastname"));
                emailAddress.setText(result.getString("emailaddress"));
                phoneNumber.setText(result.getString("phonenumber"));
                date.setText(result.getString("dateI"));
                time.setText(result.getString("timeI"));
                if (result.getString("ico") != null) ico.setText(result.getString("ico"));
                city.setText(result.getString("city"));
                address.setText(result.getString("address"));
                zip.setText(result.getString("zip"));
                brand.setText(result.getString("vehiclebrand"));
                model.setText(result.getString("vehiclemodel"));
                spz.setText(result.getString("spz"));
                yearOfProd.setText(result.getString("yearofproduction"));
                description.setText(result.getString("description"));
                if (result.getString("tow").equals("Ano")) {
                    tow.setSelected(true);
                } else {
                    tow.setSelected(false);
                }
                status.setText(result.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void loadPreviousOrder(ActionEvent event) {
        int firstID = 1;
        int lastID = Database.getOrderCount();
        if ((orderID - 1) >= firstID) {
            orderID -= 1;
        } else {
            orderID = lastID;
        }

        currentOrderID.setText(String.valueOf(orderID));
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.setTitle(String.format("Detail objednávky #%d", orderID));
        loadData();
    }

    @FXML
    private void loadNextOrder(ActionEvent event) {
        int firstID = 1;
        int lastID = Database.getOrderCount();
        if ((orderID + 1) <= lastID) {
            orderID += 1;
        } else {
            orderID = firstID;
        }

        currentOrderID.setText(String.valueOf(orderID));
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.setTitle(String.format("Detail objednávky #%d", orderID));
        loadData();
    }
}
