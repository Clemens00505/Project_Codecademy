package Testsets.MailToolsTest;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class MailToolsTest {
    @Test
    public void noMailboxPart() {
        String mailAddress = "@mail.com";
        assertFalse(!mailAddress.contains("@") || mailAddress.split("@")[0].length() < 1);

    }

    @Test 
    public void subdomainTldDelimiter() {
        String mailAddress = "@test@test.com";
        assertFalse(!mailAddress.contains("@") || mailAddress.split("@")[1].split(".").length > 2);
    }

    @Test
    public void noSubdomainPart() {
        String mailAddress = "";
        assertFalse(!mailAddress.contains("@") || mailAddress.split("@")[1].split(".")[0].length() < 1);
    }

    @Test
    public void noTldPart() {
        String mailAddress = "@mail.com";
        assertFalse(!mailAddress.contains("@") || mailAddress.split("@")[1].split(".")[1].length() < 1);
    }

    @Test
    public boolean validEmail() {
        return true;
    }
}
