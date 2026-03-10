package com.opencart.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OpenCartHomePage extends PageObject {

    private static final String BASE_URL = "http://opencart.abstracta.us/";

    @FindBy(name = "search")
    private WebElementFacade searchInput;

    @FindBy(css = "#search button")
    private WebElementFacade searchButton;

    @FindBy(css = ".product-layout")
    private List<WebElementFacade> productCards;

    @FindBy(css = ".alert-success")
    private WebElementFacade successAlert;

    @FindBy(id = "cart-total")
    private WebElementFacade cartTotal;

    @FindBy(css = "#cart > button")
    private WebElementFacade cartButton;

    @FindBy(css = "#cart > ul > li:nth-child(2) > div > p > a:nth-child(1)")
    private WebElementFacade viewCartButton;

    public void openHomePage() {
        openUrl(BASE_URL);
    }

    public void searchFor(String searchTerm) {
        searchInput.waitUntilClickable().clear();
        searchInput.type(searchTerm);
        searchButton.click();
    }

    public boolean hasSearchResults() {
        find(By.cssSelector(".product-layout")).waitUntilVisible();
        return !findAll(By.cssSelector(".product-layout")).isEmpty();
    }

    public boolean isProductListed(String productName) {
        return findAll(By.cssSelector(".product-layout .caption h4 a"))
                .stream()
                .anyMatch(product -> product.getText().trim().equalsIgnoreCase(productName));
    }

    public boolean canAddProductToCart(String productName) {
        return !findAll(addToCartButtonLocator(productName)).isEmpty();
    }

    public void addFeaturedProductToCart(String productName) {
        WebElementFacade addToCartButton = find(By.xpath(
                "//a[normalize-space()='" + productName + "']" +
                        "/ancestor::div[contains(@class,'product-thumb')]" +
                        "//button[contains(@onclick,'cart.add')]"
        ));

        addToCartButton.waitUntilClickable().click();
    }

    public void addCatalogProductToCart(String productName) {
        find(addToCartButtonLocator(productName)).waitUntilClickable().click();
    }

    public void waitForCartConfirmation(String productName, String expectedItemCount) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOf(successAlert));
        wait.until(driver -> successAlert.getText().toLowerCase().contains(productName.toLowerCase()));
        wait.until(driver -> cartTotal.getText().contains(expectedItemCount));
    }

    public void openShoppingCartFromCartMenu() {
        cartButton.waitUntilClickable().click();
        viewCartButton.waitUntilClickable().click();
        find(By.cssSelector("#content h1")).waitUntilVisible();
    }

    public boolean isProductPresentInShoppingCart(String productName) {
        return findAll(By.cssSelector(".table-responsive tbody tr td a"))
                .stream()
                .anyMatch(product -> product.getText().trim().equalsIgnoreCase(productName));
    }

    public Map<String, String> readPurchaseSummary() {
        List<WebElementFacade> summaryRows = findAll(By.cssSelector(".col-sm-4.col-sm-offset-8 table.table-bordered tbody tr"));
        Map<String, String> summary = new LinkedHashMap<>();

        for (WebElementFacade row : summaryRows) {
            List<WebElementFacade> columns = row.thenFindAll(By.tagName("td"));
            if (columns.size() >= 2) {
                String label = columns.get(0).getText().replace("\u00A0", " ").trim();
                String value = columns.get(1).getText().replace("\u00A0", " ").trim();
                summary.put(label, value);
            }
        }

        return summary;
    }

    public String getSuccessAlertText() {
        return successAlert.waitUntilVisible().getText();
    }

    public String getCartTotalText() {
        return cartTotal.waitUntilVisible().getText();
    }

    public String getPageTitle() {
        return getDriver().getTitle();
    }

    private By addToCartButtonLocator(String productName) {
        return By.xpath(
                "//a[normalize-space()='" + productName + "']" +
                        "/ancestor::div[contains(@class,'product-thumb')]" +
                        "//button[contains(@onclick,'cart.add')]"
        );
    }
}