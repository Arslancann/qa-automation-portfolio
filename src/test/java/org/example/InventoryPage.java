package org.example;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class InventoryPage {
    private final Page page;
    private final Locator pageTitle;
    private final Locator cartBadge;

    public InventoryPage(Page page) {
        this.page = page;
        this.pageTitle = page.locator("[data-test='title']");
        this.cartBadge = page.locator("[data-test='shopping-cart-badge']");
    }

    public Locator getPageTitle() {
        return pageTitle;
    }

    public String getCartCount() {
        return cartBadge.innerText();
    }

    public void addFirstProductToCart() {
        page.locator("[data-test='inventory-item']")
                .first()
                .locator("button[data-test^='add-to-cart']")
                .click();
    }
}
