
/** JUnitTesting for PhD class methods */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PhDTest {

	/**
	 * Testing original PhD constructor with three parameters, and getters.
	 */
	@Test
	public void testGroupA() {
		PhD p1 = new PhD(10, 2012, "Jim");
		assertEquals("Jim", p1.name());
		assertEquals(2012, p1.year());
		assertEquals(10, p1.month());
		assertEquals(null, p1.advisor1());
		assertEquals(null, p1.advisor2());
		assertEquals(0, p1.advisees());
	}

	/**
	 * Testing setter methods.
	 */
	@Test
	public void testGroupB() {
		PhD p1 = new PhD(1, 2000, "Carrie");
		PhD p2 = new PhD(6, 1999, "Teach");
		PhD p3 = new PhD(3, 1983, "Boon");

		p1.setAdvisor1(p2);
		p1.setAdvisor2(p3);
		assertEquals(p2, p1.advisor1());
		assertEquals(1, p2.advisees());
		assertEquals(1, p2.advisees());
		assertEquals(p3, p1.advisor2());
	}

	/**
	 * Testing PhD constructors with 4 and 5 parameters.
	 */
	@Test
	public void testGroupC() {
		PhD p4 = new PhD(12, 1943, "Tom");
		PhD p5 = new PhD(1, 1998, "Harry", p4);
		PhD p6 = new PhD(3, 2000, "Cheese", p5);

		assertEquals("Cheese", p6.name());
		assertEquals(2000, p6.year());
		assertEquals(3, p6.month());
		assertEquals(p5, p6.advisor1());
		assertEquals(null, p6.advisor2());
		assertEquals(0, p6.advisees());

		assertEquals(1, p5.advisees());
		assertEquals(1, p4.advisees());

		PhD p7 = new PhD(5, 3234, "Bob", p4, p5);

		assertEquals("Bob", p7.name());
		assertEquals(3234, p7.year());
		assertEquals(5, p7.month());
		assertEquals(p4, p7.advisor1());
		assertEquals(p5, p7.advisor2());
		assertEquals(0, p7.advisees());

		assertEquals(2, p5.advisees());
		assertEquals(2, p4.advisees());
	}

	/**
	 * Testing gotAfter and areSiblings methods.
	 */
	@Test
	public void testGroupD() {
		// Testing gotAfter
		PhD feb77 = new PhD(2, 1977, "feb77");
		PhD secfeb77 = new PhD(2, 1977, "feb77");
		PhD jun77 = new PhD(6, 1977, "jun77");
		PhD jan77 = new PhD(1, 1977, "jan77");

		assertEquals(false, feb77.gotAfter(secfeb77));
		assertEquals(false, jan77.gotAfter(secfeb77));
		assertEquals(true, jun77.gotAfter(secfeb77));

		PhD jan78 = new PhD(1, 1978, "jan77");
		PhD jan76 = new PhD(1, 1976, "jan77");
		PhD feb78 = new PhD(2, 1978, "jan77");
		PhD dec77 = new PhD(12, 1977, "jan77");

		assertEquals(false, jan76.gotAfter(jan78));
		assertEquals(true, jan78.gotAfter(jan76));
		assertEquals(true, feb78.gotAfter(jan76));
		assertEquals(false, jan76.gotAfter(feb78));
		assertEquals(false, dec77.gotAfter(feb78));
		assertEquals(true, feb78.gotAfter(dec77));

		// Testing areSiblings
		PhD a = new PhD(5, 1977, "a");
		PhD b = new PhD(5, 1977, "a");

		assertEquals(false, a.areSiblings(a));
		b = new PhD(5, 1997, "b");
		assertEquals(false, a.areSiblings(b));
		PhD c = new PhD(5, 1977, "c");
		PhD d = new PhD(5, 1997, "d");

		a.setAdvisor1(c);
		b.setAdvisor1(c);
		b.setAdvisor2(d);
		PhD e = new PhD(5, 1997, "e", d);
		assertEquals(true, a.areSiblings(b));
		assertEquals(true, e.areSiblings(b));

		a = new PhD(5, 1977, "a", e);
		b = new PhD(5, 1977, "b", d, e);
		assertEquals(true, b.areSiblings(a));
		a = new PhD(5, 1977, "a", e, d);
		b = new PhD(5, 1977, "b", e, d);
		assertEquals(true, b.areSiblings(a));
		assertEquals(false, b.areSiblings(null));

	}

	/**
	 * Testing preconditions and assert statements in class PhD.
	 */
	@Test
	void testPreconditions() {
		assertThrows(AssertionError.class, () -> {
			new PhD(2, 1999, "");
		});
		assertThrows(AssertionError.class, () -> {
			new PhD(0, 1999, "a");
		});
		assertThrows(AssertionError.class, () -> {
			new PhD(13, 1999, "a");
		});
		assertThrows(AssertionError.class, () -> {
			new PhD(2, 1999, "a", null);
		});
		PhD feb77 = new PhD(2, 1977, "feb77");
		PhD secfeb77 = new PhD(2, 1977, "feb77");
		PhD jun77 = new PhD(6, 1977, "jun77");
		PhD jan77 = new PhD(1, 1977, "jan77");
		assertThrows(AssertionError.class, () -> {
			new PhD(2, 1999, "a", feb77, null);
		});
		assertThrows(AssertionError.class, () -> {
			new PhD(2, 1999, "a", feb77, feb77);
		});
		assertThrows(AssertionError.class, () -> {
			new PhD(2, 1999, "a", null, feb77);
		});
		assertThrows(AssertionError.class, () -> {
			feb77.setAdvisor1(null);
		});
		feb77.setAdvisor1(jan77);
		assertThrows(AssertionError.class, () -> {
			feb77.setAdvisor1(jun77);
		});
		assertThrows(AssertionError.class, () -> {
			feb77.setAdvisor2(null);
		});
		feb77.setAdvisor2(jun77);
		assertThrows(AssertionError.class, () -> {
			feb77.setAdvisor2(secfeb77);
		});

		assertThrows(AssertionError.class, () -> {
			feb77.gotAfter(null);
		});

	}

}
