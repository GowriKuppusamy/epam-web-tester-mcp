package com.epam.webtester.framework.pages;

import com.microsoft.playwright.Page;

public class InventoryPage {

    private final Page page;

    private final String inventoryTitle = "span.title";

    public InventoryPage(Page page) {
        this.page = page;
    }

    public boolean isLoaded() {
        return page.locator(inventoryTitle).isVisible();
    }

    public String getTitle() {
        return page.locator(inventoryTitle).textContent();
    }
}
