package com.epam.webtester.framework.tests;

import com.epam.webtester.framework.base.BaseTest;
import com.epam.webtester.framework.data.LoginTestData;
import com.epam.webtester.framework.pages.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginValidationTest extends BaseTest {

    @Test
    @DisplayName("GIT-3 - Empty username and password should show validation")
    void emptyCredentialsShouldShowValidation() {
        LoginPage loginPage = new LoginPage(page).navigateTo(LoginTestData.BASE_URL);

        loginPage.clickLogin();

        Assertions.assertTrue(loginPage.getErrorMessage().contains("Username is required"),
                "Validation message should be shown for empty credentials");
    }
}
