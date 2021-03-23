package cz.osu.kip.swi.Controllers;

import cz.osu.kip.swi.Main;
import cz.osu.kip.swi.Methods.Database;
import cz.osu.kip.swi.Methods.QueryModes;
import cz.osu.kip.swi.Objects.Order;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersViewController {

    private OrdersViewController controller;

    @FXML
    private TextField searchTextField;

    @FXML
    private ComboBox<String> searchCategory;

    @FXML
    private Button searchButton;

    @FXML
    DatePicker datePicker;

    @FXML
    private Button createOrderButton;

    @FXML
    private TableView<Order> orderList;

    @FXML
    private TableColumn<Order, Integer> orderList_orderID;

    @FXML
    private TableColumn<Order, String> orderList_Customer;

    @FXML
    private TableColumn<Order, String> orderList_phoneNumber;

    @FXML
    private TableColumn<Order, String> orderList_date;

    @FXML
    private TableColumn<Order, String> orderList_time;

    @FXML
    private TableColumn<Order, String> orderList_brand;

    @FXML
    private TableColumn<Order, String> orderList_model;

    @FXML
    private TableColumn<Order, String> orderList_status;

    @FXML
    private TableColumn<Order, Void> orderList_show;

    @FXML
    private TableColumn<Order, Void> orderList_edit;

    @FXML
    private Button refreshButton;

    @FXML
    private ComboBox<String> comboBoxPicker;

    ObservableList<Order> list;

    public void initialize(OrdersViewController controller) {
        this.controller = controller;
        searchCategory.getItems().removeAll(searchCategory.getItems());
        searchCategory.getItems().addAll("Objednávka", "Zákazník", "Email", "Datum", "Značka", "Model", "Objednáno", "Rozpracováno", "Dokončeno", "Zrušeno");


        loadFromDatabase(QueryModes.EVERYTHING);
    }

    public void loadFromDatabase(QueryModes mode) {
        if (orderList.getItems() != null) orderList.getItems().removeAll();
        orderList_orderID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
        orderList_Customer.setCellValueFactory(new PropertyValueFactory<Order, String>("name"));
        orderList_phoneNumber.setCellValueFactory(new PropertyValueFactory<Order, String>("phoneNumber"));
        orderList_date.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));
        orderList_time.setCellValueFactory(new PropertyValueFactory<Order, String>("time"));
        orderList_brand.setCellValueFactory(new PropertyValueFactory<Order, String>("brand"));
        orderList_model.setCellValueFactory(new PropertyValueFactory<Order, String>("model"));
        orderList_status.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
        orderList_show.setCellValueFactory(new PropertyValueFactory<Order, Void>("btnShow"));
        orderList_edit.setCellValueFactory(new PropertyValueFactory<Order, Void>("btnEdit"));

        if (mode == QueryModes.EVERYTHING) list = Database.getDataOrder(controller);
        if (mode == QueryModes.FILTERED) {
            if (searchTextField.isVisible()) {
                list = Database.getDataOrder(searchTextField.getText(), searchCategory.getValue(), controller);
            } else if (datePicker.isVisible()) {
                list = Database.getDataOrder(datePicker.getValue().toString(), searchCategory.getValue(), controller);
            } else if (comboBoxPicker.isVisible()){
                list = Database.getDataOrder(comboBoxPicker.getValue(), searchCategory.getValue(), controller);
            }
        }
        orderList.setItems(list);
    }

    @FXML
    public void createOrder(ActionEvent event) {
        Stage createOrderStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Scenes/orderCreate.fxml"));
            Region root = loader.load();
            createOrderStage.setTitle("Vytvoření objednávky");
            createOrderStage.setScene(new Scene(root));
            createOrderStage.getIcons().add(new Image(Main.class.getResourceAsStream("Resources/img/icon.png")));
            OrderCreateController orderCreateController = loader.getController();
            orderCreateController.setParentController(controller);
        } catch (IOException e) {
            System.out.println(e);
        }

        createOrderStage.show();
    }

    @FXML
    public void filterOrders(ActionEvent event) {
        if (searchTextField.isVisible()) {
            if (!searchTextField.getText().equals("") && (!searchCategory.getValue().equals("Objednáno") || !searchCategory.getValue().equals("Rozpracováno") || !searchCategory.getValue().equals("Dokončeno") || !searchCategory.getValue().equals("Zrušeno"))) {
                loadFromDatabase(QueryModes.FILTERED);
            } else if (searchCategory.getValue().equals("Objednáno") || searchCategory.getValue().equals("Rozpracováno") || searchCategory.getValue().equals("Dokončeno") || searchCategory.getValue().equals("Zrušeno")) {
                loadFromDatabase(QueryModes.FILTERED);
            } else {
                loadFromDatabase(QueryModes.EVERYTHING);
            }
        } else if (datePicker.isVisible()) {
            checkIfValueIsNull(datePicker.getValue() != null);
        } else if (comboBoxPicker.isVisible()){
            checkIfValueIsNull(comboBoxPicker.getValue() != null);
        }

        searchTextField.setText("");
        datePicker.setValue(null);
    }

    private void checkIfValueIsNull(boolean b) {
        if (b && (!searchCategory.getValue().equals("Objednáno") || !searchCategory.getValue().equals("Rozpracováno") || !searchCategory.getValue().equals("Dokončeno") || !searchCategory.getValue().equals("Zrušeno"))) {
            loadFromDatabase(QueryModes.FILTERED);
        } else {
            loadFromDatabase(QueryModes.EVERYTHING);
        }
    }

    @FXML
    public void changeInput(ActionEvent event) {
        if (searchCategory.getValue().equals("Datum")) {
            searchTextField.setVisible(false);
            datePicker.setVisible(true);
        } else if (searchCategory.getValue().equals("Model") || searchCategory.getValue().equals("Značka")) {
            if (searchTextField.isVisible()) {
                searchTextField.setVisible(false);
                comboBoxPicker.setVisible(true);
            } else {
                datePicker.setVisible(false);
                comboBoxPicker.setVisible(true);
            }
            if (searchCategory.getValue().equals("Značka")) {
                comboBoxPicker.getItems().removeAll(comboBoxPicker.getItems());
                try (ResultSet result = Database.getVehicleBrands()) {
                    while (result.next()) {
                        comboBoxPicker.getItems().add(result.getString("brand"));
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
            } else if (searchCategory.getValue().equals("Model")) {
                comboBoxPicker.getItems().removeAll(comboBoxPicker.getItems());
                try (ResultSet result = Database.getVehicleModels()) {
                    while (result.next()) {
                        comboBoxPicker.getItems().add(result.getString("model"));
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        } else {
            if (comboBoxPicker.isVisible()) {
                comboBoxPicker.setVisible(false);
                searchTextField.setVisible(true);
            } else if (datePicker.isVisible()) {
                datePicker.setVisible(false);
                searchTextField.setVisible(true);
            }
        }
    }

    @FXML
    public void refreshOrders(ActionEvent event) {
        loadFromDatabase(QueryModes.EVERYTHING);
    }



}
