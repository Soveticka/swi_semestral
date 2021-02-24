package cz.osu.kip.swi;

import java.sql.*;

public class Database {

    private Connection createConnection() {
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

    public void insertData(String name, String lastname) {
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

    public void insertData(String query) {
        Connection con = createConnection();
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public ResultSet selectData(String query) {
        Connection con = createConnection();
        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void selectDataArg(String query) {
        Connection con = createConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while(result.next()){
                System.out.println(result.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
