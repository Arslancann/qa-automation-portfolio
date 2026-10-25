package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {
    @Test
    void successfulLogin_shouldNavigateToProductsPage() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLoginPage();
        loginPage.loginWithCredentials("standard_user", "secret_sauce");

        assertThat(page).hasURL(Pattern.compile(".*/inventory.html"));
        assertThat(page.getByText("Products")).isVisible();
    }
}
