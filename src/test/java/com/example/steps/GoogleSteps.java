package com.example.steps;

import com.example.pages.GooglePage;
import net.thucydides.core.annotations.Step;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step library for Google search interactions.
 *
 * <p>Each public method annotated with {@link Step} becomes a named step in the
 * Serenity HTML report and will include an automatic screenshot.</p>
 */
public class GoogleSteps {

    /** Serenity injects the page object automatically. */
    GooglePage googlePage;

    // ── Step 1 ────────────────────────────────────────────────────────────────

    /**
     * Opens Google's home page in the browser.
     *
     * <p>Serenity reports this as <em>"opens Google home page"</em>.</p>
     */
    @Step("opens Google home page")
    public void opensGoogleHomePage() {
        googlePage.openHomePage();
    }

    // ── Step 2 ────────────────────────────────────────────────────────────────

    /**
     * Types the given term into the search box and submits.
     *
     * <p>Serenity reports this as <em>"searches for '{0}'"</em>.</p>
     *
     * @param searchTerm the keyword(s) to search
     */
    @Step("searches for '{0}'")
    public void searchesFor(String searchTerm) {
        googlePage.searchFor(searchTerm);
    }

    // ── Step 3 ────────────────────────────────────────────────────────────────

    /**
     * Verifies that the results page contains at least one search result.
     *
     * <p>Serenity reports this as <em>"verifies search results are displayed"</em>.</p>
     */
    @Step("verifies search results are displayed")
    public void verifiesResultsAreDisplayed() {
        assertThat(googlePage.hasResults())
                .as("Expected search results to be displayed, but none were found")
                .isTrue();
    }

    // ── Optional step 4 ───────────────────────────────────────────────────────

    /**
     * Verifies that the page title contains the given search term.
     *
     * <p>Serenity reports this as <em>"verifies page title contains '{0}'"</em>.</p>
     *
     * @param expectedTerm term that should appear in the browser title bar
     */
    @Step("verifies page title contains '{0}'")
    public void verifiesPageTitleContains(String expectedTerm) {
        assertThat(googlePage.getPageTitle())
                .as("Page title should contain the search term")
                .containsIgnoringCase(expectedTerm);
    }
}
