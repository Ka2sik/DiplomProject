package ru.netology.diplom.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static final Faker faker = new Faker(new Locale("en"));
    private static final String approvedCard = "4444444444444441";
    private static final String declinedCard = "4444444444444442";
    private static final String month = getFutureMonth();
    private static final String year = getShiftedYear(0);
    private static final String lastYear = getShiftedYear(-1);
    private static final String nextYear = getShiftedYear(1);
    private static final String cardHolder = faker.name().firstName() + " " + faker.name().lastName();
    private static final String cvv = faker.number().digits(3);
    private static final String randomCard = faker.number().digits(16);

    private DataHelper() {
    }

    public static String getFutureMonth() {
        int minMonth = LocalDate.now().getMonthValue();
        int maxMonth = 12;
        int amount = (int) (Math.random() * (maxMonth - minMonth) + minMonth);
        if (amount < 10) {
            return ("0" + String.valueOf(amount));
        }
        return String.valueOf(amount);
    }

    public static String getShiftedYear(int yearCount) {
        return LocalDate.now().plusYears(yearCount).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static Card getApprovedCard() {
        return new Card(approvedCard, month, year, cardHolder, cvv);
    }

    public static Card getDeclinedCard() {
        return new Card(declinedCard, month, nextYear, cardHolder, cvv);
    }

    public static Card getCardEmptyNumber() {
        return new Card("", month, nextYear, cardHolder, cvv);
    }

    public static Card getCardEmptyMonth() {
        return new Card(declinedCard, "", nextYear, cardHolder, cvv);
    }

    public static Card getCardEmptyYear() {
        return new Card(declinedCard, month, "", cardHolder, cvv);
    }

    public static Card getCardEmptyCardHolder() {
        return new Card(declinedCard, month, nextYear, "", cvv);
    }

    public static Card getCardEmptyCVV() {
        return new Card(declinedCard, month, nextYear, cardHolder, "");
    }

    public static Card getRandomCard() {
        return new Card(randomCard, month, year, cardHolder, cvv);
    }

    public static Card getNumberCard15Symbols() {
        String number = faker.number().digits(15);
        return new Card(number, month, year, cardHolder, cvv);
    }

    public static Card getNumberCard17Symbols() {
        String number = faker.number().digits(17);
        return new Card(number, month, year, cardHolder, cvv);
    }

    public static Card getCardMonth1Symbol() {
        String month = faker.number().digit();
        return new Card(randomCard, month, nextYear, cardHolder, cvv);
    }

    public static Card getCardMonth3Symbols() {
        String month = Integer.toString(faker.number().numberBetween(130, 999));
        return new Card(randomCard, month, year, cardHolder, cvv);
    }

    public static Card getCard13Month() {
        return new Card(randomCard, "13", nextYear, cardHolder, cvv);
    }

    public static Card getCardZeroMonthThisYear() {
        return new Card(randomCard, "00", year, cardHolder, cvv);
    }

    public static Card getCardZeroMonthNextYear() {
        return new Card(randomCard, "00", nextYear, cardHolder, cvv);
    }

    public static Card getCardYear1Symbol() {
        String year = "2";
        return new Card(randomCard, month, year, cardHolder, cvv);
    }

    public static Card getCardYear3Symbols() {
        String yy = year + "1";
        return new Card(randomCard, month, yy, cardHolder, cvv);
    }

    public static Card getCardPlus6YearsToTheCurrent() {
        String year = getShiftedYear(6);
        return new Card(randomCard, month, year, cardHolder, cvv);
    }

    public static Card getCardLastYear() {
        return new Card(randomCard, month, lastYear, cardHolder, cvv);
    }

    public static Card getCardZeroYear() {
        String year = "00";
        return new Card(randomCard, month, year, cardHolder, cvv);
    }

    public static Card getCardHolder1Word() {
        String name = faker.name().firstName();
        return new Card(randomCard, month, year, name, cvv);
    }

    public static Card getCardHolderCyrillic() {
        Faker faker = new Faker(new Locale("ru"));
        String name = faker.name().firstName() + " " + faker.name().lastName();
        return new Card(randomCard, month, year, name, cvv);
    }

    public static Card getCardHolderWithNumbers() {
        String name = faker.name().firstName() + " " + faker.number().digits(4);
        return new Card(randomCard, month, year, name, cvv);
    }

    public static Card getCardHolderWithSpecialSymbols() {
        String name = faker.name().firstName() + " $*)#";
        return new Card(randomCard, month, year, name, cvv);
    }

    public static Card getCardHolderSpaces() {
        String name = "       ";
        return new Card(randomCard, month, year, name, cvv);
    }

    public static Card getCardCvv2Symbols() {
        String cvv = faker.number().digits(2);
        return new Card(randomCard, month, year, cardHolder, cvv);
    }

    public static Card getCardCvv4Symbols() {
        String cvv = faker.number().digits(4);
        return new Card(randomCard, month, year, cardHolder, cvv);
    }

    public static Card getCardCvvNotNumbers() {
        String cvv = "@ g";
        return new Card(randomCard, month, year, cardHolder, cvv);
    }
}
