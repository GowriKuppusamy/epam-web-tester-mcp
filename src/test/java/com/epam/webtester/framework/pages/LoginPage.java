package com.epam.webtester.framework.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.options.WaitUntilState;

public class LoginPage {

    private final Page page;

    private final String usernameInput = "#user-name";
    private final String passwordInput = "#password";
    private final String loginButton = "#login-button";
    private final String errorMessage = "[data-test='error']";

    public LoginPage(Page page) {
        this.page = page;
    }

    public LoginPage navigateTo(String url) {
        page.navigate(url, new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        page.locator(loginButton).waitFor(new Page.Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    public LoginPage enterUsername(String username) {
        page.locator(usernameInput).fill(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        page.locator(passwordInput).fill(password);
        return this;
    }

    public InventoryPage clickLogin() {
        page.locator(loginButton).click();
        return new InventoryPage(page);
    }

    public String getErrorMessage() {
        page.locator(errorMessage).waitFor(new Page.Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return page.locator(errorMessage).textContent();
    }

    public boolean isLoaded() {
        return page.locator(loginButton).isVisible();
    }

    public String getPasswordFieldType() {
        return page.locator(passwordInput).getAttribute("type");
    }
}
