package org.example;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitUntilState;

public class LoginPage {

    private final Page page;
    private final Locator userNameInput;
    private final Locator passwordInput;
    private final Locator loginButton;

    public LoginPage(Page page) {
        this.page = page;
        this.userNameInput = page.locator("[data-test='username']");
        this.passwordInput = page.locator("[data-test='password']");
        this.loginButton = page.locator("[data-test='login-button']");

    }

    public void navigateToLoginPage() {
        page.navigate("https://www.saucedemo.com",
                new Page.NavigateOptions()
                        .setWaitUntil(WaitUntilState.COMMIT)
                        .setTimeout(60000)
        );
    }

    public void loginWithCredentials(String username, String password) {
        userNameInput.fill(username);
        passwordInput.fill(password);
        loginButton.click();
    }
}
