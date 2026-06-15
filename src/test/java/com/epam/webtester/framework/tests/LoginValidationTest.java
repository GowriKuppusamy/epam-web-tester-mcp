package com.epam.webtester.framework.tests;

import com.epam.webtester.framework.base.BaseTest;
import com.epam.webtester.framework.pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LoginValidationTest extends BaseTest {

    private static final String BASE_URL = "https://www.saucedemo.com";

    @ParameterizedTest(name = "{index} => username={0}, password={1}")
    @CsvSource({
            "'', '', Username is required",
            "invalid_user, wrong_pass, Username and password do not match any user in this service",
            "locked_out_user, secret_sauce, Sorry, this user has been locked out."
    })
    @DisplayName("GIT-3 - Login validation scenarios")
    void loginValidationScenarios(String username, String password, String expectedMessage) {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateTo(BASE_URL)
                .enterUsername(username)
                .enterPassword(password)
                .clickLogin();

        String actualError = loginPage.getErrorMessage();
        Assertions.assertTrue(actualError.contains(expectedMessage),
                "Expected error message to contain: " + expectedMessage + " but was: " + actualError);
    }
}
