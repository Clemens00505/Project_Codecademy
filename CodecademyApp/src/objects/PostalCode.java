package objects;

public class PostalCode {

    /**
     * @desc Formats the input postal code to a uniform output in the form
     * nnnn<space>MM, where nnnn is numeric and > 999 and MM are 2 capital letters.
     * Spaces before and after the input string are trimmed.
     * 
     * @subcontract null postalCode {
     *   @requires postalCode == null;
     *   @signals (NullPointerException) postalCode == null; }
     * 
     * @subcontract valid postalCode {
     *   @requires Integer.valueOf(postalCode.trim().substring(0, 4)) > 999 &&
     *             Integer.valueOf(postalCode.trim().substring(0, 4)) <= 9999 &&
     *             postalCode.trim().substring(4).trim().length == 2 &&
     *             'A' <= postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z' &&
     *             'A' <= postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z';
     *   @ensures \result = postalCode.trim().substring(0, 4) + " " +
     *                  postalCode.trim().substring(4).trim().toUpperCase() }
     *  
     * @subcontract invalid postalCode {
     *   @requires no other valid precondition;
     *   @signals (IllegalArgumentException); }
     * 
     */
    public static String formatPostalCode(/* non_null */ String postalCode) {
        if (postalCode == null) {
            throw new NullPointerException("Postal code cannot be null");
        }

        postalCode = postalCode.trim();

        if (postalCode.length() != 6) {
            throw new IllegalArgumentException("Postal code must be exactly 6 characters long");
        }

        int numericPart = Integer.parseInt(postalCode.substring(0, 4));
        if (numericPart <= 999 || numericPart > 9999) {
            throw new IllegalArgumentException("Numeric part of postal code must be between 1000 and 9999");
        }

        String letterPart = postalCode.substring(4).trim().toUpperCase();
        if (!Character.isLetter(letterPart.charAt(0)) || !Character.isLetter(letterPart.charAt(1))) {
            throw new IllegalArgumentException("Letter part of postal code must be two letters");
        }

        return postalCode.substring(0, 4) + " " + letterPart;
    }
}
