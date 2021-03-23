package cz.osu.kip.swi;

import cz.osu.kip.swi.Controllers.OrdersViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    // TODO Potvrzování změn
    //      - Model, Značka vyhledávání
    //      - ?

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scenes/ordersView.fxml"));
        Region root = loader.load();

        primaryStage.setTitle("Výpis objednávek");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Resources/img/icon.png")));
        OrdersViewController ordersView = loader.getController();
        ordersView.initialize(ordersView);


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
