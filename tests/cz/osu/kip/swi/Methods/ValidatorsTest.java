package cz.osu.kip.swi.Methods;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorsTest {

    private final String[] possibleEmails = {"matej.komanek@gmail.com", "mat@gmail.com", "komanek@gmail", "komanek.com", "", "komanek@.com"};

    @Test
    void isValidEmail() {
        assertAll(
                () -> assertTrue(Validators.isValidEmail(possibleEmails[0])),
                () -> assertTrue(Validators.isValidEmail(possibleEmails[1])),
                () -> assertTrue(Validators.isValidEmail(possibleEmails[2])),
                () -> assertTrue(Validators.isValidEmail(possibleEmails[3])),
                () -> assertTrue(Validators.isValidEmail(possibleEmails[4]))
        );
    }

    private final String[] possiblePhoneNum = {"111111111", "111 111 111", "111-111-111", "+420111111111", "+420 111 111 111", "+420 111-111-111", "111 111 11", "+420 111 111", ""};

    @Test
    void isValidPhoneNumber() {
        assertAll(
                () -> assertTrue(Validators.isValidPhoneNumber(possiblePhoneNum[0])),
                () -> assertTrue(Validators.isValidPhoneNumber(possiblePhoneNum[1])),
                () -> assertTrue(Validators.isValidPhoneNumber(possiblePhoneNum[2])),
                () -> assertTrue(Validators.isValidPhoneNumber(possiblePhoneNum[3])),
                () -> assertTrue(Validators.isValidPhoneNumber(possiblePhoneNum[4])),
                () -> assertTrue(Validators.isValidPhoneNumber(possiblePhoneNum[5])),
                () -> assertTrue(Validators.isValidPhoneNumber(possiblePhoneNum[6])),
                () -> assertTrue(Validators.isValidPhoneNumber(possiblePhoneNum[7])),
                () -> assertTrue(Validators.isValidPhoneNumber(possiblePhoneNum[8]))
        );
    }

    private final String[] possibleAddress = {"Dlouhá Adresa Bez Čísla", "Dlouhá Adresa S Číslem 8", "Krátká 8", "Krátká", ""};

    @Test
    void isValidAddress() {
        assertAll(
                () -> assertTrue(Validators.isValidAddress(possibleAddress[0])),
                () -> assertTrue(Validators.isValidAddress(possibleAddress[1])),
                () -> assertTrue(Validators.isValidAddress(possibleAddress[2])),
                () -> assertTrue(Validators.isValidAddress(possibleAddress[3])),
                () -> assertTrue(Validators.isValidAddress(possibleAddress[4]))
        );
    }

    private final String[] possibleCity = {"Ostrava", "Frýdek-Místek", "Frýdek+Místek", "Frýdek_Místek", "Frýdek Místek", ""};

    @Test
    void isValidCity() {
        assertAll(
                () -> assertTrue(Validators.isValidCity(possibleCity[0])),
                () -> assertTrue(Validators.isValidCity(possibleCity[1])),
                () -> assertTrue(Validators.isValidCity(possibleCity[2])),
                () -> assertTrue(Validators.isValidCity(possibleCity[3])),
                () -> assertTrue(Validators.isValidCity(possibleCity[4])),
                () -> assertTrue(Validators.isValidCity(possibleCity[5]))
        );
    }

    private final String[] possibleZIP = {"11111", "111 11", "11 11", "1111", "111 1"};

    @Test
    void isValidZIP() {
        assertAll(
                () -> assertTrue(Validators.isValidZIP(possibleZIP[0])),
                () -> assertTrue(Validators.isValidZIP(possibleZIP[1])),
                () -> assertTrue(Validators.isValidZIP(possibleZIP[2])),
                () -> assertTrue(Validators.isValidZIP(possibleZIP[3])),
                () -> assertTrue(Validators.isValidZIP(possibleZIP[4]))
        );
    }

    private final String[] possibleSPZ = {"ASD123C", "ASD123"};

    @Test
    void isValidSPZ() {
        assertAll(
                () -> assertTrue(Validators.isValidSPZ(possibleSPZ[0])),
                () -> assertTrue(Validators.isValidSPZ(possibleSPZ[1]))
        );
    }

    private final String[] possibleYearOfProd = {"2000", "1800", "2023", "2021"};

    @Test
    void isValidYearOfProd() {
        assertAll(
                () -> assertTrue(Validators.isValidYearOfProd(possibleYearOfProd[0])),
                () -> assertTrue(Validators.isValidYearOfProd(possibleYearOfProd[1])),
                () -> assertTrue(Validators.isValidYearOfProd(possibleYearOfProd[2])),
                () -> assertTrue(Validators.isValidYearOfProd(possibleYearOfProd[3]))
        );
    }
}