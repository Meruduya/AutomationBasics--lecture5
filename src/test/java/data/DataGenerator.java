package data;

import com.github.javafaker.Faker;
import dto.UserInfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("ru"));
    private static final Random random = new Random();

    private DataGenerator() {}

    private static final String[] cities = {
            "Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург",
            "Нижний Новгород", "Казань", "Челябинск", "Омск", "Самара", "Ростов-на-Дону"
    };

    public static String generateCity() {
        return cities[random.nextInt(cities.length)];
    }

    public static String generateName() {
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generatePhone() {
        // Faker может генерировать не в том формате, поэтому создаем сами
        return "+7" + String.format("%010d", faker.number().numberBetween(9000000000L, 9999999999L));
    }

    public static UserInfo generateUser() {
        return new UserInfo(generateCity(), generateName(), generatePhone());
    }

    public static String generateDate(int daysFromToday) {
        return LocalDate.now().plusDays(daysFromToday).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}