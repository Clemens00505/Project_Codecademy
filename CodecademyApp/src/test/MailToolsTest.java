package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import objects.MailTools;

public class MailToolsTest {

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
     * @desc Validates if mailAddress is valid. It should be in the form of:
     *       <at least 1 character>@<at least 1 character>.<at least 1 character>
     * @subcontract no mailbox part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[0].length < 1;
     * @ensures \result = false; }
     */
    @Test
    public void testNoMailbox() {
        // Arrange
        String invalidMail = "@example.com";

        // Act
        Boolean isValid = MailTools.validateMailAddress(invalidMail);

        // Assert
        assertFalse(isValid);
    }

    /**
     * @subcontract subdomain-tld delimiter {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[1].split(".").length > 2;
     * @ensures \result = false; }
     */
    @Test
    public void testSubdomainTLD() {
        // Arrange
        String invalidMail = "test@subdomain.test.com";

        // Act
        Boolean isValid = MailTools.validateMailAddress(invalidMail);

        // Assert
        assertFalse(isValid);
    }

    /**
     * @subcontract no subdomain part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[1].split(".")[0].length < 1;
     * @ensures \result = false; }
     */
    @Test
    public void testNoSubdomain() {
        // Arrange
        String invalidMail = "test@.com";

        // Act
        Boolean isValid = MailTools.validateMailAddress(invalidMail);

        // Assert
        assertFalse(isValid);
    }

    /**
     * @subcontract no tld part {
     * @requires !mailAddress.contains("@") ||
     *           mailAddress.split("@")[1].split(".")[1].length < 1;
     * @ensures \result = false; }
     */
    @Test
    public void testNoTLD() {
        // Arrange
        String invalidMail = "test@subdomain.";

        // Act
        Boolean isValid = MailTools.validateMailAddress(invalidMail);

        // Assert
        assertFalse(isValid);
    }

    /**
     * @subcontract valid email {
     * @requires no other precondition
     * @ensures \result = true; }
     */
    @Test
    void testValidMail() {
        // Arrange
        String validMail = "test@subdomain.com";

        // Act
        boolean isValid = MailTools.validateMailAddress(validMail);

        // Assert
        assertTrue(isValid);
    }
}
