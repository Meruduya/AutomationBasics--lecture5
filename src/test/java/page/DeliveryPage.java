
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
    private final SelenideElement agreementCheckbox = $("[data-test-id=agreement]");
    private final SelenideElement submitButton = $(byText("Запланировать"));
    private final SelenideElement replanButton = $(byText("Перепланировать"));
    private final SelenideElement notification = $(".notification");

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
        notification.should(appear, Duration.ofSeconds(15));
        notification.shouldHave(text("Встреча успешно запланирована на " + date));
    }

    public void verifyReplanNotification() {
        notification.should(appear, Duration.ofSeconds(15));
        notification.shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
    }

    public void clickReplan() {
        replanButton.click();
    }
}