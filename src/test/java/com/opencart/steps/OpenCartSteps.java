package com.opencart.steps;

import com.opencart.pages.OpenCartHomePage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.Serenity;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenCartSteps {

    OpenCartHomePage openCartHomePage;

    @Step("opens the OpenCart store home page")
    public void opensStoreHomePage() {
        openCartHomePage.openHomePage();
        assertThat(openCartHomePage.getPageTitle())
                .as("The store home page should be loaded")
                .containsIgnoringCase("Your Store");
    }

    @Step("searches for product '{0}'")
    public void searchesForProduct(String productName) {
        openCartHomePage.searchFor(productName);
    }

    @Step("verifies catalog search results are displayed")
    public void verifiesSearchResultsAreDisplayed() {
        assertThat(openCartHomePage.hasSearchResults())
                .as("Expected at least one catalog result")
                .isTrue();
    }

    @Step("verifies product '{0}' is listed in the results")
    public void verifiesProductIsListed(String productName) {
        assertThat(openCartHomePage.isProductListed(productName))
                .as("Expected product '%s' to appear in the catalog results", productName)
                .isTrue();
    }

    @Step("adds featured product '{0}' to the cart")
    public void addsFeaturedProductToCart(String productName) {
        openCartHomePage.addFeaturedProductToCart(productName);
    }

    @Step("tries to add searched product '{0}' to the cart")
    public void triesToAddSearchedProductToCart(String productName) {
        assertThat(openCartHomePage.canAddProductToCart(productName))
                .as("Expected product '%s' to be available in the catalog to add it to the cart", productName)
                .isTrue();

        openCartHomePage.addCatalogProductToCart(productName);
    }

    @Step("adds an intentional performance delay of {0} seconds")
    public void addsIntentionalPerformanceDelay(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("The intentional performance delay was interrupted", exception);
        }
    }

    @Step("waits until product '{0}' is confirmed in the cart")
    public void waitsForCartConfirmation(String productName, String expectedItemCount) {
        openCartHomePage.waitForCartConfirmation(productName, expectedItemCount);
    }

    @Step("verifies product '{0}' was added to the cart")
    public void verifiesProductWasAddedToCart(String productName) {
        assertThat(openCartHomePage.getSuccessAlertText())
                .as("The success banner should confirm the cart update")
                .containsIgnoringCase("Success")
                .containsIgnoringCase(productName);
    }

    @Step("verifies the cart summary shows '{0}'")
    public void verifiesCartItemCount(String expectedItemCount) {
        assertThat(openCartHomePage.getCartTotalText())
                .as("The mini-cart summary should show the expected quantity")
                .contains(expectedItemCount);
    }

    @Step("opens the shopping cart from the cart menu")
    public void opensShoppingCartFromCartMenu() {
        openCartHomePage.openShoppingCartFromCartMenu();
    }

    @Step("verifies product '{0}' is present in the shopping cart")
    public void verifiesProductIsPresentInShoppingCart(String productName) {
        assertThat(openCartHomePage.isProductPresentInShoppingCart(productName))
                .as("Expected product '%s' to appear in the shopping cart", productName)
                .isTrue();
    }

    @Step("reads and reports the purchase summary for '{0}'")
    public void readsAndReportsPurchaseSummary(String productName) {
        Map<String, String> purchaseSummary = openCartHomePage.readPurchaseSummary();

        assertThat(purchaseSummary)
                .as("Expected purchase summary totals to be displayed")
                .containsKeys("Sub-Total:", "Eco Tax (-2.00):", "VAT (20%):", "Total:");

        Serenity.recordReportData()
                .withTitle("Resumen de compra " + productName)
                .andContents(
                        purchaseSummary.entrySet()
                                .stream()
                                .map(entry -> entry.getKey() + " " + entry.getValue())
                                .collect(Collectors.joining(System.lineSeparator()))
                );
    }
}