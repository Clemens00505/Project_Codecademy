package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import objects.URLTools;

public class URLToolsTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * @subcontract no prefix part {
     * @requires !url.startsWith("https://") || !url.startsWith("http://")
     * @ensures \result = false; }
     */
    public void testNoPrefix() {
        // Arrange
        String invalidURL = "@example.com";

        // Act
        Boolean isValid = URLTools.validateURL(invalidURL);

        // Assert
        assertFalse(isValid);
    }
    /**
     * @subcontract no subdomain {
     * @requires !url.contains(".") || url.split(".")[0].length < 1;
     * @ensures \result = false; }
     */
    public void testNoSubdomain() {
        // Arrange
        String invalidURL = "https://.test.com";

        // Act
        Boolean isValid = URLTools.validateURL(invalidURL);

        // Assert
        assertFalse(isValid);
    }

    /**
     * @subcontract no second-level domain {
     * @requires !url.contains(".") || url.split(".")[1].length < 1;
     * @ensures \result = false; }
     */
    public void testNoSecondLevelDomain() {
        // Arrange
        String invalidURL = "https://www..com";

        // Act
        Boolean isValid = URLTools.validateURL(invalidURL);

        // Assert
        assertFalse(isValid);
    }

    /**
     * @subcontract no top-level domain {
     * @requires !url.contains(".") || url.split(".")[2].length < 1;
     * @ensures \result = false; }
     */
    public void testNoTopLevelDomain() {
        // Arrange
        String invalidURL = "https://www.test.";

        // Act
        Boolean isValid = URLTools.validateURL(invalidURL);

        // Assert
        assertFalse(isValid);
    }

    /**
     * @subcontract valid URL {
     * @requires no other precondition;
     * @ensures \result = true; }
     */
    public void validURL() {
        // Arrange
        String validURL = "https://www.test.com";

        // Act
        Boolean isValid = URLTools.validateURL(validURL); 

        // Assert
        assertTrue(isValid);
    }

}
