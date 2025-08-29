package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataGenerator;
import dto.UserInfo;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.DeliveryPage;

import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {

    private DeliveryPage deliveryPage;

    @BeforeAll
    static void beforeAll() {
        // Подключаем Allure listener
        SelenideLogger.addListener("allure", new AllureSelenide());

        Configuration.baseUrl = "http://localhost:9999";
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1280x900";
    }

    @AfterAll
    static void afterAll() {
        // Отключаем Allure listener
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        open("/");
        deliveryPage = new DeliveryPage();
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @Test
    @DisplayName("Успешное планирование встречи на валидную дату (>= +3 дня)")
    void shouldSuccessfullyPlanDelivery() {
        UserInfo user = DataGenerator.generateUser();
        String planningDate = DataGenerator.generateDate(4);

        deliveryPage.fillForm(user, planningDate);
        deliveryPage.submitForm();
        deliveryPage.verifySuccessfulBooking(planningDate);
    }

    @Test
    @DisplayName("Успешное перепланирование встречи с подтверждением")
    void shouldSuccessfullyReplanDelivery() {
        UserInfo user = DataGenerator.generateUser();
        String firstDate = DataGenerator.generateDate(4);
        String secondDate = DataGenerator.generateDate(7);

        // Первое планирование
        deliveryPage.fillForm(user, firstDate);
        deliveryPage.submitForm();
        deliveryPage.verifySuccessfulBooking(firstDate);
        deliveryPage.closeSuccessNotification();   // убираем оверлей

        // Перепланирование: меняем ТОЛЬКО дату
        deliveryPage.changeDateOnly(secondDate);
        deliveryPage.submitForm();

        // Ждём запрос на перепланирование
        deliveryPage.verifyReplanNotification();

        // Подтверждаем и убеждаемся в новой дате
        deliveryPage.clickReplan();
        deliveryPage.verifySuccessfulBooking(secondDate);
    }
}