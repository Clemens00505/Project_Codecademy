import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PostalCodeTest {

    //waarde postalcode aanpassen bij implementatie
    @Test(expected = NullPointerException.class)
    public void testPostalCodeEqualsNull() {

        String postalCode = null;
        
        String formattedPostalCode = PostalCode.formatPostalCode(postalCode);

        assertNull(formattedPostalCode);
    }

    @Test
    public void testPostalCodeNumberValid() {
        
        String postalCode = "5126bb";

        String formattedPostalCode = PostalCode.formatPostalCode(postalCode);

        int postalCodeNumber = Integer.valueOf(formattedPostalCode.trim().substring(0, 4));
        String postalCodeLetters = formattedPostalCode.trim().substring(4).toUpperCase();

        assertTrue(postalCodeNumber > 999 && postalCodeNumber <= 9999 && postalCodeLetters.charAt(0) >= 'A' && postalCodeLetters.charAt(1) <= 'Z' && postalCodeLetters.charAt(1) >= 'A' && postalCodeLetters.charAt(1) <= 'Z');

    }
    
    @Test(expected = IllegalArgumentException.class)//niet uitgekomen
    public void testPostalCodeInvalid() {
        String postalCode = "999ZZ";

        String formattedPostalCode = PostalCode.formatPostalCode(postalCode);

    
    }
    

}
