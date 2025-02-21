package ru.netology.diplom.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.diplom.data.DataHelper;
import ru.netology.diplom.data.SQLHelper;
import ru.netology.diplom.pages.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.diplom.pages.PaymentPage.*;

public class PaymentGateTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
    }

    @AfterEach
    public void cleanBase() {
        SQLHelper.clearTestData();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    PaymentPage page = new PaymentPage();

    @Test
    @DisplayName("Should Buy Successfully With An Approved Card")
    void shouldBuySuccessfullyFromApprovedCard() {
        goToBuyForm();
        page.sendCardData(DataHelper.getApprovedCard());
        page.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Should Not Buy With A Declined Card")
    void shouldNotBuyFromDeclinedCard() {
        goToBuyForm();
        page.sendCardData(DataHelper.getDeclinedCard());
        page.waitNotificationApproved(); // лучше заменить на другую фразу, например "Отказ! Карта заблокирована"
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("Negative Test With Empty Card Number")
    void CardNumberFieldEmpty() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardEmptyNumber());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test With Empty Month")
    void MonthFieldEmpty() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardEmptyMonth());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test With Empty Year")
    void YearFieldEmpty() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardEmptyYear());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test With Empty Card Holder")
    void CardHolderFieldEmpty() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardEmptyCardHolder());
        page.waitNotificationEmptyCardHolderField();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test With Empty CVV")
    void CVVFieldEmpty() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardEmptyCVV());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test The Card Is Not In The Database")
    void buyNegativeCardNotInDatabase() {
        goToBuyForm();
        page.sendCardData(DataHelper.getRandomCard());
        page.waitNotificationRejected();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test The Card Number Consists of 15 Symbols")
    void buyNegativeNumberCard15Symbols() {
        goToBuyForm();
        page.sendCardData(DataHelper.getNumberCard15Symbols());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test The Card Number Consists of 17 Symbols")
        //поле должно принимать только 16 символов
    void buyNegativeNumberCard17Symbols() {
        goToBuyForm();
        page.sendCardData(DataHelper.getNumberCard17Symbols());
        page.waitNotificationRejected();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Month Consists Of One Symbol")
    void buyNegativeMonth1Symbol() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardMonth1Symbol());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Month Consists Of Three Symbol")
        //поле должно принимать только 2 символа
    void buyNegativeMonth3Symbol() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardMonth3Symbols());
        page.waitNotificationWrongDateError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Month Is More Than 12")
    void buyNegativeMonthMoreThan12() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCard13Month());
        page.waitNotificationWrongDateError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Month Of This Year Is Zero")
    void buyNegativeMonth00ThisYear() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardZeroMonthThisYear());
        page.waitNotificationWrongDateError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Month Of Next Year Is Zero")
    void buyNegativeMonth00NextYear() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardZeroMonthNextYear());
        page.waitNotificationWrongDateError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test The Year Is Zero")
    void buyNegativeYear00() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardZeroYear());
        page.waitNotificationExpiredYear();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test The Year Consists Of One Symbol")
    void buyNegativeYear1Symbol() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardYear1Symbol());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test The Year Consists Of Three Symbols")
        //поле должно принимать только 2 символа (текущий год + цифра 1 в конце)
    void buyNegativeYear3Symbol() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardYear3Symbols());
        page.waitNotificationRejected();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Year Earlier Than The Current One")
    void buyNegativeYearBeforeThisYear() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardLastYear());
        page.waitNotificationExpiredYear();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test The Year Is 6 More Than The Current One")
    void buyNegativeYearOverThisYearOn6() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardPlus6YearsToTheCurrent());
        page.waitNotificationWrongDateError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A CVV Code Consists Of Two Symbols")
    void buyNegativeCvv2Symbols() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardCvv2Symbols());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A CVV Code Consists Of Four Symbols")
        //поле должно принимать только 3 символа
    void buyNegativeCvv4Symbols() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardCvv4Symbols());
        page.waitNotificationRejected();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A CVV Code Contains Not Numbers")
        //поле не должно заполняться
    void buyNegativeCvvNotNumbers() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardCvvNotNumbers());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Card Holder Field Consists Of Firstname")
    void buyNegativeOwner1Word() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardHolder1Word());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Card Holder Field Contains Cyrillic")
    void buyNegativeOwnerCyrillic() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardHolderCyrillic());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Card Holder Field Contains Numbers")
    void buyNegativeOwnerNumbers() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardHolderWithNumbers());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Card Holder Field Contains Special Symbols")
    void buyNegativeOwnerSpecialSymbols() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardHolderWithSpecialSymbols());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Card Holder Field Consists of Spaces")
    void buyNegativeOwnerSpaces() {
        goToBuyForm();
        page.sendCardData(DataHelper.getCardHolderSpaces());
        page.waitNotificationEmptyCardHolderField();
        assertEquals("0", SQLHelper.requestsCount());
    }
}
