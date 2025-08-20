package test;

import data.DataGenerator;
import dto.UserInfo;
import org.junit.jupiter.api.Test;

class DataGeneratorTest {

    @Test
    void shouldGenerateValidUserData() {
        UserInfo user = DataGenerator.generateUser();
        String date = DataGenerator.generateDate(5);

        System.out.println("Generated user: " + user);
        System.out.println("Generated date: " + date);

        // Простые проверки
        assert user.getCity() != null && !user.getCity().isEmpty();
        assert user.getName() != null && !user.getName().isEmpty();
        assert user.getPhone() != null && user.getPhone().startsWith("+7");  // ИСПРАВИЛИ ТУТ!
        assert date != null && date.matches("\\d{2}\\.\\d{2}\\.\\d{4}");
    }
}