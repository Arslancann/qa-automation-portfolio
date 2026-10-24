package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest {

    static Playwright playwright;
    static Browser browser;

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();
        boolean isCi = System.getenv("CI") != null;
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isCi));
    }

    @AfterAll
    static void teardown() {
        browser.close();
        playwright.close();
    }

    @Test
    void successfulLogin_shouldNavigateToProductsPage() {
        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();
        LoginPage loginPage = new LoginPage(page);

        loginPage.navigateToLoginPage();
        loginPage.loginWithCredentials("standard_user", "secret_sauce");

        assertThat(page.getByText("Products")).isVisible();

        browserContext.close();
    }

}
