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
public class OpencartIphoneTest {

    @Managed(uniqueSession = false)
    WebDriver driver;

    @Steps
    OpenCartSteps openCartSteps;

    @BeforeClass
    public static void setupWebDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    @Title("Resumen de compra iPhone")
    public void resumenDeCompraIPhone() {
        openCartSteps.opensStoreHomePage();
        openCartSteps.addsIntentionalPerformanceDelay(10);
        openCartSteps.addsFeaturedProductToCart("iPhone");
        openCartSteps.waitsForCartConfirmation("iPhone", "1 item(s)");
        openCartSteps.verifiesProductWasAddedToCart("iPhone");
        openCartSteps.verifiesCartItemCount("1 item(s)");
        openCartSteps.addsIntentionalPerformanceDelay(10);
        openCartSteps.opensShoppingCartFromCartMenu();
        openCartSteps.verifiesProductIsPresentInShoppingCart("iPhone");
        openCartSteps.addsIntentionalPerformanceDelay(10);
        openCartSteps.readsAndReportsPurchaseSummary("iPhone");
    }
}