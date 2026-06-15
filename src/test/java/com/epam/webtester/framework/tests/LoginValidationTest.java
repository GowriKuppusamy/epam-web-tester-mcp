package com.epam.webtester.framework.tests;

import com.epam.webtester.framework.base.BaseTest;
import com.epam.webtester.framework.pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class LoginValidationTest extends BaseTest {

    private static final String BASE_URL = "https://www.saucedemo.com";

    private record LoginScenario(String displayName, String username, String password, String expectedErrorFragment) {
    }

    static Stream<Arguments> loginScenarios() {
        return Stream.of(
                Arguments.of(new LoginScenario(
                        "Empty username and password should show validation",
                        "",
                        "",
                        "username is required")),
                Arguments.of(new LoginScenario(
                        "Invalid username/password should show error",
                        "invalid_user",
                        "wrong_pass",
                        "username and password do not match")),
                Arguments.of(new LoginScenario(
                        "Locked out user should show locked out error",
                        "locked_out_user",
                        "secret_sauce",
                        "locked out"))
        );
    }

    @ParameterizedTest(name = "GIT-3 - {0.displayName}")
    @MethodSource("loginScenarios")
    void loginValidationScenariosShouldShowExpectedMessage(LoginScenario scenario) {
        LoginPage loginPage = new LoginPage(page).navigateTo(BASE_URL);

        try {
            loginPage.enterUsername(scenario.username())
                    .enterPassword(scenario.password())
                    .clickLogin();

            String actualError = loginPage.getErrorMessage().toLowerCase();
            Assertions.assertTrue(actualError.contains(scenario.expectedErrorFragment()),
                    "Expected error containing '" + scenario.expectedErrorFragment() + "' but got: " + actualError);
        } catch (AssertionError | RuntimeException ex) {
            captureScreenshot("ASSERTION_FAILURE");
            throw ex;
        }
    }
}
