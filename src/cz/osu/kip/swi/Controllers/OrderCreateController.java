package cz.osu.kip.swi.Controllers;

import cz.osu.kip.swi.Methods.OrderMethods;
import cz.osu.kip.swi.Methods.QueryModes;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import cz.osu.kip.swi.Methods.Database;
import javafx.scene.input.MouseEvent;
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

    public void setParentController(OrdersViewController parentController) {
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
    public void actionContinue(ActionEvent event) {
        if (OrderMethods.inputChecks(firstName, lastName, emailAddress, phoneNumber, address, city, zip, brand, model, spz, yearOfProd, time, date)) {
            String errorMessage;
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
                OrderMethods.callErrorMessage(errorMessage, "Chybová hláška");
            }
        }
    }

    @FXML
    public void brandSelection(ActionEvent actionEvent) {
        OrderMethods.generateModelList(brand, model);
    }

    @FXML
    public void dateSelection(ActionEvent actionEvent) {
        OrderMethods.generateTimesByDate(date.getValue().toString(), time);
    }


    public void resetColor(MouseEvent mouseEvent) {
        Node toReset = (Node) mouseEvent.getSource();
        toReset.setStyle(null);
    }
}
