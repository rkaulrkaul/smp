import java.util.Arrays;

/* NetIds: rk493. Time spent: 4 hours, 0 minutes. */

/**
 * A collection of static String utility functions. <br>
 * All methods assume that String parameters are non-null.
 *
 * If any method is called with arguments that do not satisfy the
 * Preconditions,<br>
 * the behavior is undefined (it can do anything). You do not have to use
 * assert<br>
 * statements to test preconditions. We will not test with test cases that do
 * <br>
 * not satisfy Preconditions.
 */
public class A2 {
    /*
     * Wherever possible, prefer library functions to writing your own loops.
     *
     * The more complicated your loops become, the more important it is to explain
     * the logic in comments.
     *
     * See the JavaHyperText entries for if-statement, while-loop, and for-loop. Use
     * of the break-statement and continue-statement is discouraged but not
     * forbidden. They make loops and programs harder to understand. Usually, they
     * can be eliminated by restructuring/reorganizing code.
     */

    /**
     * Replace "-1" by the time you spent on A2 in hours.<br>
     * Example: for 3 hours 15 minutes, use 3.25<br>
     * Example: for 4 hours 30 minutes, use 4.50<br>
     * Example: for 5 hours, use 5 or 5.0
     */
    public static double timeSpent = 4.0;

    /**
     * Return either s1 + s2 or s1 - s2, depending on b. <br>
     * If b is true, return the sum, otherwise return the difference.
     */
    public static int sumDif(boolean b, int s1, int s2) {
	// This method is already implemented; it is here to give you something
	// to test, and to show you different ways of writing return statements.
	if (b) {
	    int s;
	    s = s1 + s2;
	    return s;

	    /*
	     * equivalently: int s = s1 + s2; return s;
	     *
	     * or simply: return s1 + s2;
	     */
	}

	// b is false;
	return s1 - s2;
    }

    /**
     * Return true iff (i.e. if and only if) s has an odd number of characters
     * and<br>
     * the characters on either side of the middle one (if they exist) are the same.
     * <br>
     * Examples: <br>
     * For s = "" return false <br>
     * For s = "$" return true <br>
     * For s = "22" return false <br>
     * For s = "2A2" return true <br>
     * For s = "2A%" return false <br>
     * For s = "2222" return false <br>
     * For s = "1234321" return true <br>
     * For s = "111" return true
     */
    public static boolean isMidSame(String s) {
	// TODO 1. There is no need for a loop. Do not use a loop.
	// This can be done cleanly in 4-5 statements.
	// Hint: Follow these Principles:
	// Principle: Avoid unnecessary case analysis
	// Principle: Avoid the same expression in several places.
	// Principle: Keep the structure of the method as simple as possible.
	if (s.length() % 2 != 0) {
	    if (s.length() == 1)
		return true;
	    else if (s.charAt(s.length() / 2 - 1) == s.charAt(s.length() / 2 + 1))
		return true;
	}
	return false;
    }

    /**
     * Return s but, for each character that is an upper-case letter in A..Z, <br>
     * insert the corresponding lower-case letter after it. <br>
     * Examples: <br>
     * For s = "", return "". <br>
     * For s = "b", return "b". For s = "B", return "Bb". <br>
     * For s = "$", return "$" <br>
     * For s = "1AAB", return "1AaAaBb".<br>
     * For s = "1Z$Bby", return "1Zz$Bby"
     */
    public static String dupCaps(String s) {
	// TODO 2.
	if (s.length() > 0) {
	    String dup = s;
	    if (s.length() == 1 && s.charAt(0) >= 'A' && s.charAt(0) <= 'Z') {
		dup = s + Character.toLowerCase(s.charAt(0));
	    } else {
		for (int i = 0; i < dup.length(); i++) {
		    char a = dup.charAt(i);
		    if (a >= 'A' && a <= 'Z') {
			dup = dup.substring(0, i + 1) + Character.toLowerCase(a)
				+ dup.substring(i + 1, dup.length());
		    }
		}
	    }
	    return dup;
	}
	return "";

    }

    /**
     * Return s but with each occurrence of a letter in 'a'..'y' replaced by<br>
     * the following letter and 'z' replaced by 'a'. <br>
     * Thus, there is "wraparound", 'z' wraps around to 'a'.
     *
     * Examples: prevChar("") = "" prevChar("bcda") = "cdeb" <br>
     * prevChar("1z$a") = "1a$b" prevChar("AB") = "AB" <br>
     * prevChar("love") = "mpwf" prevChar("knud") = "love"
     */
    public static String nextLowerCase(String s) {
	// TODO 3. Hint: Follow these Principles:
	// Principle: Avoid unnecessary case analysis like the plague.
	// Principle: Avoid the use of int constants for characters.
	// Principle: Use short names where long mnemonic names are unnecessary.
	if (s.length() > 0) {
	    String lower = s;
	    for (int i = 0; i < s.length(); i++) {
		char a = s.charAt(i);
		if (a >= 'a' && a <= 'y') {
		    lower = lower.substring(0, i) + (char) (a + 1)
			    + lower.substring(i + 1, lower.length());
		} else if (a == 'z') {
		    lower = lower.substring(0, i) + 'a' + lower.substring(i + 1, lower.length());
		}
	    }
	    return lower;
	}
	return "";
    }

