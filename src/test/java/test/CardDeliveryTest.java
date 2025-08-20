package test;

import com.codeborne.selenide.Configuration;
import data.DataGenerator;
import dto.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DeliveryPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;

class CardDeliveryTest {
    private DeliveryPage deliveryPage;

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        deliveryPage = new DeliveryPage();
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfullyPlanDelivery() {
        UserInfo user = DataGenerator.generateUser();
        String planningDate = DataGenerator.generateDate(4);

        deliveryPage.fillForm(user, planningDate);
        deliveryPage.submitForm();
        deliveryPage.verifySuccessfulBooking(planningDate);
    }

    @Test
    void shouldSuccessfullyReplanDelivery() {
        UserInfo user = DataGenerator.generateUser();
        String firstDate = DataGenerator.generateDate(4);
        String secondDate = DataGenerator.generateDate(7);

        // Первое планирование
        deliveryPage.fillForm(user, firstDate);
        deliveryPage.submitForm();
        deliveryPage.verifySuccessfulBooking(firstDate);

        // Обновляем страницу для второго заказа
        refresh();

        // Попытка перепланирования
        deliveryPage.fillForm(user, secondDate);
        deliveryPage.submitForm();
        deliveryPage.verifyReplanNotification();

        // Подтверждение перепланирования
        deliveryPage.clickReplan();
        deliveryPage.verifySuccessfulReplan(secondDate);
    }
}