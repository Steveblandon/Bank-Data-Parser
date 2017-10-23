package proj.core.extras;

public class Utils {
	/*NOTE: when OpenCsv parses empty string fields it passes them on as null.
	 * This creates a conflict when an empty string is expected. 
	 * Since this is only an issue when unit testing, this is the simpler solution
	 * as opposed to modifying the OpenCsv code.
	 */
	public static boolean nullOrEmptyStrings(String current, String other) {
		return (current == null && other.isEmpty())
				|| (current.isEmpty() && other  == null);
	}
}
