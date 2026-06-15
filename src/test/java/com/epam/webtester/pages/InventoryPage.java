package com.epam.webtester.pages;

import com.microsoft.playwright.Page;

public class InventoryPage {
    private final Page page;

    public InventoryPage(Page page) {
        this.page = page;
    }

    public boolean isLoaded() {
        return page.url().contains("inventory.html") && page.locator(".inventory_list").isVisible();
    }

    public String getTitleText() {
        return page.locator(".title").textContent();
    }
}
