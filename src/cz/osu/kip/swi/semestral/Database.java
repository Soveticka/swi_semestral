package cz.osu.kip.swi.semestral;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {

    private Connection createConnection(){
        String url = "jdbc:mariadb://soveticka.eu:3307/swi_semestral";
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            return DriverManager.getConnection(
                    url,
                    "Soveticka",
                    "Immergas51347."
            );
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public void insertData(String nameI, String lastnameI){
        Connection con = createConnection();
        try{
            Statement stmt = con.createStatement();
            String query = "INSERT INTO testTable (name, lastname) VALUES('"+nameI+"', '"+lastnameI+"')";
            stmt.executeUpdate(query);
            con.close();
            System.out.printf("User %s %s was successfully added to the Database%n", nameI, lastnameI);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void selectData(String query){
        Connection con = createConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet results = stmt.executeQuery(query);
            while(results.next()){
                System.out.println(String.format("%d\t%s\t%s", results.getInt(1), results.getString(2), results.getString(3)));
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
