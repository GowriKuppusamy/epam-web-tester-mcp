package com.epam.webtester.framework.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.options.WaitUntilState;

public class LoginPage {

    private static final String LOGIN_URL = "https://www.saucedemo.com";
    private static final String USERNAME_INPUT = "#user-name";
    private static final String PASSWORD_INPUT = "#password";
    private static final String LOGIN_BUTTON = "#login-button";
    private static final String ERROR_MESSAGE = "[data-test='error']";

    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public LoginPage navigateToLoginPage() {
        page.navigate(LOGIN_URL, new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.locator(LOGIN_BUTTON).waitFor(new Page.Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    public LoginPage navigateTo(String url) {
        page.navigate(url, new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.locator(LOGIN_BUTTON).waitFor(new Page.Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    public LoginPage enterUsername(String username) {
        page.locator(USERNAME_INPUT).fill(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        page.locator(PASSWORD_INPUT).fill(password);
        return this;
    }

    public LoginPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return this;
    }

    public InventoryPage submitLogin() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
        return new InventoryPage(page);
    }

    public InventoryPage clickLogin() {
        return submitLogin();
    }

    public String getErrorMessage() {
        page.locator(ERROR_MESSAGE).waitFor(new Page.Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return page.locator(ERROR_MESSAGE).textContent();
    }

    public boolean isLoaded() {
        return page.locator(LOGIN_BUTTON).isVisible();
    }

    public String getPasswordFieldType() {
        return page.locator(PASSWORD_INPUT).getAttribute("type");
    }
}
