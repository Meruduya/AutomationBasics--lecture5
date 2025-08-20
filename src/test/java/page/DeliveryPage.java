package page;

import com.codeborne.selenide.SelenideElement;
import dto.UserInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import org.openqa.selenium.Keys;

public class DeliveryPage {
    private final SelenideElement cityField = $("[data-test-id=city] input");
    private final SelenideElement dateField = $("[data-test-id=date] input");
    private final SelenideElement nameField = $("[data-test-id=name] input");
    private final SelenideElement phoneField = $("[data-test-id=phone] input");
    private final SelenideElement agreementCheckbox = $("[data-test-id=agreement]");
    private final SelenideElement submitButton = $(byText("Запланировать"));
    private final SelenideElement replanButton = $(byText("Перепланировать"));
    private final SelenideElement successNotification = $(".notification_status_ok");
    private final SelenideElement replanNotification = $("[data-test-id=replan-notification]");

    public void fillForm(UserInfo user, String date) {
        cityField.setValue(user.getCity());
        dateField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        dateField.setValue(date);
        nameField.setValue(user.getName());
        phoneField.setValue(user.getPhone());
        agreementCheckbox.click();
    }

    public void submitForm() {
        submitButton.click();
    }

    public void verifySuccessfulBooking(String date) {
        successNotification.should(appear, Duration.ofSeconds(15));
        successNotification.should(text("Встреча успешно запланирована на " + date));
    }

    public void verifyReplanNotification() {
        replanNotification.should(appear, Duration.ofSeconds(15));
        replanNotification.should(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
    }

    public void clickReplan() {
        replanButton.click();
    }

    public void verifySuccessfulReplan(String date) {
        successNotification.should(appear, Duration.ofSeconds(15));
        successNotification.should(text("Встреча успешно запланирована на " + date));
    }
}