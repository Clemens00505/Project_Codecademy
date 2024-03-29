package objects;

public class URLTools {

        public static boolean validateURL(String url) {
            if (url == null || url.isEmpty()) {
                return false; // Empty or null URL
            }
    
            // Check if the URL starts with "https://" or "http://"
            if (!url.startsWith("https://") && !url.startsWith("http://")) {
                return false; // Missing prefix
            }
    
            // Remove the protocol part from the URL
            String urlWithoutProtocol = url.substring(url.indexOf("://") + 3);
    
            // Split the remaining URL into parts using "." as delimiter
            String[] urlParts = urlWithoutProtocol.split("\\.");
    
            // Check if the URL contains exactly three parts separated by dots
            if (urlParts.length != 3) {
                return false; // Invalid format
            }
    
            // Check if each part contains at least one letter
            for (String part : urlParts) {
                if (!part.matches(".*[a-zA-Z].*")) {
                    return false; // Part does not contain any letter
                }
            }
    
            // All subcontracts passed, URL is valid
            return true;
        }
}

