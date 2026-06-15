package com.epam.webtester.framework.tests;

import com.epam.webtester.framework.base.BaseTest;
import com.epam.webtester.framework.data.LoginTestData;
import com.epam.webtester.framework.pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class LoginValidationTest extends BaseTest {

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("loginValidationScenarios")
    @DisplayName("GIT-3 - Login validation scenarios")
    void loginValidationShouldShowMessage(String scenarioName, String username, String password, String expectedMessageFragment, TestInfo testInfo) {
        LoginPage loginPage = new LoginPage(page).navigateTo(LoginTestData.BASE_URL);

        try {
            loginPage.login(username, password);
            String actualError = loginPage.getErrorMessage();

            Assertions.assertTrue(
                    actualError != null && actualError.toLowerCase().contains(expectedMessageFragment.toLowerCase()),
                    () -> "Expected validation message containing '" + expectedMessageFragment + "' but got: " + actualError
            );
        } catch (AssertionError | RuntimeException exception) {
            captureFailureScreenshot(testInfo, exception);
            throw exception;
        }
    }

    static Stream<Arguments> loginValidationScenarios() {
        return Stream.of(
                Arguments.of("Empty username and password", "", "", "Username is required"),
                Arguments.of("Invalid credentials", LoginTestData.INVALID_USERNAME, LoginTestData.INVALID_PASSWORD, "incorrect"),
                Arguments.of("Locked-out user", LoginTestData.LOCKED_OUT_USERNAME, LoginTestData.VALID_PASSWORD, "locked out")
        );
    }
}
