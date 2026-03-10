package com.example.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

/**
 * Page Object for Google's home page and search results page.
 *
 * <p>PageObject is a Serenity BDD base class that provides built-in
 * waiting, screenshot capture, and Serenity-aware element interactions.</p>
 */
public class GooglePage extends PageObject {

    /** Google's search input field. */
    @FindBy(name = "q")
    private WebElementFacade searchInput;

    /** The "Google Search" submit button. */
    @FindBy(name = "btnK")
    private WebElementFacade searchButton;

    /** Container wrapping the organic search results. */
    @FindBy(id = "search")
    private WebElementFacade searchResultsContainer;

    // ── Actions ───────────────────────────────────────────────────────────────

    /** Navigates to https://www.google.com */
    public void openHomePage() {
        openUrl("https://www.google.com");
    }

    /**
     * Types the given term into the search box and submits the search.
     *
     * @param searchTerm the text to search for
     */
    public void searchFor(String searchTerm) {
        searchInput.waitUntilVisible();
        searchInput.clear();
        searchInput.type(searchTerm);
        searchInput.submit();
    }

    /**
     * Returns {@code true} when at least one search result is displayed.
     *
     * @return whether the results container is visible and non-empty
     */
    public boolean hasResults() {
        return searchResultsContainer.isVisible()
                && !searchResultsContainer.getText().trim().isEmpty();
    }

    /** Returns the page title of the currently loaded page. */
    public String getPageTitle() {
        return getDriver().getTitle();
    }
}
