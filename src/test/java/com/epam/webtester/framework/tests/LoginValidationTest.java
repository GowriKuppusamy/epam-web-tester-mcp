package com.epam.webtester.framework.tests;

import com.epam.webtester.framework.base.BaseTest;
import com.epam.webtester.framework.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginValidationTest extends BaseTest {

    private static Stream<Arguments> invalidLoginScenarios() {
        return Stream.of(
                Arguments.of("", "", "Username is required"),
                Arguments.of("invalid_user", "wrong_pass", "Username and password do not match"),
                Arguments.of("locked_out_user", "secret_sauce", "locked out" )
        );
    }

    @ParameterizedTest(name = "GIT-3 - Login validation scenario {index}")
    @MethodSource("invalidLoginScenarios")
    @DisplayName("GIT-3 - Login validation scenarios")
    void invalidCredentialsShouldShowValidation(String username, String password, String expectedMessagePart) {
        LoginPage loginPage = new LoginPage(page).navigateToLoginPage();
        loginPage.login(username, password).submitLogin();

        String actualError = loginPage.getErrorMessage();
        assertTrue(actualError.toLowerCase().contains(expectedMessagePart.toLowerCase()),
                () -> "Expected error to contain '" + expectedMessagePart + "' but was: " + actualError);
    }

    @Test
    @DisplayName("GIT-3 - Empty username and password should show validation")
    void emptyCredentialsShouldShowValidation() {
        LoginPage loginPage = new LoginPage(page).navigateToLoginPage();
        loginPage.submitLogin();

        String actualError = loginPage.getErrorMessage();
        assertTrue(actualError.contains("Username is required")
                        || actualError.contains("Password is required")
                        || actualError.toLowerCase().contains("username and password"),
                "Validation message should be shown for empty credentials. Actual: " + actualError);
    }
}