    /**
     * Precondition: s and s1 are not null. <br>
     * Return true iff s contains at least 2 occurrences of s1. <br>
     * Examples: For s = "" and s1 = "", return false <br>
     * For s = "a" and s1 = "", return true. This is weird! The empty string <br>
     * occurs before and after each character! <br>
     * For s = "abc" and s1 = "", return true <br>
     * For s = "abbb" and s2 = "c", return false. <br>
     * For s = "abbb" and s2 = "ab", return false. <br>
     * For s = "abbbabc" and s2 = "ab", return true.
     */
    public static boolean atLeast2(String s, String s1) {
	// TODO 4 Do not use a loop or recursion. Instead, look through the
	// methods of class String and see how you can tell that the first
	// and last occurrences of s1 in s are different. Be sure you handle
	// correctly the case that s1 does not occur in s.
	//
	// Hint: Follow this Principle:
	// Principle: Be aware of efficiency considerations.
	// Note that a call like s.indexOf(s1) may take time proportional to the
	// length of string s. If s contains 1,000 characters and s1 contains 5, then
	// about 9996 tests may have to be made in the worst case. So you don't want
	// to have the same method call executed several times. Even the call of
	// contains in the code below is wasteful.
	//
	// if (s.contains(s1)) {
	// int k= s.indexOf(s1);
	// ...
	// }
	int k = s.indexOf(s1);
	if (s.length() > 0 && s1 == "")
	    return true;
	else if (k != -1 && s1 != "") {
	    k = s.substring(k + 1).indexOf(s1);
	    // k == -1 if s1 is not found again in the string
	    if (k != -1) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Return true iff sorting both s and u ends up with the same string of
     * chars.<br>
     * Note: 'a' and 'A' are different chars, and the space ' ' is a character.
     *
     * Examples: For s = "noon", u = "noon", return true. <br>
     * For s = "mary", u = "army", return true. <br>
     * For s = "tom marvolo riddle", u = "i am lordvoldemort", return true. <br>
     * For s = "tommarvoloriddle", u = "i am lordvoldemort", return false. <br>
     * For s = "hello", u = "world", return false.
     */
    public static boolean sortToSame(String s, String u) {
	// TODO 5
	/*
	 * Do not use a loop or recursion! This can be done in five lines using methods
	 * of classes String and Arrays
	 * (http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html). Hint: how
	 * can a sequence of characters be uniquely ordered? You might need to first
	 * convert the string to an array of characters and then use methods in class
	 * Arrays.
	 */
	String[] first = s.split("");
	String[] second = u.split("");
	Arrays.sort(first);
	Arrays.sort(second);
	return Arrays.equals(first, second);
    }

    /**
     * Return true iff s consists of its initial string of k chars catenated<br>
     * together a number of times. <br>
     * Precondition: k > 0 <br>
     * Examples: isCatenated("x", 1) is true <br>
     * isCatenated("x", 2) is false <br>
     * isCatenated("bbbbbb", 1") is true <br>
     * isCatenated("bbbbbb", 2") is true <br>
     * isCatenated("bbbbbb", 3") is true <br>
     * isCatenated("bbbbbb", 4") is false <br>
     * isCatenated("xyzxyz", 1") is false <br>
     * isCatenated("xyzxyz", 3") is true <br>
     * isCatenated("xyzxyzxyz", 3") is true <br>
     * isCatenated("xyxyxyxy", 2") is true <br>
     * isCatenated(s, s.length()) is true if s.length() > 0.
     */
    public static boolean isCatenated(String s, int k) {
	// TODO 6. Hint: Follow this Principle:
	// Make the structure of a loop reflect the structure of the data it
	// processes.
	// Make sure to use function equals, not ==, to test equality of strings.
	if (s.length() % k == 0 && s.length() > 0) {
	    String ini = s.substring(0, k);
	    for (int i = 0; i < s.length(); i = i + k) {
		if (!s.substring(i, i + k).equals(ini))
		    return false;
	    }
	    return true;
	}
	return false;
    }

    /**
     * Return the shortest substring x of s such that s = x + x + ... + x. <br>
     * Examples: For s = "" return ""<br>
     * For s = "xxxxxxxxx" return "x" <br>
     * For s = "xyxyxyxy" return "xy" <br>
     * For s = "012012012012" return "012" <br>
     * For s = "hellohellohello" return "hello" <br>
     * For s = "hellohelloworld" return "hellohelloworld" <br>
     * For s = "hellohel" return "hellohel"
     */
    public static String findShort(String s) {
	// TODO 7.
	// To implement this one, start checking for the shortest
	// substring to have length 1, then 2, then 3, and stop when
	// it meets the criterium. To make each of those checks,
	// use the previous method isCatenated.
	// Note that isCatenated(s, s.length()) is always true if s != "".
	String shortest = "";
	int step = 0;
	while (step < s.length()) {
	    if (!isCatenated(s, step + 1)) {
		step++;
	    } else {
		shortest = shortest + s.substring(0, step + 1);
		step = s.length(); // ends while loop
	    }
	}
	return shortest;
    }
}
