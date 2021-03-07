package cz.osu.kip.swi.Controllers;

import cz.osu.kip.swi.Methods.OrderMethods;
import cz.osu.kip.swi.Methods.QueryModes;
import cz.osu.kip.swi.Methods.Validators;
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
import javafx.scene.control.*;

import cz.osu.kip.swi.Methods.Database;
import javafx.stage.Stage;

public class OrderCreateController implements Initializable {

    private OrdersViewController parentController;

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

    @FXML
    private Button createOrder;

    public void setParentController(OrdersViewController parentController){
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        brand.getItems().removeAll(brand.getItems());
        ResultSet result = Database.getVehicleBrands();
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
                                                    query = String.format("INSERT INTO orders(firstname, lastname, emailAddress, phoneNumber, dateI, timeI, ico, city, address, zip, vehiclebrand, vehiclemodel, spz, yearOfProduction, tow, description) VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%d','%s','%s')", firstName.getText(), lastName.getText(), emailAddress.getText(), phoneNumber.getText(), date.getValue(), time.getValue(), ico.getText(), city.getText(), address.getText(), zip.getText(), brand.getValue(), model.getValue(), spz.getText(), Integer.parseInt(yearOfProd.getText()), towBool, description.getText());
                                                } else {
                                                    query = String.format("INSERT INTO orders(firstname, lastname, emailAddress, phoneNumber, dateI, timeI, city, address, zip, vehiclebrand, vehiclemodel, spz, yearOfProduction, tow, description)  VALUES('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%d','%s','%s')", firstName.getText(), lastName.getText(), emailAddress.getText(), phoneNumber.getText(), date.getValue(), time.getValue(), city.getText(), address.getText(), zip.getText(), brand.getValue(), model.getValue(), spz.getText(), Integer.parseInt(yearOfProd.getText()), towBool, description.getText());
                                                }

                                                if (Database.insertData(query)) {
                                                    parentController.loadFromDatabase(QueryModes.EVERYTHING);
                                                    OrderMethods.callErrorMessage("Objednávka byla úspěšně vytvořena", "Informace");
                                                    Stage stage = (Stage) createOrder.getScene().getWindow();
                                                    stage.close();
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
            OrderMethods.callErrorMessage(errorMessage, "Chybová hláška");
        }
    }

    @FXML
    void brandSelection(ActionEvent event) {
        OrderMethods.generateModelList(brand, model);
    }

    @FXML
    void timeSelection(ActionEvent event) {

    }

    @FXML
    public void dateSelection(ActionEvent actionEvent) {
        OrderMethods.generateTimesByDate(date.getValue().toString(), time);
    }
}
