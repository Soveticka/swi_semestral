package cz.osu.kip.swi.Objects;

import cz.osu.kip.swi.Controllers.OrderDetailController;
import cz.osu.kip.swi.Controllers.OrderEditController;
import cz.osu.kip.swi.Controllers.OrdersViewController;
import cz.osu.kip.swi.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class Order {
    private int orderID;
    private String name;
    private String phoneNumber;
    private String date;
    private String time;
    private String brand;
    private String model;
    private String status;
    private final Button btnShow;
    private final Button btnEdit;
    private final OrdersViewController controller;


    public Order(int orderID, String name, String phoneNumber, String date, String time, String brand, String model, String status, OrdersViewController controller) {
        this.orderID = orderID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.time = time;
        this.brand = brand;
        this.model = model;
        this.status = status;
        this.btnShow = createButtonShow();
        this.btnEdit = createButtonEdit();
        this.controller = controller;
    }

    private Button createButtonEdit() {
        Button btn = new Button("Upravit");
        {
            btn.setOnAction((ActionEvent event) -> {
                Stage orderEditStage = new Stage();
                // - Vyhození error okna
                try {
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("Scenes/orderEdit.fxml"));
                    Region root = loader.load();
                    orderEditStage.setTitle("Úprava objednávky #" + orderID);
                    orderEditStage.setScene(new Scene(root));
                    orderEditStage.getIcons().add(new Image(Main.class.getResourceAsStream("Resources/img/icon.png")));
                    OrderEditController orderEdit = loader.getController();
                    orderEdit.setOrderID(orderID);
                    orderEdit.setParentController(controller);
                    orderEdit.loadData();
                    orderEditStage.show();
                } catch (IOException e) {
                    System.out.println(e);
                }
            });
        }
        return btn;
    }

    private Button createButtonShow() {
        Button btn = new Button("Detail");
        {
            btn.setOnAction((ActionEvent event) -> {
                Stage orderDetailStage = new Stage();

                // - Vyhození error okna
                try {
                    FXMLLoader loader = new FXMLLoader(Main.class.getResource("Scenes/orderDetail.fxml"));
                    Region root = loader.load();
                    orderDetailStage.setTitle("Detail objednávky #" + orderID);
                    orderDetailStage.setScene(new Scene(root));
                    orderDetailStage.getIcons().add(new Image(Main.class.getResourceAsStream("Resources/img/icon.png")));

                    OrderDetailController orderDetail = loader.getController();
                    orderDetail.setOrderID(orderID);
                    orderDetail.initialize();

                    orderDetailStage.show();
                } catch (IOException e) {
                    System.out.println(e);
                }
            });
        }
        return btn;
    }

    public Button getBtnShow() {
        return btnShow;
    }

    public Button getBtnEdit() {
        return btnEdit;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getStatus() {
        return status;
    }

    public OrdersViewController getController() {
        return controller;
    }
}
