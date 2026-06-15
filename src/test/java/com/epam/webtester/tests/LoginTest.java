package com.epam.webtester.tests;

import com.epam.webtester.base.BaseTest;
import com.epam.webtester.pages.InventoryPage;
import com.epam.webtester.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
                {"standard_user", "secret_sauce", true, null},
                {"locked_out_user", "secret_sauce", false, "Sorry, this user has been locked out."},
                {"invalid_user", "wrong_pass", false, "Username and password do not match any user in this service"},
                {"", "", false, "Username is required"}
        };
    }

    @Test(dataProvider = "loginData")
    public void verifyLogin(String username, String password, boolean success, String expectedError) {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();
        loginPage.login(username, password);

        if (success) {
            InventoryPage inventoryPage = new InventoryPage(page);
            Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page should be loaded after valid login");
            Assert.assertEquals(inventoryPage.getTitleText(), "Products");
        } else {
            Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError), "Expected error message was not displayed");
        }
    }
}
