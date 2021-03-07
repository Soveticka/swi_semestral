package cz.osu.kip.swi.Controllers;

import cz.osu.kip.swi.Methods.OrderMethods;
import cz.osu.kip.swi.Methods.Database;
import cz.osu.kip.swi.Methods.QueryModes;
import cz.osu.kip.swi.Methods.Validators;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderEditController {

    private int orderID;
    private String oldDate;
    private String oldTime;

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
    private TextField spz;

    @FXML
    private DatePicker date;

    @FXML
    private TextField city;

    @FXML
    private TextField yearOfProd;

    @FXML
    private ComboBox<String> time;

    @FXML
    private TextField zip;

    @FXML
    private ComboBox<String> status;

    @FXML
    private TextArea description;

    @FXML
    private CheckBox tow;

    @FXML
    private Button updateButton;

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setParentController(OrdersViewController parentController) {
        this.parentController = parentController;
    }


    public void initialize() {
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
        time.getItems().addAll("8:00", "10:00", "12:00", "14:00", "16:00");

        status.getItems().removeAll(status.getItems());
        status.getItems().addAll("Objednáno", "Rozpracováno", "Dokončeno", "Zrušeno");
    }

    public void loadData() {
        OrderMethods.generateModelList(brand, model);
        ResultSet result = Database.getOrderByID(orderID);
        try {
            while (result.next()) {
                oldDate = result.getString("dateI");
                oldTime = result.getString("timeI");
                date.setValue(OrderMethods.LOCAL_DATE(result.getString("dateI")));
                getTimes();
                firstName.setText(result.getString("firstname"));
                lastName.setText(result.getString("lastname"));
                emailAddress.setText(result.getString("emailaddress"));
                phoneNumber.setText(result.getString("phonenumber"));

                time.getSelectionModel().select(result.getString("timeI"));
                if (result.getString("ico") != null) ico.setText(result.getString("ico"));
                city.setText(result.getString("city"));
                address.setText(result.getString("address"));
                zip.setText(result.getString("zip"));
                brand.getSelectionModel().select(result.getString("vehiclebrand"));
                OrderMethods.generateModelList(brand, model);
                model.getSelectionModel().select(result.getString("vehiclemodel"));
                spz.setText(result.getString("spz"));
                yearOfProd.setText(result.getString("yearofproduction"));
                description.setText(result.getString("description"));
                if (result.getString("tow").equals("Ano")) {
                    tow.setSelected(true);
                } else {
                    tow.setSelected(false);
                }
                status.getSelectionModel().select(result.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    @FXML
    void actionUpdate(ActionEvent event) {
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
                                                query = String.format(
                                                        "UPDATE orders SET firstname='%s', lastname='%s', emailaddress='%s', phonenumber='%s', dateI='%s', timeI='%s', ico='%s', city='%s',address='%s',zip='%s',vehiclebrand='%s',vehiclemodel='%s',spz='%s',yearofproduction='%d',tow='%s',description='%s',status='%s' WHERE id='%d'",
                                                        firstName.getText(),
                                                        lastName.getText(),
                                                        emailAddress.getText(),
                                                        phoneNumber.getText(),
                                                        date.getValue(),
                                                        time.getValue(),
                                                        ico.getText(),
                                                        city.getText(),
                                                        address.getText(),
                                                        zip.getText(),
                                                        brand.getValue(),
                                                        model.getValue(),
                                                        spz.getText(),
                                                        Integer.parseInt(yearOfProd.getText()),
                                                        towBool,
                                                        description.getText(),
                                                        status.getValue(),
                                                        orderID
                                                );
                                                if (Database.insertData(query)) {
                                                    parentController.loadFromDatabase(QueryModes.EVERYTHING);
                                                    Stage stage = (Stage) updateButton.getScene().getWindow();
                                                    stage.close();
                                                    OrderMethods.callErrorMessage("Objednávka byla úspěšně aktualizována", "Informace");
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
    void dateSelection(ActionEvent event) {
        time.getItems().removeAll(time.getItems());
        time.getItems().addAll("8:00", "10:00", "12:00", "14:00", "16:00");
        getTimes();
    }

    private void getTimes() {
        try {
            if (date.getValue().toString().equals(oldDate)) {
                ResultSet result = Database.getUsedTimesAtDate(date.getValue().toString());
                while (result.next()) {
                    if (!result.getString(1).equals(oldTime)) {
                        time.getItems().remove(result.getString(1));
                    }
                }
            } else {
                OrderMethods.generateTimesByDate(date.getValue().toString(), time);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    void timeSelection(ActionEvent event) {
    }


}
