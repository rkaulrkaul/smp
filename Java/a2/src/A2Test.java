import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class A2Test {

    @Test
    void testIsMidSame() {
	assertEquals(false, A2.isMidSame(""));
	assertEquals(true, A2.isMidSame("$"));
	assertEquals(false, A2.isMidSame("bb"));
	assertEquals(true, A2.isMidSame("bAb"));
	assertEquals(false, A2.isMidSame("bA%"));
	assertEquals(false, A2.isMidSame("2222"));
	assertEquals(true, A2.isMidSame("1234321"));
	assertEquals(true, A2.isMidSame("111"));
	assertEquals(true, A2.isMidSame("A1$1B"));
	assertEquals(true, A2.isMidSame("AAAAA1$1BAAAA"));
    }

    @Test
    void testDupCaps() {
	assertEquals("", A2.dupCaps(""));
	assertEquals("b", A2.dupCaps("b"));
	assertEquals("$", A2.dupCaps("$"));
	assertEquals("Bb", A2.dupCaps("B"));
	assertEquals("1AaAaBb", A2.dupCaps("1AAB"));
	assertEquals("1Zz$Bbby", A2.dupCaps("1Z$Bby"));
	assertEquals("1aAaAaBbbBb", A2.dupCaps("1aAABbB"));
	assertEquals("Å", A2.dupCaps("Å"));
	assertEquals("\u00E4", A2.dupCaps("\u00E4"));
	assertEquals("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz",
		A2.dupCaps("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
    }

    @Test
    void testNextLowerCase() {
	assertEquals("", A2.nextLowerCase(""));
	assertEquals("cdeb", A2.nextLowerCase("bcda"));
	assertEquals("1a$b", A2.nextLowerCase("1z$a"));
	assertEquals("ACZ", A2.nextLowerCase("ACZ"));
	assertEquals("bcdefghijklmnopqrstuvwxyza", A2.nextLowerCase("abcdefghijklmnopqrstuvwxyz"));
	assertEquals("mpwf", A2.nextLowerCase("love"));
	assertEquals("love", A2.nextLowerCase("knud"));
	assertEquals("å", A2.nextLowerCase("å"));
    }

    @Test
    void testAtLeast2() {
	assertEquals(false, A2.atLeast2("", ""));
	assertEquals(true, A2.atLeast2("a", ""));
	assertEquals(true, A2.atLeast2("a^c", ""));
	assertEquals(false, A2.atLeast2("ac!!", "c"));
	assertEquals(true, A2.atLeast2("accc", "c"));
	assertEquals(false, A2.atLeast2("abbbabc", "abb"));
	assertEquals(true, A2.atLeast2("abbbabc", "ab"));
    }

    @Test
    public void testSortToSame() {
	assertEquals(true, A2.sortToSame("", ""));
	assertEquals(false, A2.sortToSame("n", ""));
	assertEquals(false, A2.sortToSame("", "m"));
	assertEquals(true, A2.sortToSame("m", "m"));
	assertEquals(true, A2.sortToSame("noon", "noon"));
	assertEquals(true, A2.sortToSame("mary", "army"));
	assertEquals(false, A2.sortToSame("hello", "world"));
	assertEquals(false, A2.sortToSame("hello", "hellos"));
	assertEquals(true, A2.sortToSame("tom marvolo riddle", "i am lordvoldemort"));
	assertEquals(false, A2.sortToSame("tommarvoloriddle", "i am lordvoldemort"));
    }

    @Test
    public void testIsCatenated() {
	assertEquals(false, A2.isCatenated("", 1));
	assertEquals(true, A2.isCatenated("$", 1));
	assertEquals(false, A2.isCatenated("$", 2));
	assertEquals(true, A2.isCatenated("bbbbbb", 1));
	assertEquals(true, A2.isCatenated("bbbbbb", 2));
	assertEquals(true, A2.isCatenated("bbbbbb", 3));
	assertEquals(false, A2.isCatenated("bbbbbb", 4));
	assertEquals(false, A2.isCatenated("xyzxyz", 1));
	assertEquals(false, A2.isCatenated("xyzxyz", 2));
	assertEquals(true, A2.isCatenated("xyzxyz", 3));
	assertEquals(false, A2.isCatenated("xyzxyz", 18));
	assertEquals(false, A2.isCatenated("xyzxyzxyzxyzxyzxyzxyzxyzxyzxyzxyzxyzxyzxyzx", 3));
	assertEquals(true, A2.isCatenated("xyzxyzxyzxyzxyzxyzxyzxyzxyzxyzxyzxyzxyzxyz", 3));
    }

    @Test
    public void testFindShort() {
	assertEquals("", A2.findShort(""));
	assertEquals("x", A2.findShort("x"));
	assertEquals("x", A2.findShort("xxxxxx"));
	assertEquals("012", A2.findShort("012012012012"));
	assertEquals("xy", A2.findShort("xyxyxyxy"));
	assertEquals("xyxz", A2.findShort("xyxzxyxz"));
	assertEquals("hello", A2.findShort("hellohellohello"));
	assertEquals("hellohelloworld", A2.findShort("hellohelloworld"));
	assertEquals("hellohel", A2.findShort("hellohel"));
    }

}
