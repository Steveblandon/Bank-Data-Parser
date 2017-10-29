package proj.core.utils;

/** when OpenCsv parses empty string fields it passes them on as null.
 * This creates a conflict when an empty string is expected. 
 * Since this is only an issue when unit testing, this is the simpler solution
 * as opposed to modifying the OpenCsv code.
 */
public class StringUtil {
	
	/**
	 * compares a string with another string, applying certain filters to derive equality.
	 * @param string
	 * @param anotherString
	 * @param nullSameAsEmpty
	 * @param ignoreCase
	 * @return
	 * <li> true, if nullSameAsEmpty is true, and both passed strings are either empty or null
	 * <li> true, if ignoreCase is true, and both passed strings are equal as per String.equalsIgnoreCase(String anotherString). 
	 * This is only here for convenience, if setting nullSameAsEmpty to false, just use String.equalsIgnoreCase(String anotherString) directly.
	 * <li> true, if strings are equals as per String.equals(Object anObject). 
	 * This is only here for consistency, if setting nullSameAsEmpty and ignoreCase both to false, just use String.equals(Object anObject) directly.
	 */
	public static boolean equalsWithFilters(String string, String anotherString, boolean nullSameAsEmpty, boolean ignoreCase) {
		return 
				(nullSameAsEmpty  
				&& (string == null && anotherString.isEmpty())
				|| (string.isEmpty() && anotherString  == null))
				|| 
				(ignoreCase 
				&& string.equalsIgnoreCase(anotherString))
				||
				(!ignoreCase
				&& string.equals(anotherString));
	}
}
