package com.sergeev.rogii.service;

import com.sergeev.rogii.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserService {
    private final Random random = new Random();

    private String generateName() {
        String firstChar = RandomStringUtils.random(1, 'A', 'Z', true, false);
        return firstChar + RandomStringUtils.randomAlphabetic(5, 10).toLowerCase();
    }

    private String generateEmail(String firstName, String lastName) {
        String[] domains = {"@gmail.com", "@yahoo.com", "@outlook.com", "@example.com", "@mail.com"};
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + domains[ThreadLocalRandom.current().nextInt(domains.length)];
        return email;
    }

    private String generateAddress() {
        String streetName = RandomStringUtils.randomAlphabetic((int)(Math.random() * 6) + 5);
        streetName = Character.toUpperCase(streetName.charAt(0)) + streetName.substring(1).toLowerCase();
        String houseNumber = RandomStringUtils.randomNumeric(1, 4);
        String cityName = RandomStringUtils.randomAlphabetic((int)(Math.random() * 6) + 5);
        cityName = Character.toUpperCase(cityName.charAt(0)) + cityName.substring(1).toLowerCase();
        String postalCode = RandomStringUtils.randomNumeric(5);
        return houseNumber + " " + streetName + " St, " + cityName + ", " + postalCode;
    }

    public User generateUser(int id) {
        String firstName = generateName();
        String lastName = generateName();
        User user = new User(id, generateEmail(firstName, lastName), firstName, lastName);
        return user;
    }

    public User generateUserWithAddress(int id) {
        String firstName = generateName();
        String lastName = generateName();
        User user = new User(id, generateEmail(firstName, lastName), firstName, lastName);
        user.setAddress(generateAddress());
        return user;
    }

    public int generateRandomId() {
        return random.nextInt(10000);
    }
}
