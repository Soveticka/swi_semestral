package cz.osu.kip.swi.Methods;

import cz.osu.kip.swi.Controllers.ErrorController;
import cz.osu.kip.swi.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
}
