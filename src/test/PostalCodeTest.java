package test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import objects.PostalCode;

public class PostalCodeTest {

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
     * @subcontract null postalCode {
     * @requires postalCode == null;
     * @signals (NullPointerException) postalCode == null; }
     */
    // waarde postalcode aanpassen bij implementatie
    @Test(expected = NullPointerException.class)
    public void testPostalCodeEqualsNull() {
        // Arrange
        String postalCode = null;

        // Act
        try {
            PostalCode.formatPostalCode(postalCode);
            
            // If the formatPostalCode method does not throw NullPointerException,
            // the test should fail
            fail("Expected NullPointerException to be thrown");
        } catch (NullPointerException e) {
            // Assert
            // The expected NullPointerException was thrown
        }
    }

    /**
     * @subcontract valid postalCode {
     * @requires Integer.valueOf(postalCode.trim().substring(0, 4)) > 999 &&
     *           Integer.valueOf(postalCode.trim().substring(0, 4)) <= 9999 &&
     *           postalCode.trim().substring(4).trim().length == 2 &&
     *           'A' <=
     *           postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <=
     *           'Z' &&
     *           'A' <=
     *           postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <=
     *           'Z';
     * @ensures \result = postalCode.trim().substring(0, 4) + " " +
     *          postalCode.trim().substring(4).trim().toUpperCase() }
     */
    @Test
    public void testPostalCodeNumberValid() {

        String postalCode = "5126bb";

        String formattedPostalCode = PostalCode.formatPostalCode(postalCode);

        int postalCodeNumber = Integer.valueOf(formattedPostalCode.trim().substring(0, 4));
        String postalCodeLetters = formattedPostalCode.trim().substring(4).toUpperCase();

        assertTrue(postalCodeNumber > 999 && postalCodeNumber <= 9999 && postalCodeLetters.charAt(0) >= 'A'
                && postalCodeLetters.charAt(1) <= 'Z' && postalCodeLetters.charAt(1) >= 'A'
                && postalCodeLetters.charAt(1) <= 'Z');

    }

    /*
     * @subcontract invalid postalCode {
     * @requires no other valid precondition;
     * @signals (IllegalArgumentException); }
     */
    @Test(expected = IllegalArgumentException.class) // niet uitgekomen
    public void testPostalCodeInvalid() {
        String postalCode = "999ZZ";

        String formattedPostalCode = PostalCode.formatPostalCode(postalCode);

    }
}
