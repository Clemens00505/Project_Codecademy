package objects;

public class URLTools {
    /**
         * @desc Validates is URL is valid. It should be in the form of:
         *       <https:// of http://><minimaal één letter>.<minimaal één letter>.<minimaal één letter>
         * 
         * @subcontract no prefix part {
         * @requires !url.startsWith("https://") || !url.startsWith("http://")
         * @ensures \result = false; }
         * 
         * @subcontract no subdomain {
         * @requires !url.contains(".") || url.split(".")[0].length < 1;
         * @ensures \result = false; }
         * 
         * @subcontract no second-level domain {
         * @requires !url.contains(".") || url.split(".")[1].length < 1;
         * @ensures \result = false; }
         * 
         * @subcontract no top-level domain {
         * @requires !url.contains(".") || url.split(".")[2].length < 1;
         * @ensures \result = false; }
         * 
         * @subcontract valid URL {
         * @requires no other precondition;
         * @ensures \result = true; }
         * 
         */
        public static boolean validateURL(String url) {

            return false;
        }
}

