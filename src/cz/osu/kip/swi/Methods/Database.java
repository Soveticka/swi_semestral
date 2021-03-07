package cz.osu.kip.swi.Methods;

import cz.osu.kip.swi.Controllers.OrdersViewController;
import cz.osu.kip.swi.Objects.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Database {

    private static Connection createConnection() {
        String url = "jdbc:mariadb://soveticka.eu:3307/swi_semestral";
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(
                    url,
                    "Soveticka",
                    "Immergas51347."
            );
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void insertData(String name, String lastname) {
        Connection con = createConnection();
        try {
            Statement stmt = con.createStatement();
            String query = String.format("INSERT INTO testTable (name, lastname) VALUES('%s', '%s')", name, lastname);
            stmt.executeUpdate(query);
            con.close();
            System.out.printf("User %s %s was successfully added to the Database%n", name, lastname);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static boolean insertData(String query) {
        Connection con = createConnection();
        try {
            Statement stmt = con.createStatement();
            int i = stmt.executeUpdate(query);
            con.close();
            if (i > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public static ResultSet selectData(String query) {
        Connection con = createConnection();
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static ObservableList<Order> getDataOrder(OrdersViewController controller) {
        Connection con = createConnection();
        ObservableList<Order> list = FXCollections.observableArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM orders");

            return createListOfOrders(list, result, controller);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static ResultSet getVehicleBrands() {
        Connection con = createConnection();
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery("SELECT brand FROM vehicleBrand");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static ResultSet getVehicleModelsByBrand(String brand) {
        Connection con = createConnection();
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(String.format("SELECT model FROM vehicleModel WHERE vehicleBrand_brand='%s'", brand));
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static ResultSet getUsedTimesAtDate(String date) {
        Connection con = createConnection();
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(String.format("SELECT timeI FROM orders WHERE dateI='%s'", date));
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static ResultSet getOrderByID(int orderID) {
        Connection con = createConnection();
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(String.format("SELECT * FROM orders WHERE id='%d'", orderID));
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static ObservableList<Order> getDataOrder(String query, String filter, OrdersViewController controller) {
        //"Objednávka","Zákazník","Email", "Telefon", "Datum", "Značka", "Model", "Objednané", "Rozpracované", "Dokončeno", "Zrušeno"
        Connection con = createConnection();
        ObservableList<Order> list = FXCollections.observableArrayList();
        String sqlQuery = "";
        if (filter.equals("Objednáno") || filter.equals("Rozpracováno") || filter.equals("Dokončeno") || filter.equals("Zrušeno")) {
            sqlQuery = String.format("SELECT * FROM orders WHERE status='%s'", filter);
        } else {
            switch (filter) {
                case "Objednávka":
                    sqlQuery = String.format("SELECT * FROM orders WHERE id='%d'", Integer.parseInt(query));
                    break;
                case "Zákazník":
                    if (query.contains(" ")) {
                        String[] name = query.split(" ");
                        sqlQuery = String.format("SELECT * FROM orders WHERE firstname='%s' AND lastname='%s'", name[0], name[1]);
                    } else {
                        sqlQuery = String.format("SELECT * FROM orders WHERE firstname='%s' or lastname='%s'", query, query);
                    }
                    break;
                case "Email":
                    sqlQuery = String.format("SELECT * FROM orders WHERE emailaddress='%s'", query);
                    break;
                case "Datum":
                    sqlQuery = String.format("SELECT * FROM orders WHERE dateI='%s'", query);
                    break;
                case "Značka":
                    sqlQuery = String.format("SELECT * FROM orders WHERE vehiclebrand='%s'", query);
                    break;
                case "Model":
                    sqlQuery = String.format("SELECT * FROM orders WHERE vehiclemodel='%s'", query);
                    break;
            }
        }
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(sqlQuery);
            return createListOfOrders(list, result, controller);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    private static ObservableList<Order> createListOfOrders(ObservableList<Order> list, ResultSet result, OrdersViewController controller) throws SQLException {
        while (result.next()) {
            list.add(
                    new Order(
                            Integer.parseInt(result.getString(1)),
                            (result.getString("firstname") + " " + result.getString("lastname")),
                            result.getString("phonenumber"),
                            result.getString("dateI"),
                            result.getString("timeI"),
                            result.getString("vehiclebrand"),
                            result.getString("vehiclemodel"),
                            result.getString("status"),
                            controller
                    )
            );
        }
        return list;
    }

    public static int getOrderCount(){
        Connection con = createConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT COUNT(*) AS rowcount FROM orders");
            result.next();
            int rows = result.getInt("rowcount");
            result.close();
            con.close();
            return rows;
        }catch (SQLException e){
            System.out.println(e);
        }
        return -1;
    }
}
