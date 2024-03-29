package objects;

import test.MailToolsTest;

public class MailTools {
    
    public static boolean validateMailAddress(String mailAddress) {
        if (mailAddress == null || mailAddress.isEmpty()) {
            return false; // No mailAddress or empty mailAddress
        }
    
        // Split the mailAddress into parts using "@" as delimiter
        String[] parts = mailAddress.split("@");
    
        // Check if the mailAddress contains exactly one "@" character
        if (parts.length != 2) {
            return false; // Invalid format: must contain exactly one "@"
        }
    
        // Check if the mailbox part is empty
        if (parts[0].isEmpty()) {
            return false; // Empty mailbox part
        }
    
        // Extract the domain part from parts[1]
        String domain = parts[1];
    
        // Split the domain into subdomain and TLD parts using "\\." as delimiter
        String[] domainParts = domain.split("\\.");
    
        // Check if the domain contains at least one subdomain and one TLD
        if (domainParts.length < 2) {
            return false; // Invalid format: must contain at least one subdomain and TLD
        }
    
        // Check if the domain contains more than one dot after "@" (subdomain-TLD delimiter)
        if (domain.indexOf(".") != domain.lastIndexOf(".")) {
            return false; // More than one dot found after "@"
        }
    
        // Check if any subdomain or TLD part is empty
        for (String part : domainParts) {
            if (part.isEmpty()) {
                return false; // Empty subdomain or TLD part
            }
        }
    
        // All subcontracts passed, email address is valid
        return true;
    }
}