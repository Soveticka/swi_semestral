package cz.osu.kip.swi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        double width = screenSize.getWidth()/2;
        double height = screenSize.getHeight()/2;

        Parent root = FXMLLoader.load(getClass().getResource("registration.fxml"));

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, width, height));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

//        Database db = new Database();
//
//        db.insertData("Soveticka","Sovetickovy");
//        db.insertData("ZuZeekHD","ZuZeekHDckova");
//
//        db.selectData("SELECT * FROM testTable");
    }
}
