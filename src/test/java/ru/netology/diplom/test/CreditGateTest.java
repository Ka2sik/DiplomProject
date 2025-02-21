package ru.netology.diplom.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.diplom.data.DataHelper;
import ru.netology.diplom.data.SQLHelper;
import ru.netology.diplom.pages.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.diplom.pages.PaymentPage.goToCreditForm;

public class CreditGateTest {

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
    @DisplayName("Should Get Credit With An Approved Card")
    void shouldGetCreditSuccessfullyWithApprovedCard() {
        goToCreditForm();
        page.sendCardData(DataHelper.getApprovedCard());
        page.waitNotificationApproved();
        assertEquals("APPROVED", SQLHelper.getCreditStatus());
    }

    @Test
    @DisplayName("Should Not Get Credit With An Declined Card")
    void shouldNotGetCreditWithDeclinedCard() {
        goToCreditForm();
        page.sendCardData(DataHelper.getDeclinedCard());
        page.waitNotificationApproved(); // лучше заменить на другую фразу, например "Отказ! Карта заблокирована"
        assertEquals("DECLINED", SQLHelper.getCreditStatus());
    }

    @Test
    @DisplayName("Negative Test With Empty Credit Card Number")
    void CardNumberFieldEmpty() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardEmptyNumber());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card With Empty Month")
    void MonthFieldEmpty() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardEmptyMonth());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card With Empty Year")
    void YearFieldEmpty() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardEmptyYear());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card With Empty Card Holder")
    void CardHolderFieldEmpty() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardEmptyCardHolder());
        page.waitNotificationEmptyCardHolderField();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card With Empty CVV")
    void CVVFieldEmpty() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardEmptyCVV());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test The Credit Card Is Not In The Database")
    void buyNegativeCardNotInDatabase() {
        goToCreditForm();
        page.sendCardData(DataHelper.getRandomCard());
        page.waitNotificationRejected();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test The Credit Card Number Consists of 15 Symbols")
    void buyNegativeNumberCard15Symbols() {
        goToCreditForm();
        page.sendCardData(DataHelper.getNumberCard15Symbols());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test The Credit Card Number Consists of 17 Symbols")
        //поле должно принимать только 16 символов
    void buyNegativeNumberCard17Symbols() {
        goToCreditForm();
        page.sendCardData(DataHelper.getNumberCard17Symbols());
        page.waitNotificationRejected();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card Month Consists Of One Symbol")
    void buyNegativeMonth1Symbol() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardMonth1Symbol());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card Month Consists Of Three Symbol")
        //поле должно принимать только 2 символа
    void buyNegativeMonth3Symbol() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardMonth3Symbols());
        page.waitNotificationWrongDateError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card Month Is More Than 12")
    void buyNegativeMonthMoreThan12() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCard13Month());
        page.waitNotificationWrongDateError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card Month Of This Year Is Zero")
    void buyNegativeMonth00ThisYear() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardZeroMonthThisYear());
        page.waitNotificationWrongDateError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card Month Of Next Year Is Zero")
    void buyNegativeMonth00NextYear() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardZeroMonthNextYear());
        page.waitNotificationWrongDateError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card Year Is Zero")
    void buyNegativeYear00() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardZeroYear());
        page.waitNotificationExpiredYear();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card Year Consists Of One Symbol")
    void buyNegativeYear1Symbol() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardYear1Symbol());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card Year Consists Of Three Symbols")
        //поле должно принимать только 2 символа (текущий год + цифра 1 в конце)
    void buyNegativeYear3Symbol() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardYear3Symbols());
        page.waitNotificationRejected();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card Year Earlier Than The Current One")
    void buyNegativeYearBeforeThisYear() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardLastYear());
        page.waitNotificationExpiredYear();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card Year Is 6 More Than The Current One")
    void buyNegativeYearOverThisYearOn6() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardPlus6YearsToTheCurrent());
        page.waitNotificationWrongDateError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card CVV Code Consists Of Two Symbols")
    void buyNegativeCvv2Symbols() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardCvv2Symbols());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card CVV Code Consists Of Four Symbols")
        //поле должно принимать только 3 символа
    void buyNegativeCvv4Symbols() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardCvv4Symbols());
        page.waitNotificationRejected();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card CVV Code Contains Not Numbers")
        //поле не должно заполняться
    void buyNegativeCvvNotNumbers() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardCvvNotNumbers());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card Holder Field Consists Of Firstname")
    void buyNegativeOwner1Word() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardHolder1Word());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card Holder Field Contains Cyrillic")
    void buyNegativeOwnerCyrillic() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardHolderCyrillic());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card Holder Field Contains Numbers")
    void buyNegativeOwnerNumbers() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardHolderWithNumbers());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card Holder Field Contains Special Symbols")
    void buyNegativeOwnerSpecialSymbols() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardHolderWithSpecialSymbols());
        page.waitNotificationWrongFormatError();
        assertEquals("0", SQLHelper.requestsCount());
    }

    @Test
    @DisplayName("Negative Test A Credit Card Holder Field Consists of Spaces")
    void buyNegativeOwnerSpaces() {
        goToCreditForm();
        page.sendCardData(DataHelper.getCardHolderSpaces());
        page.waitNotificationEmptyCardHolderField();
        assertEquals("0", SQLHelper.requestsCount());
    }
}

