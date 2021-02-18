package cz.osu.kip.swi.semestral;

public class Main {

    public static void main(String[] args) {
        Database db = new Database();

        db.insertData("Soveticka","Sovetickovy");
        db.insertData("ZuZeekHD","ZuZeekHDckova");

        db.selectData("SELECT * FROM testTable");
    }
}
