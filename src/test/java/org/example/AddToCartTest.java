package org.example;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddToCartTest extends BaseTest {
    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";

    @Test
    @DisplayName("Başarılı girişten sonra ilk ürün sepete eklenmeli ve sepet ikonunda '1' görünmeli")
    void shouldAddFirstProductToCartAndShowBadgeWithOne() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLoginPage();
        loginPage.loginWithCredentials(USERNAME, PASSWORD);

        PlaywrightAssertions.assertThat(page).hasURL(java.util.regex.Pattern.compile(".*/inventory.html"));

        InventoryPage inventoryPage = new InventoryPage(page);

        PlaywrightAssertions.assertThat(inventoryPage.getPageTitle()).hasText("Products");

        inventoryPage.addFirstProductToCart();

        PlaywrightAssertions.assertThat(page.locator("[data-test='shopping-cart-badge']"))
                .isVisible();
        PlaywrightAssertions.assertThat(page.locator("[data-test='shopping-cart-badge']"))
                .hasText("1");
    }
}
