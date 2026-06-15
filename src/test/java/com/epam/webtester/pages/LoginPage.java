package com.epam.webtester.pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;
    private final String loginUrl = "https://www.saucedemo.com";

    public LoginPage(Page page) {
        this.page = page;
    }

    public void navigate() {
        page.navigate(loginUrl);
    }

    public void login(String username, String password) {
        page.getByPlaceholder("Username").fill(username);
        page.getByPlaceholder("Password").fill(password);
        page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
    }

    public String getErrorMessage() {
        return page.locator("[data-test='error']").textContent();
    }
}
