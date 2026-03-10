package com.example.features;

import com.example.steps.GoogleSteps;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

/**
 * Basic Google search acceptance test.
 *
 * <p>Uses Serenity BDD's {@link SerenityRunner} to:
 * <ul>
 *   <li>Manage the browser lifecycle ({@link Managed})</li>
 *   <li>Inject step libraries ({@link Steps})</li>
 *   <li>Capture screenshots and generate a rich HTML report</li>
 * </ul>
 * </p>
 *
 * <h2>How to run</h2>
 * <pre>
 *   ./gradlew clean test aggregate
 * </pre>
 * <p>The HTML "Living Documentation" report is generated in
 * {@code build/site/serenity/index.html}.</p>
 */
@RunWith(SerenityRunner.class)
public class GoogleSearchTest {

    /** Serenity creates and tears down the WebDriver instance automatically. */
    @Managed(uniqueSession = false)
    WebDriver driver;

    /** Serenity injects and instruments the step library. */
    @Steps
    GoogleSteps googleSteps;

    @BeforeClass
    public static void setupWebDriver() {
        // Download / configure the correct ChromeDriver binary automatically
        WebDriverManager.chromedriver().setup();
    }

    // ── Test 1: basic 3-step flow ──────────────────────────────────────────

    /**
     * Verifies that a Google search for "Serenity BDD" returns results.
     *
     * <p>Steps:
     * <ol>
     *   <li>Open Google home page</li>
     *   <li>Search for "Serenity BDD"</li>
     *   <li>Verify search results are displayed</li>
     * </ol>
     * </p>
     */
    @Test
    @Title("Search for 'Serenity BDD' and verify results are displayed")
    public void searchForSerenityBDD() {
        // Step 1 – Open Google
        googleSteps.opensGoogleHomePage();

        // Step 2 – Search for a term
        googleSteps.searchesFor("Serenity BDD");

        // Step 3 – Verify results appear
        googleSteps.verifiesResultsAreDisplayed();
    }

    // ── Test 2: extended 4-step flow ──────────────────────────────────────

    /**
     * Verifies that a Google search for "Java automation testing" returns results
     * and that the browser title reflects the search term.
     *
     * <p>Steps:
     * <ol>
     *   <li>Open Google home page</li>
     *   <li>Search for "Java automation testing"</li>
     *   <li>Verify search results are displayed</li>
     *   <li>Verify the page title contains the search term</li>
     * </ol>
     * </p>
     */
    @Test
    @Title("Search for 'Java automation testing' and verify title and results")
    public void searchForJavaAutomationTesting() {
        // Step 1 – Open Google
        googleSteps.opensGoogleHomePage();

        // Step 2 – Search for a term
        googleSteps.searchesFor("Java automation testing");

        // Step 3 – Verify results appear
        googleSteps.verifiesResultsAreDisplayed();

        // Step 4 – Verify page title
        googleSteps.verifiesPageTitleContains("Java automation testing");
    }
}
