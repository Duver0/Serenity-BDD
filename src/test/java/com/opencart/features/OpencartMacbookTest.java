package com.opencart.features;

import com.opencart.steps.OpenCartSteps;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityRunner.class)
public class OpencartMacbookTest {

    @Managed(uniqueSession = false)
    WebDriver driver;

    @Steps
    OpenCartSteps openCartSteps;

    @BeforeClass
    public static void setupWebDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    @Title("Resumen de compra MacBook")
    public void resumenDeCompraMacBook() {
        openCartSteps.opensStoreHomePage();
        openCartSteps.addsFeaturedProductToCart("MacBook");
        openCartSteps.waitsForCartConfirmation("MacBook", "1 item(s)");
        openCartSteps.verifiesProductWasAddedToCart("MacBook");
        openCartSteps.verifiesCartItemCount("1 item(s)");
        openCartSteps.opensShoppingCartFromCartMenu();
        openCartSteps.verifiesProductIsPresentInShoppingCart("MacBook");
        openCartSteps.readsAndReportsPurchaseSummary("MacBook");
    }
}