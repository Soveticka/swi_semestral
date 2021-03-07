package cz.osu.kip.swi.Methods;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Validators {
    private static boolean isValid(String regex, String match){
        Pattern pat = Pattern.compile(regex);
        return pat.matcher(match).matches();
    }

    public static boolean isValidEmail(String email) {
        //      - Check email format: String + "@" + String + "." + String (2-3 characters) | :)
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        return isValid(regex, email);
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        //      - Check phone number: number | :)
        String regex = "^(\\+\\d{1,3}( )?)?(\\d{3}[- .]?){2}\\d{3}$";
        return isValid(regex, phoneNumber);
    }

    public static boolean isValidAddress(String address){
        //      - Check address: String + number ( "Velkomoravská 8" ) | :)
        String regex = "[A-Za-zÀ-ž -.]+([0-9]+)";
        return isValid(regex, address);
    }

    public static boolean isValidCity(String city){
        //      - Check city: Text | :)
        String regex = "[A-Za-zÀ-ž -]+";
        return isValid(regex, city);
    }

    public static boolean isValidZIP(String zip){
        //      - Check ZIP: number (5 characters)
        String regex = "(\\d{3}( )?)(\\d{2})";
        return isValid(regex, zip);
    }

    public static boolean isValidSPZ(String spz){
        //      - Check SPZ: length (7 characters)
        String regex = "[A-Za-z0-9]{7}";
        return isValid(regex, spz);
    }

    public static boolean isValidYearOfProd(String year){
        //      - Check yearOfProductiom: 1885 - actual year
        return Integer.parseInt(year) > 1885 && Integer.parseInt(year) <= Calendar.getInstance().get(Calendar.YEAR);
    }


}