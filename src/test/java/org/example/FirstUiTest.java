package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FirstUiTest {

    static Playwright playwright;
    static Browser browser;

    @BeforeAll
    static void setup() {
        playwright = Playwright.create(); // Playwright motorunu başlat
        boolean isCi = System.getenv("CI") != null; // Yereldeysek headless=false (gözle gör), GitHub'daysak headless=true (görünmez)
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isCi) // Tarayıcıyı başlat ve gözle gör
        );
    }

    @AfterAll
    static void tearDown() {
        browser.close(); // Tarayıcıyı kapat
        playwright.close(); // Motoru kapat
    }

    @Test
    void sayfaBasligi_dogruOlmali() {
        BrowserContext context = browser.newContext(); // İzole bir profil aç
        Page page = context.newPage(); // Yeni bir sekme aç
        page.navigate("https://playwright.dev/"); // Siteye git
        assertThat(page).hasTitle(Pattern.compile("Playwright")); // ASSERT: Sayfa başlığı "Playwright" içermeli
        context.close();  // Context'i kapat (temizlik)
    }

    @Test
    void aramaKutusunaYaziYazilmali() {
        BrowserContext context = browser.newContext();
        Page page = context.newPage();
        page.navigate("https://playwright.dev/");

        Locator searchButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search"));
        searchButton.click();

        Locator searchInput = page.getByPlaceholder("Search docs");
        searchInput.fill("Locators");
        assertThat(searchInput).hasValue("Locators");
        context.close();
    }

    @Test
    void basariliGirisYapilmali() {
        BrowserContext context = browser.newContext();
        Page page = context.newPage();
        page.navigate("https:www.saucedemo.com/");

        Locator searchNameInput = page.getByPlaceholder("Username");
        searchNameInput.fill("standard_user");

        Locator searchPasswordInput = page.getByPlaceholder("Password");
        searchPasswordInput.fill("secret_sauce");

        Locator searchButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        searchButton.click();

        assertThat(page.getByText("Products")).isVisible();

        context.close();
    }
}
