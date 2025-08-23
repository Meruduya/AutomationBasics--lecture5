package page;

import com.codeborne.selenide.SelenideElement;
import dto.UserInfo;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryPage {
    private final SelenideElement cityField = $("[data-test-id=city] input");
    private final SelenideElement dateField = $("[data-test-id=date] input");
    private final SelenideElement nameField = $("[data-test-id=name] input");
    private final SelenideElement phoneField = $("[data-test-id=phone] input");
    private final SelenideElement agreementBox = $("[data-test-id=agreement] .checkbox__box");
    private final SelenideElement agreementInput = $("[data-test-id=agreement] input[type='checkbox']");
    private final SelenideElement submitButton = $$("button").findBy(exactText("Запланировать"));

    private final SelenideElement successNotification = $("[data-test-id=success-notification]");
    private final SelenideElement successClose = $("[data-test-id=success-notification] .icon-button");
    private final SelenideElement replanNotification = $("[data-test-id=replan-notification]");

    private void replaceValue(SelenideElement el, String value) {
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        el.setValue(value);
    }

    private void replaceDate(String date) {
        dateField.sendKeys(
                Keys.chord(Keys.CONTROL, "a"),
                Keys.BACK_SPACE
        );
        dateField.setValue(date);
    }

    private void ensureAgreementChecked() {
        agreementInput.scrollIntoView(true);
        if (!agreementInput.isSelected()) {
            agreementBox.click();
        }
        agreementInput.shouldBe(selected);
    }

    /** Первичное заполнение всех полей */
    public void fillForm(UserInfo user, String date) {
        replaceValue(cityField, user.getCity());
        // На некоторых версиях стенда хватает простого setValue; pressEnter() не обязателен.
        replaceDate(date);
        replaceValue(nameField, user.getName());
        replaceValue(phoneField, user.getPhone());
        ensureAgreementChecked();
    }

    /** Для перепланирования меняем ТОЛЬКО дату + убеждаемся в галочке */
    public void changeDateOnly(String date) {
        replaceDate(date);
        ensureAgreementChecked();
    }

    public void submitForm() {
        submitButton.scrollIntoView(true).click();
    }

    public void verifySuccessfulBooking(String date) {
        successNotification.should(appear, Duration.ofSeconds(15));
        successNotification.$(".notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + date), Duration.ofSeconds(15));
    }

    public void closeSuccessNotification() {
        successNotification.should(appear, Duration.ofSeconds(15));
        successClose.click();
        successNotification.should(disappear, Duration.ofSeconds(15));
    }

    public void verifyReplanNotification() {
        replanNotification.should(appear, Duration.ofSeconds(15)).shouldBe(visible);
        replanNotification.$(".notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату"), Duration.ofSeconds(15))
                .shouldHave(text("Перепланировать?"), Duration.ofSeconds(15));
    }

    public void clickReplan() {
        replanNotification.$(byText("Перепланировать")).click();
    }
}