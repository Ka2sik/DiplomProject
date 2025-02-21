package ru.netology.diplom.pages;

import com.codeborne.selenide.SelenideElement;
import ru.netology.diplom.data.Card;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {

    private static final SelenideElement buyButton = $$("button").findBy(exactText("Купить"));
    private static final SelenideElement creditButton = $$("button").findBy(exactText("Купить в кредит"));
    private static final SelenideElement buyHeader = $$("h3").findBy(exactText("Оплата по карте"));
    private static final SelenideElement creditHeader = $$("h3").findBy(exactText("Кредит по данным карты"));
    private static final SelenideElement approveButton = $$("button").findBy(exactText("Продолжить"));
    private static final SelenideElement cardNumberField = $(byText("Номер карты")).parent().$("[class=input__control]");
    private static final SelenideElement monthField = $(byText("Месяц")).parent().$("[class=input__control]");
    private static final SelenideElement yearField = $(byText("Год")).parent().$("[class=input__control]");
    private static final SelenideElement cardHolderField = $(byText("Владелец")).parent().$("[class=input__control]");
    private static final SelenideElement cvvField = $(byText("CVC/CVV")).parent().$("[class=input__control]");
    private static final SelenideElement operationApproved = $(byText("Операция одобрена Банком.")).parent().$("[class=notification__content]");
    private static final SelenideElement operationRejected = $(byText("Ошибка! Банк отказал в проведении операции.")).parent().$("[class=notification__content]");
    private static final SelenideElement wrongFormatError = $(byText("Неверный формат"));
    private static final SelenideElement wrongDateError = $(byText("Неверно указан срок действия карты"));
    private static final SelenideElement expiredYearError = $(byText("Истёк срок действия карты"));
    private static final SelenideElement emptyFieldError = $(byText("Поле обязательно для заполнения"));

    public static void goToBuyForm() {
        buyButton.click();
        buyHeader.shouldBe(visible);
    }

    public static void goToCreditForm() {
        creditButton.click();
        creditHeader.shouldBe(visible);
    }

    public void sendCardData(Card card) {
        cardNumberField.setValue(card.getCardNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        cardHolderField.setValue(card.getCardHolder());
        cvvField.setValue(card.getCvv());
        approveButton.click();
    }

    public void waitNotificationApproved() {
        operationApproved.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void waitNotificationRejected() {
        operationRejected.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void waitNotificationWrongFormatError() {
        wrongFormatError.shouldBe(visible, Duration.ofSeconds(3));
    }

    public void waitNotificationWrongDateError() {
        wrongDateError.shouldBe(visible, Duration.ofSeconds(3));
    }

    public void waitNotificationExpiredYear() {
        expiredYearError.shouldBe(visible, Duration.ofSeconds(3));
    }

    public void waitNotificationEmptyCardHolderField() {
        emptyFieldError.shouldBe(visible, Duration.ofSeconds(3));
    }
}