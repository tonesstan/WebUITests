package Autotests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSauceDemo {
    Locators locators = new Locators();

    @BeforeAll
    public static void setUp() {
        Configuration.browser = "firefox";
        Configuration.browserSize = "1920x1080";
        Configuration.headless = true;
        Configuration.baseUrl = "https://www.saucedemo.com";
        Configuration.fastSetValue = false;
        Configuration.pageLoadTimeout = 300000;
        Configuration.pageLoadStrategy = "eager";
        open("/");
    }

    @Test
    @Order(1)
    public void loginTest() {
        String username = $("#login_credentials").getText().split("\\n")[1];
        String password = $("div.login_password").getText().split("\\n")[1];
        $("#user-name").setValue(username);
        $("#password").setValue(password);
        $("#login-button").click();
        $(".title").shouldBe(visible).shouldHave(text("Products"));
    }

    @Test
    @Order(2)
    public void itemsTest() {
        List<String> itemNames = $$("div.inventory_item_name").texts();
        for (String itemName : itemNames) {
            open("/inventory.html");
            locators.clickLink(itemName);
            $(".inventory_details_name").shouldHave(text(itemName));
        }
    }
}