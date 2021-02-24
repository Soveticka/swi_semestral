package cz.osu.kip.swi.controllers;

import cz.osu.kip.swi.methods.Validators;
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

    private void callErrorMessage(String message, String title) {
        double width = 200;
        double height = 200;
        Stage errorStage = new Stage();

        // - Vyhození error okna
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../error.fxml"));
            Region root = (Region) loader.load();
            errorStage.setTitle(title);
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
    }


    @FXML
    void actionContinue(ActionEvent event) {
        String errorMessage = "";
        //Check if mandatory is not null
        // TODO:
        //      - MAX 2 customers per day
        //      - MAX 1 customer per time
        //      If day has over 2 orders, the day is blocked. If only one time is taken, dont show the taken time and the next one.
        if (!firstName.getText().equals("") && !lastName.getText().equals("") && !emailAddress.getText().equals("") && !phoneNumber.getText().equals("") && !address.getText().equals("") && !city.getText().equals("") && !zip.getText().equals("") && !brand.getSelectionModel().isEmpty() && !model.getSelectionModel().isEmpty() && !spz.getText().equals("") && !yearOfProd.getText().equals("")) {
            if (!time.getSelectionModel().isEmpty() && date.getValue() != null) {
                if (Validators.isValidEmail(emailAddress.getText())) {
                    if (Validators.isValidPhoneNumber(phoneNumber.getText())) {
                        if (Validators.isValidAddress(address.getText())) {
                            if (Validators.isValidCity(city.getText())) {
                                if (Validators.isValidZIP(zip.getText())) {
                                    if (Validators.isValidSPZ(spz.getText())) {
                                        if (Validators.isValidYearOfProd(yearOfProd.getText())) {
                                            if (!time.getValue().equals("Všechny časy jsou zabrány")) {
                                                String towBool = null;
                                                String query = null;
                                                if (tow.isSelected()) {
                                                    towBool = "Ano";
                                                } else {
                                                    towBool = "Ne";
                                                }
                                                if (!ico.getText().equals("")) {
                                                    query = String.format("INSERT INTO orders(firstname, lastname, emailAddress, phoneNumber, dateI, timeI, ico, city, zip, vehiclebrand, vehiclemodel, spz, yearOfProduction, tow, description) VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%d','%s','%s')", firstName.getText(), lastName.getText(), emailAddress.getText(), phoneNumber.getText(), date.getValue(), time.getValue(), ico.getText(), city.getText(), zip.getText(), brand.getValue(), model.getValue(), spz.getText(), Integer.parseInt(yearOfProd.getText()), towBool, description.getText());
                                                } else {
                                                    query = String.format("INSERT INTO orders(firstname, lastname, emailAddress, phoneNumber, dateI, timeI, city, zip, vehiclebrand, vehiclemodel, spz, yearOfProduction, tow, description)  VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%d','%s','%s')", firstName.getText(), lastName.getText(), emailAddress.getText(), phoneNumber.getText(), date.getValue(), time.getValue(), city.getText(), zip.getText(), brand.getValue(), model.getValue(), spz.getText(), Integer.parseInt(yearOfProd.getText()), towBool, description.getText());
                                                }

                                                if (db.insertData(query)) {
                                                    callErrorMessage("Objednávka byla úspěšně vytvořena", "Informace");
                                                } else {
                                                    errorMessage = "Při vytváření objednávky nastala chyba";
                                                }
                                            } else {
                                                errorMessage = "Je nutné vybrat jiný datum a čas";
                                            }
                                        } else {
                                            errorMessage = "Neplatný rok výroby";
                                        }
                                    } else {
                                        errorMessage = "SPZ je neplatná";
                                    }
                                } else {
                                    errorMessage = "PSČ je krátké nebo příliš dlouhé";
                                }
                            } else {
                                errorMessage = "Město je neplatné";
                            }
                        } else {
                            errorMessage = "Adresa je neplatná";
                        }
                    } else {
                        errorMessage = "Neplatné telefonní číslo";
                    }
                } else {
                    errorMessage = "Neplatný email";
                }
            } else {
                errorMessage = "Je nutno vybrat datum a čas";

            }
        } else {
            errorMessage = "Je nutno vyplnit všechny povinné údaje (*)";
        }

        if (!errorMessage.equals("")) {
            callErrorMessage(errorMessage, "Chybová hláška");
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

    @FXML
    public void dateSelection(ActionEvent actionEvent) {
        ResultSet result = db.selectData(String.format("SELECT timeI FROM orders WHERE dateI='%s'", date.getValue()));
        time.getItems().removeAll(time.getItems());
        time.getItems().addAll("8:00", "10:00", "12:00", "14:00", "16:00");

        try {
            result.last();
            int size = result.getRow();
            result.beforeFirst();
            System.out.println(size);
            if (size < 2) {
                while (result.next()) {
                    time.getItems().remove(result.getString(1));
                }
            } else {
                time.getItems().removeAll(time.getItems());
                time.getItems().add("Všechny časy jsou zabrány");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
