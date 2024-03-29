package test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @Test(expected = NullPointerException.class)
    public void testPostalCodeEqualsNull() {
        // Arrange
        String postalCode = null;

        // Act
        PostalCode.formatPostalCode(postalCode);
    }

    /**
     * @subcontract valid postalCode { 
     * @requires Integer.valueOf(postalCode.trim().substring(0, 4)) > 999 &&
     *           Integer.valueOf(postalCode.trim().substring(0, 4)) <= 9999 &&
     *           postalCode.trim().substring(4).trim().length == 2 &&
     *           'A' <= postalCode.trim().substring(4).trim().toUpperCase().charAt(0) &&
     *           postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z' &&
     *           'A' <= postalCode.trim().substring(5).trim().toUpperCase().charAt(1) &&
     *           postalCode.trim().substring(5).trim().toUpperCase().charAt(1) <= 'Z';
     * @ensures \result = postalCode.trim().substring(0, 4) + " " +
     *          postalCode.trim().substring(4).trim().toUpperCase() }
     */
    @Test
    public void testPostalCodeValid() {
        // Arrange
        String postalCode = "5126BB";

        // Act
        String formattedPostalCode = PostalCode.formatPostalCode(postalCode);

        // Assert
        assertEquals("5126 BB", formattedPostalCode);
    }

    /*
     * @subcontract invalid postalCode { 
     * @requires no other valid precondition;
     * @signals (IllegalArgumentException); }
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPostalCodeInvalid() {
        // Arrange
        String postalCode = "999ZZ";

        // Act
        PostalCode.formatPostalCode(postalCode);
    }

    /**
     * @subcontract invalid postalCode {
     * @requires postalCode.length() != 6;
     * @signals (IllegalArgumentException); }
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPostalCodeLengthInvalid() {
        // Arrange
        String postalCode = "1234567"; // Length is not 6

        // Act
        PostalCode.formatPostalCode(postalCode);
    }
}
