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
public class OpencartCamionetaNegativeTest {

    @Managed(uniqueSession = false)
    WebDriver driver;

    @Steps
    OpenCartSteps openCartSteps;

    @BeforeClass
    public static void setupWebDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    @Title("Intento fallido de compra de camioneta")
    public void intentoFallidoDeCompraDeCamioneta() {
        openCartSteps.opensStoreHomePage();
        openCartSteps.searchesForProduct("Toyota Prado TXL 2024");
        openCartSteps.triesToAddSearchedProductToCart("Toyota Prado TXL 2024");
    }
}