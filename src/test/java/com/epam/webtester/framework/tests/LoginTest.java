package com.epam.webtester.framework.tests;

import com.epam.webtester.framework.base.BaseTest;
import com.epam.webtester.framework.data.LoginTestData;
import com.epam.webtester.framework.pages.InventoryPage;
import com.epam.webtester.framework.pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTest {

    @Test
    @DisplayName("GIT-3 - Successful login with valid credentials")
    void validUserShouldLoginSuccessfully() {
        LoginPage loginPage = new LoginPage(page).navigateTo(LoginTestData.BASE_URL);
        Assertions.assertTrue(loginPage.isLoaded(), "Login page should be visible");

        InventoryPage inventoryPage = loginPage
                .enterUsername(LoginTestData.STANDARD_USER)
                .enterPassword(LoginTestData.SECRET_SAUCE)
                .clickLogin();

        Assertions.assertTrue(inventoryPage.isLoaded(), "Inventory page should be visible");
        Assertions.assertEquals("Products", inventoryPage.getTitle(), "Inventory title should be Products");
    }

    @Test
    @DisplayName("GIT-3 - Invalid credentials should show error")
    void invalidCredentialsShouldShowError() {
        LoginPage loginPage = new LoginPage(page).navigateTo(LoginTestData.BASE_URL);

        loginPage
                .enterUsername(LoginTestData.INVALID_USER)
                .enterPassword(LoginTestData.INVALID_PASSWORD)
                .clickLogin();

        Assertions.assertTrue(loginPage.isLoaded(), "User should remain on login page");
        Assertions.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                "Error message should indicate invalid credentials");
    }

    @Test
    @DisplayName("GIT-3 - Locked out user should show locked out error")
    void lockedOutUserShouldShowError() {
        LoginPage loginPage = new LoginPage(page).navigateTo(LoginTestData.BASE_URL);

        loginPage
                .enterUsername(LoginTestData.LOCKED_OUT_USER)
                .enterPassword(LoginTestData.SECRET_SAUCE)
                .clickLogin();

        Assertions.assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out"),
                "Error message should indicate locked out user");
    }

    @Test
    @DisplayName("GIT-3 - Login button should keep password masked")
    void passwordFieldShouldBeMasked() {
        LoginPage loginPage = new LoginPage(page).navigateTo(LoginTestData.BASE_URL);
        Assertions.assertEquals("password", loginPage.getPasswordFieldType(), "Password field should be masked");
    }
}
