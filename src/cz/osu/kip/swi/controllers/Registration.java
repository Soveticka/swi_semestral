package cz.osu.kip.swi.controllers;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import cz.osu.kip.swi.Database;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Registration implements Initializable {

    private Database db;

    public void callErrorMessage(String message) {
        double width = 200;
        double height = 200;
        Stage errorStage = new Stage();

        // - Vyhození error okna
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../error.fxml"));
            Region root = (Region) loader.load();
            errorStage.setTitle("Chybová hláška");
            errorStage.setScene(new Scene(root, width, height));
            errorStage.setResizable(false);

            ErrorController error = loader.<ErrorController>getController();
            error.setErrorText(message);

            errorStage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    private TextField firstName;

    @FXML
    private TextField emailAddress;

    @FXML
    private ComboBox<String> brand;

    @FXML
    private TextField lastName;

    @FXML
    private TextField phoneNumber;

    @FXML
    private ComboBox<String> model;

    @FXML
    private TextField ico;

    @FXML
    private TextField address;

    @FXML
    private DatePicker date;

    @FXML
    private TextField spz;

    @FXML
    private ComboBox<String> time;

    @FXML
    private TextField zip;

    @FXML
    private TextField yearOfProd;

    @FXML
    private TextArea description;

    @FXML
    private CheckBox tow;

    @FXML
    private TextField city;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new Database();

        brand.getItems().removeAll(brand.getItems());
        ResultSet result = db.selectData("SELECT brand FROM vehicleBrand");
        try {
            while (result.next()) {
                brand.getItems().add(result.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        model.getItems().removeAll(model.getItems());

        time.getItems().removeAll(time.getItems());
        time.getItems().addAll("8:00", "8:30", "9:00", "9:30");
        time.getSelectionModel().select("8:00");
    }


    @FXML
    void actionContinue(ActionEvent event) {
        //Check if mandatory is not null
        if (!firstName.getText().equals("") && !lastName.getText().equals("") && !emailAddress.getText().equals("") && !phoneNumber.getText().equals("") && !address.getText().equals("") && !city.getText().equals("") && !zip.getText().equals("") && !brand.getSelectionModel().isEmpty() && !model.getSelectionModel().isEmpty() && !spz.getText().equals("") && !yearOfProd.getText().equals("")) {
            if (!time.getSelectionModel().isEmpty() && date.getValue() != null) {
                String towBool = null;
                String query = null;
                if(tow.isSelected()){
                    towBool ="Ano";
                }else{
                    towBool = "Ne";
                }
                if (!ico.getText().equals("")) {
                    System.out.println("tu");
                    query = String.format("INSERT INTO orders(firstname, lastname, emailAddress, phoneNumber, dateI, timeI, ico, city, zip, vehiclebrand, vehiclemodel, spz, yearOfProduction, tow, description) VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%d','%s','%s')", firstName.getText(), lastName.getText(), emailAddress.getText(), phoneNumber.getText(), date.getValue(), time.getValue(), ico.getText(), city.getText(), zip.getText(), brand.getValue(), model.getValue(), spz.getText(), Integer.parseInt(yearOfProd.getText()), towBool, description.getText());
                }else{
                    System.out.println("nebo tu");
                    query = String.format("INSERT INTO orders(firstname, lastname, emailAddress, phoneNumber, dateI, timeI, city, zip, vehiclebrand, vehiclemodel, spz, yearOfProduction, tow, description)  VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%d','%s','%s')", firstName.getText(), lastName.getText(), emailAddress.getText(), phoneNumber.getText(), date.getValue(), time.getValue(), city.getText(), zip.getText(),brand.getValue(), model.getValue(), spz.getText(), Integer.parseInt(yearOfProd.getText()), towBool, description.getText());
                }

                db.insertData(query);
            } else {
                callErrorMessage("Je nutno vybrat datum a čas");
            }
        } else {
            callErrorMessage("Je nutno vyplnit všechny povinné údaje (*)");
        }
    }

    @FXML
    void brandSelection(ActionEvent event) {
        ResultSet result = db.selectData(String.format("SELECT model FROM vehicleModel WHERE vehicleBrand_brand='%s'", brand.getValue()));
        model.getItems().removeAll(model.getItems());
        try {
            while (result.next()) {
                model.getItems().add(result.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        model.getSelectionModel().select(0);
    }

    @FXML
    void timeSelection(ActionEvent event) {

    }


}
