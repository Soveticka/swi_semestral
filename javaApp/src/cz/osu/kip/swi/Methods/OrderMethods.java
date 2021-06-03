package cz.osu.kip.swi.Methods;

import cz.osu.kip.swi.Controllers.ErrorController;
import cz.osu.kip.swi.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderMethods {
    public static void generateModelList(ComboBox<String> brand, ComboBox<String> model) {
        ResultSet result = Database.getVehicleModelsByBrand(brand.getValue());
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

    public static void generateTimesByDate(String date, ComboBox<String> time) {
        ResultSet result = Database.getUsedTimesAtDate(date);
        time.getItems().removeAll(time.getItems());
        time.getItems().addAll("8:00", "10:00", "12:00", "14:00", "16:00");

        try {
            result.last();
            int size = result.getRow();
            result.beforeFirst();
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

    public static LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    public static void callErrorMessage(String message, String title) {
        Stage errorStage = new Stage();

        // - Vyhození error okna
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Scenes/error.fxml"));
            Region root = loader.load();
            errorStage.setTitle(title);
            errorStage.setScene(new Scene(root));
            errorStage.setResizable(false);
            errorStage.initModality(Modality.APPLICATION_MODAL);
            errorStage.getIcons().add(new Image(Main.class.getResourceAsStream("Resources/img/icon.png")));

            ErrorController error = loader.getController();
            error.setErrorText(message);

            errorStage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static boolean inputChecks(TextField firstName, TextField lastName, TextField emailAddress, TextField phoneNumber, TextField address, TextField city, TextField zip, ComboBox<String> brand, ComboBox<String> model, TextField spz, TextField yearOfProd, ComboBox<String> time, DatePicker date) {
        String style = "-fx-background-color: #ffdbc7; -fx-border-color: lightgray; -fx-background-radius: 5; -fx-border-radius: 5";
        String errorMessage;
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
                                                return true;
                                            } else {
                                                errorMessage = "Je nutné vybrat jiný datum a čas";
                                                time.setStyle(style);
                                            }
                                        } else {
                                            errorMessage = "Neplatný rok výroby";
                                            yearOfProd.setStyle(style);
                                        }
                                    } else {
                                        errorMessage = "SPZ je neplatná";
                                        spz.setStyle(style);
                                    }
                                } else {
                                    errorMessage = "PSČ je krátké nebo příliš dlouhé";
                                    zip.setStyle(style);
                                }
                            } else {
                                errorMessage = "Město je neplatné";
                                city.setStyle(style);
                            }
                        } else {
                            errorMessage = "Adresa je neplatná";
                            address.setStyle(style);
                        }
                    } else {
                        errorMessage = "Neplatné telefonní číslo";
                        phoneNumber.setStyle(style);
                    }
                } else {
                    errorMessage = "Neplatný email";
                    emailAddress.setStyle(style);
                }
            } else {
                errorMessage = "Je nutno vybrat datum a čas";
                date.setStyle("-fx-border-color: #ffdbc7");
                time.setStyle(style);
            }
        } else {
            errorMessage = "Je nutno vyplnit všechny povinné údaje (*)";
            firstName.setStyle(style);
            lastName.setStyle(style);
            time.setStyle(style);
            yearOfProd.setStyle(style);
            spz.setStyle(style);
            zip.setStyle(style);
            city.setStyle(style);
            address.setStyle(style);
            phoneNumber.setStyle(style);
            emailAddress.setStyle(style);
            date.setStyle("-fx-border-color: red");
            time.setStyle(style);
            brand.setStyle(style);
            model.setStyle(style);
        }

        callErrorMessage(errorMessage, "Chybová hláška");
        return false;
    }
}
